package org.multiagent_city.utils.strategy;

import org.multiagent_city.environment.Zone;
import org.multiagent_city.utils.Position;

public interface IStrategy {
    void execute(Zone[][] zones, Position townHallPosition);
}
