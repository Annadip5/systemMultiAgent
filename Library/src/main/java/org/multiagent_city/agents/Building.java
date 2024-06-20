package org.multiagent_city.agents;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.environment.Map;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;

public abstract class Building extends Infrastructure {
    private int capacity;
    protected int spawnProbability;

    public Building(InfrastructureType type, Position position) {
        super(type, position);
    }

    public Building(InfrastructureType type, Position position, int capacity) {
        super(type, position);
        this.capacity = capacity;
    }
    public Building(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, Position position, int capacity, int spawnProbability) {
        super(type, usuryCoefficient, minHealth, maxHealth, position);
        this.capacity = capacity;
        this.spawnProbability = spawnProbability;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSpawnProbability() {
        return spawnProbability;
    }

    public void setSpawnProbability(int spawnProbability) {
        this.spawnProbability = spawnProbability;
    }

    public Boolean checkBuildRule(Map map, Position positionToCheck){
        if(map.isZoneBuildable(positionToCheck)) {

            boolean isPresentUp = map.getZones()[positionToCheck.getX()][positionToCheck.getY()+1].getInfrastructure() instanceof Road;
            boolean isPresentDown =  map.getZones()[positionToCheck.getX()][positionToCheck.getY()-1].getInfrastructure() instanceof Road;
            boolean isPresentLeft =  map.getZones()[positionToCheck.getX()-1][positionToCheck.getY()].getInfrastructure() instanceof Road;
            boolean isPresentRight =  map.getZones()[positionToCheck.getX()+1][positionToCheck.getY()].getInfrastructure() instanceof Road;


            if (isPresentUp) {
                return true;
            }
            if (isPresentDown) {
                return true;
            }
            if (isPresentLeft) {
                return true;
            }
            if (isPresentRight) {
                return true;
            }
        }
        return false;



    }

}
