package org.multiagent_city.environment;

import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.strategy.IStrategy;

public class Map extends Observable{
    private static Map instance = null;
    private Zone[][] zones;
    private Position townHallPosition;
    private Map() {
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
}
