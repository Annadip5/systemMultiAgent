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

public class Road extends Infrastructure {

    List<Position> roadsPosition;

    public Road(float usuryCoefficient, int minHealth, int maxHealth, Position position) {
        super(InfrastructureFactory.getInfrastructureType("Road", new Color(0,0,0), Texture.road), usuryCoefficient, minHealth, maxHealth, position);
        this.roadsPosition = new LinkedList<>();
    }

    public Road(InfrastructureType type, Position position) {

        super(type, position);
        this.roadsPosition = new LinkedList<>();
    }



    public void generate(Map map,Position roadPosition){
        this.roadsPosition.add(roadPosition);
        map.getZones()[roadPosition.getX()][roadPosition.getY()].setInfrastructure(this);


    }

    @Override
    public Boolean checkSpecificRule(Map map, Position positionToCheck) {
        if(!map.isZoneBuildable(positionToCheck)) {
            return false;
        }
        if(roadsPosition.size()==0){
            Position positionUp = new Position(positionToCheck.getX(), positionToCheck.getY() + 1);
            Position positionDown = new Position(positionToCheck.getX(), positionToCheck.getY() - 1);
            Position positionLeft = new Position(positionToCheck.getX() - 1, positionToCheck.getY());
            Position positionRight = new Position(positionToCheck.getX() + 1, positionToCheck.getY());

            boolean isPresentUp = map.getTownHall().getPosition().equals(positionUp);
            boolean isPresentDown = map.getTownHall().getPosition().equals(positionDown);
            boolean isPresentLeft = map.getTownHall().getPosition().equals(positionLeft);
            boolean isPresentRight = map.getTownHall().getPosition().equals(positionRight);

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

        boolean isPresentUp = roadsPosition.stream().anyMatch(element -> element.equals(positionUp));
        boolean isPresentDown = roadsPosition.stream().anyMatch(element -> element.equals(positionDown));
        boolean isPresentLeft = roadsPosition.stream().anyMatch(element -> element.equals(positionLeft));
        boolean isPresentRight = roadsPosition.stream().anyMatch(element -> element.equals(positionRight));

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
