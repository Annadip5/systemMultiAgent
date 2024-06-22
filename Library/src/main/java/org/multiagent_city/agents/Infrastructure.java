package org.multiagent_city.agents;

import org.multiagent_city.environment.Map;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;

import java.util.ArrayList;
import java.util.List;
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

        if (this.currentUsury >= this.health) {
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

    public Boolean checkBuildRule(Map map, Position positionToCheck) {

        return this.isInMap(positionToCheck, map.getWidth(), map.getHeight()) && this.checkSpecificRule(map, positionToCheck) && map.isZoneBuildable(positionToCheck);
    }

    public boolean checkNeighborPosition(Map map, Position positionToCheck) {
        int x = positionToCheck.getX();
        int y = positionToCheck.getY();
        int width = map.getWidth();
        int height = map.getHeight();

        boolean isPresentUp = false, isPresentDown = false, isPresentLeft = false, isPresentRight = false;
        if (isInMap(new Position(x, y + 1), width, height)) {
            isPresentUp = map.getZones()[x][y + 1].getInfrastructure() instanceof Road;
        }
        // Check bottom position
        if (isInMap(new Position(x, y - 1), width, height)) {
            isPresentDown = map.getZones()[x][y - 1].getInfrastructure() instanceof Road;
        }
        // Check left position
        if (isInMap(new Position(x - 1, y), width, height)) {
            isPresentLeft = map.getZones()[x - 1][y].getInfrastructure() instanceof Road;
        }
        // Check right position
        if (isInMap(new Position(x + 1, y), width, height)) {
            isPresentRight = map.getZones()[x + 1][y].getInfrastructure() instanceof Road;
        }

        return isPresentUp || isPresentDown || isPresentLeft || isPresentRight;
    }

    public List<Infrastructure> isNotAlone(Map map, List<Position> positionAlreadyChecked) {
        List<Position> positionsInMap = new ArrayList<Position>();
        List<Infrastructure> infrastructuresToRemove = new ArrayList<>();

        int width = map.getWidth();
        int height = map.getHeight();
        int x = this.position.getX();
        int y = this.position.getY();
        Position posNeighborDown = new Position(x, y + 1);
        Position posNeighborUp = new Position(x, y - 1);
        Position posNeighborRight = new Position(x + 1, y);
        Position posNeighborLeft = new Position(x - 1, y);

        if (this.isInMap(posNeighborDown, width, height)) {
            positionsInMap.add(posNeighborDown);
        }
        if (this.isInMap(posNeighborUp, width, height)) {
            positionsInMap.add(posNeighborUp);
        }
        if (this.isInMap(posNeighborLeft, width, height)) {
            positionsInMap.add(posNeighborLeft);
        }
        if (this.isInMap(posNeighborRight, width, height)) {
            positionsInMap.add(posNeighborRight);
        }

        for (Position pos : positionsInMap) {
            Infrastructure infra = map.getZones()[pos.getX()][pos.getY()].getInfrastructure();
            if (infra != null && !this.checkNeighborPosition(map, pos) && !positionAlreadyChecked.contains(pos)) {
                infrastructuresToRemove.add(infra);
                positionAlreadyChecked.add(pos);
            }
        }

        /*List<Infrastructure> nestedInfrastructuresToRemove = new ArrayList<>();
        for (Infrastructure infra : infrastructuresToRemove) {
            nestedInfrastructuresToRemove.addAll(infra.isNotAlone(map, positionAlreadyChecked));
        }
        infrastructuresToRemove.addAll(nestedInfrastructuresToRemove);*/

        return infrastructuresToRemove;
    }


}
