package org.multiagent_city.agents;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.environment.Map;
import org.multiagent_city.infrastructure.InfrastructureFactory;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.Texture;

import java.awt.*;

public abstract class Building extends Infrastructure {
    private int capacity;
    protected int spawnProbability;

    public Building(InfrastructureType type, int minHealth, int maxHealth, float usuryCoefficient) {
        super(type, minHealth, maxHealth, usuryCoefficient);
    }

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

    @Override
    public Boolean checkSpecificRule(Map map, Position positionToCheck){
        int x = positionToCheck.getX();
        int y = positionToCheck.getY();
        int width = map.getWidth();
        int height = map.getHeight();

        boolean isPresentUp = false, isPresentDown = false, isPresentLeft = false, isPresentRight = false;

        // Check top position
        if (isInMap(new Position(x, y + 1), width, height)) {
            isPresentUp = map.getZones()[x][y + 1].getInfrastructure() instanceof Road;
        }
        // Check bottom position
        if (isInMap(new Position(x, y - 1), width, height)) {
            isPresentDown = map.getZones()[x][y - 1].getInfrastructure() instanceof Road;
        }
        // Check left position
        if (isInMap(new Position(x - 1, y), width, height)) {
            isPresentLeft = map.getZones()[x - 1][y].getInfrastructure() instanceof Road;
        }
        // Check right position
        if (isInMap(new Position(x + 1, y), width, height)) {
            isPresentRight = map.getZones()[x + 1][y].getInfrastructure() instanceof Road;
        }

        return isPresentUp || isPresentDown || isPresentLeft || isPresentRight;
    }

}
