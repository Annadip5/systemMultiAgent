package org.multiagent_city.agents;

import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;

public abstract class Infrastructure {
    private InfrastructureType type;
    protected float usuryCoefficient;
    protected int minHealth;
    protected int maxHealth;
    protected Position position;
    public Infrastructure(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth) {
        this.type = type;
        this.usuryCoefficient = usuryCoefficient;
        this.minHealth = minHealth;
        this.maxHealth = maxHealth;
    }

    public Infrastructure(InfrastructureType type) {
        this.type = type;
    }
    public InfrastructureType getType() {
        return type;
    }

    public void setType(InfrastructureType type) {
        this.type = type;
    }

    public float getUsuryCoefficient() {
        return usuryCoefficient;
    }

    public void setUsuryCoefficient(float usuryCoefficient) {
        this.usuryCoefficient = usuryCoefficient;
    }

    public int getMinHealth() {
        return minHealth;
    }

    public void setMinHealth(int minHealth) {
        this.minHealth = minHealth;
    }



    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public String toString() {
        return "Infrastructure{" +
                "type=" + type +
                ", usuryCoefficient=" + usuryCoefficient +
                ", minHealth=" + minHealth +
                ", maxHealth=" + maxHealth +
                '}';
    }
}
