package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureType;

public class School extends Building {
    public School(InfrastructureType type) {
        super(type);
    }

    public School(InfrastructureType type, int capacity) {
        super(type, capacity);
    }

    public School(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, int capacity, int spawnProbability) {
        super(type, usuryCoefficient, minHealth, maxHealth, capacity,spawnProbability);
    }
}
