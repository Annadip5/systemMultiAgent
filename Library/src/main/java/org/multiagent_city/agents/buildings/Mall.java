package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.Texture;

import java.awt.*;

public class Mall extends Building {
    public Mall(int minHealth, int maxHealth, float usuryCoefficient){
        super(new InfrastructureType("Mall", new Color(123, 104, 238), Texture.MALL), minHealth, maxHealth, usuryCoefficient);
    }
    public Mall(InfrastructureType type, Position position) {
        super(type, position);
    }

    public Mall(InfrastructureType type, Position position, int capacity) {
        super(type, position, capacity);
    }
    public Mall(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, Position position, int capacity, int spawnProbability) {
        super(type, usuryCoefficient, minHealth, maxHealth, position, capacity, spawnProbability);
    }
}
