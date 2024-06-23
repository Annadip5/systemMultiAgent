package org.multiagent_city.environment;

import org.multiagent_city.agents.Building;
import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.agents.Road;
import org.multiagent_city.agents.buildings.TownHall;
import org.multiagent_city.nature.Nature;
import org.multiagent_city.nature.nature_elements.*;
import org.multiagent_city.utils.FastNoiseLite;
import org.multiagent_city.utils.NatureMap;
import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.strategy.IStrategy;
import org.multiagent_city.zonestate.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Map extends Observable{
    private static Map instance = null;

    private Zone[][] zones;
    private int width;
    private int height;
    private TownHall townHall;
    private List<Road> roads;
    private List<Building> buildings;

    private Map() {
        this.roads = new ArrayList<>();
        this.buildings = new ArrayList<>();
    }

    public void buildMap(FastNoiseLite noise, int blurRadius){
        this.zones = new Zone[this.height][this.width];
        Nature[][] natureMap = NatureMap.generateNatureMap(this.width, this.height, noise, blurRadius);

        for(int x = 0; x < this.height; x++){
            for(int y = 0; y < this.width; y++){

                this.zones[x][y] = new Zone(natureMap[x][y]);
            }
        }

        // Notify map changes
        this.notify(this, null);
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Zone[][] getZones() {
        return zones;
    }
    public void setZones(Zone[][] zones) {
        this.zones = zones;
    }

    public TownHall getTownHall() {
        return townHall;
    }

    public void setTownHall(TownHall townHall) {
        this.townHall = townHall;
        this.zones[townHall.getPosition().getX()][townHall.getPosition().getY()].setInfrastructure(townHall);
        // Notify map changes
        this.notify(this, townHall.getPosition());
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
        // Notify map changes
        this.notify(this, null);
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
        // Notify map changes
        this.notify(this, null);
    }

    // Methods
    public static Map getInstance() {
        if (Map.instance == null) {
            synchronized(Map.class) {
                if (Map.instance == null) {
                    Map.instance = new Map();
                }
            }
        }
        return Map.instance;
    }

    public boolean isZoneOccupied(Position position){
        return this.zones[position.getX()][position.getY()].isOccupied();
    }

    public boolean isZoneBuildable(Position position){
        return this.zones[position.getX()][position.getY()].isBuildable();
    }

    private void setZone(Position position, Infrastructure infrastructure) {
        Zone zone = this.zones[position.getX()][position.getY()];
        // Set the infrastructure for the Zone
        zone.setInfrastructure(infrastructure);
        // Set the state for the Zone
        zone.getZoneState().nextState(3);
        // Notify map changes
        this.notify(this, position);
    }

    public void addBuilding(IStrategy strategy, Class<? extends Building> buildingClass, int minHealth, int maxHealth, float usuryCoefficient){
        MapContext context = new MapContext(strategy);
        try {
            Building building = buildingClass
                    .getDeclaredConstructor(int.class, int.class, float.class)
                    .newInstance(minHealth, maxHealth, usuryCoefficient);
            Position newPosition = context.execute(this, building);

            if(newPosition == null) {
                return;
            }
            building.setPosition(newPosition);

            // Add the building to the list
            this.buildings.add(building);
            // Add the building to the zone
            this.setZone(newPosition, building);
            System.out.println(building.getClass() + " " + newPosition);
        } catch (Exception e) {
            System.out.println("Error in Map.addBuilding !");
            e.printStackTrace();
        }
    }
    public void addRoad(IStrategy strategy, int minHealth, int maxHealth, float usuryCoefficient){
        MapContext context = new MapContext(strategy);
        Road road = new Road(minHealth, maxHealth, usuryCoefficient);
        Position newPosition = context.execute(this, road);

        if(newPosition == null) {
            return;
        }
        road.setPosition(newPosition);
        if (this.hasBuildingInRadius(newPosition, 10)) { // Remplacer 5 par le rayon souhaité
            // Ajouter l'agent de route à la liste
            this.roads.add(road);
            // Ajouter la route à la zone
            this.setZone(newPosition, road);
            System.out.println("Road " + newPosition);
       }
    }


    public void checkZoneState(double deltaTime) {
        List<Infrastructure> infrastructures = new ArrayList<>();
        List<Infrastructure> infrastructuresToRemove = new ArrayList<>();

        infrastructures.addAll(this.roads);
        infrastructures.addAll(this.buildings);
        if (!infrastructures.isEmpty()) {
            // System.out.println(infrastructures.get(0));
        }
        for (Infrastructure infrastructure : infrastructures) {
            Position infraPos = infrastructure.getPosition();
            if (!infrastructure.checkNeighborPosition(this, infraPos)) {
                infrastructuresToRemove.add(infrastructure);
                continue;
            }
            ZoneState zoneState = this.zones[infraPos.getX()][infraPos.getY()].getZoneState();
            if (zoneState instanceof BuildedState || zoneState instanceof Degradedstate) {
                if (infrastructure.getCurrentUsury() < (double) infrastructure.getHealth() / 2 && zoneState instanceof BuildedState) {
                    zoneState.nextState(0);
                }
                if (infrastructure.getCurrentUsury() == infrastructure.getHealth() && zoneState instanceof Degradedstate) {
                    zoneState.nextState(0);
                    infrastructuresToRemove.add(infrastructure);
                }
                // Add usury
                infrastructure.addCurrentUsury(deltaTime);

                // Specific logic for roads
                if (infrastructure instanceof Road) {
                    Road road = (Road) infrastructure;
                    if (!infrastructure.hasBuildingsAround(this, infraPos, 5)) {
                        road.setNoBuildingTime(road.getNoBuildingTime() + deltaTime);
                        if (road.getNoBuildingTime() > 10) {
                            infrastructuresToRemove.add(road);
                        }
                    } else {
                        road.setNoBuildingTime(0);
                    }
                }
            } else {
                zoneState.removeCurrentTime(deltaTime);
                if (zoneState.getCurrentTime() == 0) {
                    this.notify(this, infraPos);
                }
            }
        }
        this.deleteInfrastructures(infrastructuresToRemove);
    }

    public void resetZone(Infrastructure infrastructure){
        Position infraPos = infrastructure.getPosition();
        Zone zone = this.getZones()[infraPos.getX()][infraPos.getY()];
        if(!(zone.getNature() instanceof Grass)) {
            zone.setNature(new Grass());
        }
        if(!(zone.getZoneState() instanceof EmptyState)) {
            zone.setZoneState(new EmptyState(zone));
        }
    }
    public void deleteInfrastructure(Infrastructure infrastructure){
        if (infrastructure instanceof Road) {
            if(((Road) infrastructure).getNoBuildingTime()>10){
                this.roads.remove(infrastructure);
                this.resetZone(infrastructure);
            }
            else {
                infrastructure.repair(this, 2);
            }
        }
        if (infrastructure instanceof Building) {
            this.buildings.remove(infrastructure);

           this.resetZone(infrastructure);
        }

        this.notify(this, infrastructure.getPosition());
    }
    public void deleteInfrastructures(List<Infrastructure> list){
        for(Infrastructure i: list){
            this.deleteInfrastructure(i);
        }
    }
    public boolean hasBuildingInRadius(Position position, int radius) {
        int startX = Math.max(0, position.getX() - radius);
        int endX = Math.min(width - 1, position.getX() + radius);
        int startY = Math.max(0, position.getY() - radius);
        int endY = Math.min(height - 1, position.getY() + radius);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (zones[x][y].getInfrastructure() instanceof Building) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public String toString() {
        String printZone = "\n";
        for(int x = 0; x < this.height; x++){
            for(int y = 0; y < this.width; y++){
                printZone += this.zones[x][y].toString() + "|";
            }

            printZone +="\n";
        }
        return "Map{" +
                "zones=" + printZone+
                ", width=" + width +
                ", height=" + height +
                ", townHallPosition=" + townHall.getPosition().toString() +
                '}';
    }
}
