package org.multiagent_city.utils.strategy;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.environment.Map;
import org.multiagent_city.environment.Zone;
import org.multiagent_city.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategyRandom implements IStrategy {
    public Position execute(Map map, Infrastructure infrastructure ) {
        Random random = new Random();
        Position pos;
        List<Position> possiblePositions = new ArrayList<>();
        for(int x = 0; x < map.getHeight(); x++){
            for(int y = 0; y < map.getWidth(); y++){
                possiblePositions.add(new Position(x,y));
            }
        }

        do {
            if( possiblePositions.size() == 0){
                System.out.println("all positions are not buildable");
                return null;
            }
            int randomIndex = random.nextInt(possiblePositions.size());
            pos = possiblePositions.get(randomIndex);

            possiblePositions.remove(pos);
        }
        while(!infrastructure.checkBuildRule(map,pos));
        return pos;
    }
}
