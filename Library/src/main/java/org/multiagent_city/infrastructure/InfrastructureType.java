package org.multiagent_city.infrastructure;

import org.multiagent_city.utils.Texture;

import java.awt.*;

public class InfrastructureType {
    private String name;
    private Color color;
    private String texture;

    public InfrastructureType(String name, Color color, String texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "InfrastructureType{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", texture='" + texture + '\'' +
                '}';
    }
}