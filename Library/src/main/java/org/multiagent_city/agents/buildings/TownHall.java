package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Texture;

import java.awt.*;

public class TownHall extends Building {

    public TownHall() {
        super(new InfrastructureType("TownHall", new Color(255,0,0), Texture.townHall));
    }
    public TownHall(InfrastructureType type) {
        super(type);
    }

    public TownHall(InfrastructureType type, int capacity) {
        super(type, -1);
    }
    public TownHall(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, int capacity, int spawnProbability) {
        super(type, -1, -1, -1, -1, -1);
    }
}
