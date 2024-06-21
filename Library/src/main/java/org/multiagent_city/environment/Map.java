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
import org.multiagent_city.zonestate.LockedState;

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
        zone.setZoneState(new LockedState(1, zone));
        // Notify map changes
        this.notify(this, position);
    }

    public void addBuilding(IStrategy strategy, Class<? extends Building> buildingClass){
        MapContext context = new MapContext(strategy);
        try {
            Building building = buildingClass.getDeclaredConstructor().newInstance();
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
    public void addRoad(IStrategy strategy){
        MapContext context = new MapContext(strategy);
        Road road = new Road();
        Position newPosition = context.execute(this, road);
        System.out.println("newPosition");

        System.out.println(newPosition);

        if(newPosition == null) {
            return;
        }
        road.setPosition(newPosition);

        // Add the road agent to list
        this.roads.add(road);
        // Add the road to the zone
        this.setZone(newPosition, road);
        System.out.println("Road " + newPosition);
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
