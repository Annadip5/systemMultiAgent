package org.multiagent_city.environment;

import org.multiagent_city.agents.Building;
import org.multiagent_city.agents.Road;
import org.multiagent_city.agents.buildings.TownHall;
import org.multiagent_city.nature.Nature;
import org.multiagent_city.nature.nature_elements.*;
import org.multiagent_city.utils.FastNoiseLite;
import org.multiagent_city.utils.NatureMap;
import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.strategy.IStrategy;

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
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
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

    public void addBuilding(IStrategy strategy){

    }
    public void addRoad(IStrategy strategy){
        MapContext context = new MapContext(strategy);
        Road road = new Road();
        Position newPosition = context.execute(this, road);

        road.setPosition(newPosition);

        // Add the road agent to list
        this.roads.add(road);
        // Add the road to the zone
        this.zones[newPosition.getX()][newPosition.getY()].setInfrastructure(road);
        System.out.println(newPosition);
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
