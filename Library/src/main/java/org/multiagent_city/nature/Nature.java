package org.multiagent_city.nature;

import java.awt.*;

public abstract class Nature {
    protected String name;
    protected Color color;
    protected String texture;
    protected boolean buildable;

    public Nature(String name, Color color, String texture, boolean buildable) {
        this.name = name;
        this.color = color;
        this.texture = texture;
        this.buildable = buildable;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public String getTexture() {
        return this.texture;
    }

    public boolean isBuildable() {
        return buildable;
    }
}
