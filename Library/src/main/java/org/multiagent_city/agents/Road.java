package org.multiagent_city.agents;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.infrastructure.InfrastructureType;

public class Road extends Infrastructure {
    public Road(InfrastructureType type, float usuryCoefficient, int minHealth, int maxHealth) {
        super(type, usuryCoefficient, minHealth, maxHealth);
    }

    public Road(InfrastructureType type) {
        super(type);
    }
}
