package org.multiagent_city.environment;

import org.multiagent_city.utils.Position;
import org.multiagent_city.utils.strategy.IStrategy;

public class MapContext {

    private IStrategy strategy;

    public MapContext() {
    }
    public MapContext(IStrategy strategy) {
        this.strategy = strategy;
    }

    public IStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    // Method
    public void execute(Zone[][] zones, Position townHallPosition) {
        this.strategy.execute(zones, townHallPosition);
    }
}
