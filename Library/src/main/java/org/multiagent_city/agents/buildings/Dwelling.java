package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureType;

public class Dwelling extends Building {
    public Dwelling(InfrastructureType type) {
        super(type);
    }

    public Dwelling(InfrastructureType type, int capacity) {
        super(type, capacity);
    }

    public Dwelling(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, int capacity, int spawnProbability) {
        super(type, usuryCoefficient, minHealth, maxHealth, capacity,spawnProbability);
    }

    @Override
    public String toString() {
        return "Dwelling{" +
                "usuryCoefficient=" + usuryCoefficient +
                ", minHealth=" + minHealth +
                ", maxHealth=" + maxHealth +
                '}';
    }
}
