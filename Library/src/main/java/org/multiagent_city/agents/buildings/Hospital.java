package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;

public class Hospital extends Building {
    public Hospital(InfrastructureType type, Position position) {
        super(type, position);
    }

    public Hospital(InfrastructureType type, Position position, int capacity) {
        super(type, position, capacity);
    }

    public Hospital(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, Position position, int capacity, int spawnProbability) {
        super(type, usuryCoefficient, minHealth, maxHealth, position, capacity, spawnProbability);
    }
}
