package org.multiagent_city.zonetype;

import org.multiagent_city.enumerations.Color;
import org.multiagent_city.enumerations.Texture;

public abstract class ZoneType {


    public ZoneType(String name, Color color, Texture texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
    }



    private String name;
    private Color color;
    private Texture texture;

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
        return "ZoneType{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", texture=" + texture +
                '}';
    }
}
