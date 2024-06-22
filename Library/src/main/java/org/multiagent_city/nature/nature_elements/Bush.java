package org.multiagent_city.nature.nature_elements;

import org.multiagent_city.nature.Nature;
import org.multiagent_city.utils.Texture;

import java.awt.*;

public class Bush extends Nature {
    public Bush( ) {
        super("Bush", new Color(50, 205, 50), Texture.bush,true);
    }
}
