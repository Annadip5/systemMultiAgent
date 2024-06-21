package org.multiagent_city.agents;

import org.multiagent_city.environment.Map;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;

public abstract class Infrastructure {
    private InfrastructureType type;
    protected float usuryCoefficient;
    protected int minHealth;
    protected int maxHealth;
    protected Position position;
    public Infrastructure(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, Position position) {
        this.type = type;
        this.usuryCoefficient = usuryCoefficient;
        this.minHealth = minHealth;
        this.maxHealth = maxHealth;
        this.position = position;
    }

    public Infrastructure(InfrastructureType type) {
        this.type = type;
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
                ", minHealth=" + minHealth +
                ", maxHealth=" + maxHealth +
                ", position=" + position.toString() +
                '}';
    }
    public abstract Boolean checkSpecificRule(Map map, Position positionToCheck);


    public boolean isInMap(Position positionToCheck, int mapWidth, int mapHeight) {
        return positionToCheck.getX() >= 0 && positionToCheck.getX() < mapWidth && positionToCheck.getY() >= 0 && positionToCheck.getY() < mapHeight;
    }
    public Boolean checkBuildRule(Map map, Position positionToCheck){
        System.out.println( "" +this.isInMap(positionToCheck,map.getWidth(),map.getHeight()) + this.checkSpecificRule(map, positionToCheck) +map.isZoneBuildable(positionToCheck));

        return this.isInMap(positionToCheck,map.getWidth(),map.getHeight()) && this.checkSpecificRule(map, positionToCheck) && map.isZoneBuildable(positionToCheck);
    }
}
