package org.multiagent_city.environment;

import org.multiagent_city.agents.buildings.TownHall;
import org.multiagent_city.nature.Nature;
import org.multiagent_city.nature.nature_elements.*;
import org.multiagent_city.utils.FastNoiseLite;
import org.multiagent_city.utils.NatureMap;
import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.strategy.IStrategy;

import java.util.Arrays;
import java.util.Random;

public class Map extends Observable{
    private static Map instance = null;

    private Zone[][] zones;
    private int width;
    private int height;
    private TownHall townHall;
    private Map() {

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

    public void setStrategy(IStrategy strategy){

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
