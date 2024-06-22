package org.multiagent_city.model;

import java.awt.*;

public class MapCellUI {
    private Color color;
    private String texture;

    public MapCellUI(Color color, String texture) {
        this.color = color;
        this.texture = texture;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}
