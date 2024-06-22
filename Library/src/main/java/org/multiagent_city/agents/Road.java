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

    public Road(int minHealth, int maxHealth, float usuryCoefficient) {
        super(InfrastructureFactory.getInfrastructureType("Road", new Color(0,0,0), Texture.road), minHealth, maxHealth, usuryCoefficient);
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

            return isPresentUp || isPresentDown || isPresentLeft || isPresentRight;
        }
        Position positionUp = new Position(positionToCheck.getX(), positionToCheck.getY() + 1);
        Position positionDown = new Position(positionToCheck.getX(), positionToCheck.getY() - 1);
        Position positionLeft = new Position(positionToCheck.getX() - 1, positionToCheck.getY());
        Position positionRight = new Position(positionToCheck.getX() + 1, positionToCheck.getY());

        boolean isPresentUp = roadsPosition.stream().anyMatch(element -> element.isEqual(positionUp));
        boolean isPresentDown = roadsPosition.stream().anyMatch(element -> element.isEqual(positionDown));
        boolean isPresentLeft = roadsPosition.stream().anyMatch(element -> element.isEqual(positionLeft));
        boolean isPresentRight = roadsPosition.stream().anyMatch(element -> element.isEqual(positionRight));

        // Vérifie les diagonales pour éviter un carré 2x2 de routes
        Position positionUpLeft = new Position(positionToCheck.getX() - 1, positionToCheck.getY() + 1);
        Position positionUpRight = new Position(positionToCheck.getX() + 1, positionToCheck.getY() + 1);
        Position positionDownLeft = new Position(positionToCheck.getX() - 1, positionToCheck.getY() - 1);
        Position positionDownRight = new Position(positionToCheck.getX() + 1, positionToCheck.getY() - 1);

        boolean isPresentUpLeft = roadsPosition.stream().anyMatch(element -> element.isEqual(positionUpLeft));
        boolean isPresentUpRight = roadsPosition.stream().anyMatch(element -> element.isEqual(positionUpRight));
        boolean isPresentDownLeft = roadsPosition.stream().anyMatch(element -> element.isEqual(positionDownLeft));
        boolean isPresentDownRight = roadsPosition.stream().anyMatch(element -> element.isEqual(positionDownRight));

        // Vérifie les combinaisons qui pourraient former un carré 2x2 de routes
        if ((isPresentUp && isPresentLeft && isPresentUpLeft) ||
                (isPresentUp && isPresentRight && isPresentUpRight) ||
                (isPresentDown && isPresentLeft && isPresentDownLeft) ||
                (isPresentDown && isPresentRight && isPresentDownRight) ||
                (isPresentUp && isPresentDown && (isPresentLeft || isPresentRight)) ||
                (isPresentLeft && isPresentRight && (isPresentUp || isPresentDown))) {
            return false;
        }

        // Vérifie si la position est adjacente à une route
        return isPresentUp || isPresentDown || isPresentLeft || isPresentRight;
    }

}
