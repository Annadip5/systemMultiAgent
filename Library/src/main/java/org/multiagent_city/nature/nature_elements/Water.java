package org.multiagent_city.nature.nature_elements;

import org.multiagent_city.nature.Nature;
import org.multiagent_city.utils.Texture;

import java.awt.*;

public class Water extends Nature {
    public Water() {
        super("Water", new Color(0, 206, 209), Texture.water,false);
    }
}
