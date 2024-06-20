package org.multiagent_city.utils.strategy;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.environment.Map;
import org.multiagent_city.environment.Zone;
import org.multiagent_city.utils.Position;

import java.util.ArrayList;
import java.util.Random;

public class StrategyRandom implements IStrategy {
    public Position execute(Map map, Infrastructure infrastructure ) {
        Random random = new Random();
        Position pos = new Position();
        ArrayList<Position> positions = new ArrayList<Position>();
        do {
            if( positions.size() == map.getHeight()* map.getWidth()){
                System.out.println("all positions are not buildable");
                break;
            }
            do {
                int X = random.nextInt(map.getWidth());
                int Y = random.nextInt(map.getHeight());
                pos =  new Position(X,Y);
            }
            while (pos.isContained(positions) );

            positions.add(pos);


        }
        while(!infrastructure.checkBuildRule(map,pos));
        return pos;

    }
}
