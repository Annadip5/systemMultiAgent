package org.multiagent_city.agents;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.environment.Map;
import org.multiagent_city.environment.Zone;
import org.multiagent_city.infrastructure.InfrastructureType;
import org.multiagent_city.utils.Position;

import java.util.LinkedList;
import java.util.List;

public class Road extends Infrastructure {

    List<Position> RoadsPosition;

    public Road(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth, Position position) {
        super(type, usuryCoefficient, minHealth, maxHealth, position);
        this.RoadsPosition = new LinkedList<>();
    }

    public Road(InfrastructureType type, Position position) {

        super(type, position);
        this.RoadsPosition = new LinkedList<>();
    }

    public void generate(Map map,Position roadPosition){

        map.getZones()[roadPosition.getX()][roadPosition.getY()].setInfrastructure(this);
        this.RoadsPosition.add(new Position());

    }

    public Boolean checkBuildRule(Map map, Position positionToCheck){
        if(map.isZoneBuildable(positionToCheck)) {

            Position positionUp = new Position(positionToCheck.getX(), positionToCheck.getY() + 1);
            Position positionDown = new Position(positionToCheck.getX(), positionToCheck.getY() - 1);
            Position positionLeft = new Position(positionToCheck.getX() - 1, positionToCheck.getY());
            Position positionRight = new Position(positionToCheck.getX() + 1, positionToCheck.getY());

            boolean isPresentUp = RoadsPosition.stream().anyMatch(element -> element.equals(positionUp));
            boolean isPresentDown = RoadsPosition.stream().anyMatch(element -> element.equals(positionDown));
            boolean isPresentLeft = RoadsPosition.stream().anyMatch(element -> element.equals(positionLeft));
            boolean isPresentRight = RoadsPosition.stream().anyMatch(element -> element.equals(positionRight));

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
        }
         return false;



    }

}
