package org.multiagent_city.agents;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.environment.Map;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;

public abstract class Building extends Infrastructure {
    private int capacity;
    protected int spawnProbability;

    public Building(InfrastructureType type) {
        super(type);
    }

    public Building(InfrastructureType type, int capacity) {
        super(type);
        this.capacity = capacity;
    }
    public Building(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, int capacity, int spawnProbability) {
        super(type, usuryCoefficient, minHealth, maxHealth);
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
