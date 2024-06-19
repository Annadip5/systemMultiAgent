package org.multiagent_city.environment;

import org.multiagent_city.nature.Nature;
import org.multiagent_city.nature.nature_elements.*;
import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.strategy.IStrategy;

import java.util.Arrays;
import java.util.Random;

public class Map extends Observable{
    private static Map instance = null;

    private Zone[][] zones;
    private int width;
    private int height;
    private Position townHallPosition;
    private Map() {

    }
    private Nature buildNature(){
        Random rand = new Random();
        int natureRand=rand.nextInt(5)+1;
        Nature nature;
        switch(natureRand){
            case 1:
                nature =  new Bush();
                break;
            case 2:
                nature =  new Grass();
                break;
            case 3:
                nature =  new Rock();
                break;
            case 4:
                nature =  new Tree();
                break;
            case 5:
                nature =  new Water();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + natureRand);
        }
        return nature;
    }
    public void buildMap(){
        this.zones = new Zone[this.height][this.width];
        for(int x = 0; x < this.height; x++){
            for(int y = 0; y < this.width; y++){

                this.zones[x][y] = new Zone(this.buildNature());
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

    public Position getTownHallPosition() {
        return townHallPosition;
    }
    public void setTownHallPosition(Position townHallPosition) {
        this.townHallPosition = townHallPosition;
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

            printZone +="\n"+ "-".repeat(width*2-1)+"\n";
        }
        return "Map{" +
                "zones=" + printZone+
                ", width=" + width +
                ", height=" + height +
                ", townHallPosition=" + townHallPosition.toString() +
                '}';
    }
}
