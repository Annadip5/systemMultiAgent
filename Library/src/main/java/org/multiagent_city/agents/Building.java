package org.multiagent_city.agents;

import org.multiagent_city.agents.Infrastructure;
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

}
