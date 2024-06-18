package org.multiagent_city.agents;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.infrastructure.InfrastructureType;

public abstract class Building extends Infrastructure {
    private int capacity;
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Building(int capacity, InfrastructureType type) {
        super(type);
        this.capacity = capacity;
    }
}
