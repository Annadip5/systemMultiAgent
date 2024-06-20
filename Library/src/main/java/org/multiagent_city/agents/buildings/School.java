package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;

public class School extends Building {
    public School(InfrastructureType type, Position position) {
        super(type, position);
    }

    public School(InfrastructureType type, Position position, int capacity) {
        super(type, position, capacity);
    }

    public School(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, Position position, int capacity, int spawnProbability) {
        super(type, usuryCoefficient, minHealth, maxHealth, position, capacity, spawnProbability);
    }
}
