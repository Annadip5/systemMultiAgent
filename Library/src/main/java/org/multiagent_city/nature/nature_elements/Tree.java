package org.multiagent_city.nature.nature_elements;

import org.multiagent_city.nature.Nature;
import org.multiagent_city.utils.Texture;

import java.awt.*;

public class Tree extends Nature {
    public Tree() {
        super("Tree", new Color(34, 139, 34), Texture.tree,true);
    }
}
