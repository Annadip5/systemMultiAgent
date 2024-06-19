package org.multiagent_city.model;

import org.multiagent_city.environment.Map;

public class Simulator {
    private Map map;

    public Map getMap() {
        return map;
    }



    public Simulator() {
        this.map = Map.getInstance();

    }


}
