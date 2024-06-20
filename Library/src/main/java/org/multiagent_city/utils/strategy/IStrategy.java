package org.multiagent_city.utils.strategy;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.environment.Map;
import org.multiagent_city.environment.Zone;
import org.multiagent_city.utils.Position;

public interface IStrategy {
    Position execute(Map map, Infrastructure infrastructure );
}
