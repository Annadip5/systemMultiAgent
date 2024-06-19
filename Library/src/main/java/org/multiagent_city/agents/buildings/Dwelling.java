package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureType;

public class Dwelling extends Building {
    public Dwelling(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, int capacity, int spawnProbability) {
        super(type, usuryCoefficient, minHealth, maxHealth, capacity,spawnProbability);
    }



    public Dwelling(int capacity, InfrastructureType type) {
        super(capacity,type);
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
