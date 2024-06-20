package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureType;

public class Mall extends Building {
    public Mall(InfrastructureType type) {
        super(type);
    }

    public Mall(InfrastructureType type, int capacity) {
        super(type, capacity);
    }
    public Mall(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, int capacity, int spawnProbability) {
        super(type, usuryCoefficient, minHealth, maxHealth, capacity,spawnProbability);
    }
}
