package org.multiagent_city.infrastructure;

import org.multiagent_city.enumerations.Color;
import org.multiagent_city.enumerations.Texture;

public class InfrastructureType {
    private String name;



    private Color color;
    private Texture texture;

    public InfrastructureType(String name, Color color, Texture texture) {
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



    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {
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