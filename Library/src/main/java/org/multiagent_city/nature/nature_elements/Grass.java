package org.multiagent_city.nature.nature_elements;

import org.multiagent_city.nature.Nature;
import org.multiagent_city.utils.Texture;

import java.awt.*;

public class Grass extends Nature {
    public Grass() {
        super("Grass", new Color(173, 255, 47), Texture.grass,true);
    }
}
