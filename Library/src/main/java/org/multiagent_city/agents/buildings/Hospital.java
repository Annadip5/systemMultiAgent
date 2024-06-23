package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.Texture;

import java.awt.*;

public class Hospital extends Building {
    public Hospital(int minHealth, int maxHealth, float usuryCoefficient){
        super(new InfrastructureType("Hospital", new Color(240, 128, 128), Texture.HOSPITAL), minHealth, maxHealth, usuryCoefficient);
    }
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
