package org.multiagent_city.zonestate;

import org.multiagent_city.agents.Road;
import org.multiagent_city.environment.Zone;

public class Degradedstate extends ZoneState{
    public Degradedstate(int duration, Zone zone) {
        super(duration, zone, null);
    }
    @Override
    public void nextState(int duration) {
        if (!(zone.getInfrastructure() instanceof Road)) {
            EmptyState state = new EmptyState(duration, this.zone);
            this.zone.setZoneState(state);
            deleteInfrastructure();
        }
    }
}
