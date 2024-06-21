package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.Texture;

import java.awt.*;

public class Dwelling extends Building {
    public Dwelling(){
        super(new InfrastructureType("Dwelling", new Color(255, 165, 0), Texture.dwelling));
    }
    public Dwelling(InfrastructureType type, Position position) {
        super(type, position);
    }

    public Dwelling(InfrastructureType type, Position position, int capacity) {
        super(type, position, capacity);
    }

    public Dwelling(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, Position position, int capacity, int spawnProbability) {
        super(type, usuryCoefficient, minHealth, maxHealth, position, capacity,spawnProbability);
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
