package org.multiagent_city.nature.nature_elements;

import org.multiagent_city.nature.Nature;
import org.multiagent_city.utils.Texture;

import java.awt.*;

public class Rock extends Nature {
    public Rock() {
        super("Rock", new Color(128, 128, 128), Texture.ROCK,false);
    }
}
