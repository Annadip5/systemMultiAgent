package org.multiagent_city.nature;

import java.awt.*;

public abstract class Nature {
    protected String name;
    protected Color color;
    protected boolean buildable;

    public Nature(String name, Color color, boolean buildable) {
        this.name = name;
        this.color = color;
        this.buildable = buildable;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public boolean isBuildable() {
        return buildable;
    }
}
