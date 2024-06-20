package org.multiagent_city.agents;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.environment.Map;
import org.multiagent_city.environment.Zone;
import org.multiagent_city.infrastructure.InfrastructureFactory;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.Texture;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Road extends Infrastructure {

    public Road() {
        super(InfrastructureFactory.getInfrastructureType("Road", new Color(0,0,0), Texture.road));
    }

    public Road(float usuryCoefficient, int minHealth, int maxHealth, Position position) {
        super(InfrastructureFactory.getInfrastructureType("Road", new Color(0,0,0), Texture.road), usuryCoefficient, minHealth, maxHealth, position);
    }

    public Road(InfrastructureType type, Position position) {
        super(type, position);
    }

    @Override
    public Boolean checkSpecificRule(Map map, Position positionToCheck) {
        if(!map.isZoneBuildable(positionToCheck)) {
            return false;
        }
        List<Position> roadsPosition = map.getRoads().stream().map(element -> element.getPosition()).collect(Collectors.toList());
        if(roadsPosition.size()==0){
            Position positionUp = new Position(positionToCheck.getX(), positionToCheck.getY() + 1);
            Position positionDown = new Position(positionToCheck.getX(), positionToCheck.getY() - 1);
            Position positionLeft = new Position(positionToCheck.getX() - 1, positionToCheck.getY());
            Position positionRight = new Position(positionToCheck.getX() + 1, positionToCheck.getY());

            boolean isPresentUp = map.getTownHall().getPosition().isEqual(positionUp);
            boolean isPresentDown = map.getTownHall().getPosition().isEqual(positionDown);
            boolean isPresentLeft = map.getTownHall().getPosition().isEqual(positionLeft);
            boolean isPresentRight = map.getTownHall().getPosition().isEqual(positionRight);

            if (isPresentUp) {
                return true;
            }
            if (isPresentDown) {
                return true;
            }
            if (isPresentLeft) {
                return true;
            }
            if (isPresentRight) {
                return true;
            }
            return false;


        }
        Position positionUp = new Position(positionToCheck.getX(), positionToCheck.getY() + 1);
        Position positionDown = new Position(positionToCheck.getX(), positionToCheck.getY() - 1);
        Position positionLeft = new Position(positionToCheck.getX() - 1, positionToCheck.getY());
        Position positionRight = new Position(positionToCheck.getX() + 1, positionToCheck.getY());

        boolean isPresentUp = roadsPosition.stream().anyMatch(element -> element.isEqual(positionUp));
        boolean isPresentDown = roadsPosition.stream().anyMatch(element -> element.isEqual(positionDown));
        boolean isPresentLeft = roadsPosition.stream().anyMatch(element -> element.isEqual(positionLeft));
        boolean isPresentRight = roadsPosition.stream().anyMatch(element -> element.isEqual(positionRight));

        if ((isPresentUp && isPresentLeft) || (isPresentUp && isPresentRight) || (isPresentDown && isPresentLeft) || (isPresentDown && isPresentRight)) {
            return false;
        }
        if (isPresentUp) {
            return true;
        }
        if (isPresentDown) {
            return true;
        }
        if (isPresentLeft) {
            return true;
        }
        if (isPresentRight) {
            return true;
        }
        return false;


    }

}
