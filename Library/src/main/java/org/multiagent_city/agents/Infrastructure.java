package org.multiagent_city.agents;

import org.multiagent_city.environment.Map;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;

import java.util.Random;

public abstract class Infrastructure {
    private InfrastructureType type;
    protected float usuryCoefficient;
    protected double currentUsury;
    protected int minHealth;
    protected int maxHealth;
    protected int health;
    protected Position position;
    public Infrastructure(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, Position position) {
        this.type = type;
        this.usuryCoefficient = usuryCoefficient;
        this.currentUsury = 0;
        this.minHealth = minHealth;
        this.maxHealth = maxHealth;
        Random random = new Random();
        this.health = random.nextInt(minHealth, maxHealth + 1);
        this.position = position;
    }

    public Infrastructure(InfrastructureType type) {
        this.type = type;
    }

    public Infrastructure(InfrastructureType type, int minHealth, int maxHealth, float usuryCoefficient) {
        this.type = type;
        this.usuryCoefficient = usuryCoefficient;
        this.currentUsury = 0;
        this.minHealth = minHealth;
        this.maxHealth = maxHealth;
        Random random = new Random();
        this.health = random.nextInt(minHealth, maxHealth + 1);
    }

    public Infrastructure(InfrastructureType type, Position position) {
        this.type = type;
        this.position = position;
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

    public double getCurrentUsury() {
        return currentUsury;
    }

    public void setCurrentUsury(double currentUsury) {
        this.currentUsury = currentUsury;
    }

    public void addCurrentUsury(double deltaTime) {
        this.currentUsury += this.usuryCoefficient * deltaTime;

        if(this.currentUsury >= this.health) {
            this.currentUsury = this.health;
        }
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Infrastructure{" +
                "type=" + type.getName() +
                ", usuryCoefficient=" + usuryCoefficient +
                ", currentUsury=" + currentUsury +
                ", minHealth=" + minHealth +
                ", maxHealth=" + maxHealth +
                ", health=" + health +
                ", position=" + position.toString() +
                '}';
    }
    public abstract Boolean checkSpecificRule(Map map, Position positionToCheck);


    public boolean isInMap(Position positionToCheck, int mapWidth, int mapHeight) {
        return positionToCheck.getX() >= 0 && positionToCheck.getX() < mapWidth && positionToCheck.getY() >= 0 && positionToCheck.getY() < mapHeight;
    }
    public Boolean checkBuildRule(Map map, Position positionToCheck){

        return this.isInMap(positionToCheck,map.getWidth(),map.getHeight()) && this.checkSpecificRule(map, positionToCheck) && map.isZoneBuildable(positionToCheck);
    }
}
