package org.multiagent_city.agents;

import org.multiagent_city.infrastructure.InfrastructureType;

public abstract class Infrastructure {
    private InfrastructureType type;

    public Infrastructure(InfrastructureType type) {
        this.type = type;


    }
}
