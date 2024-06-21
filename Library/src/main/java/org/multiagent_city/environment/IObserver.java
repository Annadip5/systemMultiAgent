package org.multiagent_city.environment;

import org.multiagent_city.utils.Position;

public interface IObserver {
    void update(Map map, Position updatedPostion);
}
