package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureType;

public class TownHall extends Building {

    public TownHall(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, int capacity, int spawnProbability) {
        super(type, -1, -1, -1, -1, -1);
    }

    public TownHall(int capacity, InfrastructureType type) {
        super(-1, type);
    }
}
