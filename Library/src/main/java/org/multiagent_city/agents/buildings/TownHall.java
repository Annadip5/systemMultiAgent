package org.multiagent_city.agents.buildings;

import org.multiagent_city.agents.Building;
import org.multiagent_city.infrastructure.InfrastructureFactory;
import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.Texture;

import java.awt.*;

public class TownHall extends Building {

    public TownHall(int x, int y) {
        super(InfrastructureFactory.getInfrastructureType("TownHall", new Color(255,0,0), Texture.townHall), new Position(x, y));
    }
}
