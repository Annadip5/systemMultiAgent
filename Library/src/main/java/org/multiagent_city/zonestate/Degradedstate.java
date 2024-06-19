package org.multiagent_city.zonestate;

import org.multiagent_city.environment.Zone;

public class Degradedstate extends ZoneState{
    public Degradedstate(int duration, Zone zone) {
        super(duration, zone);
    }
    @Override
    public void nextState() {
        EmptyState state = new EmptyState(this.duration, this.zone);
        this.zone.setZoneState(state);
        deleteInfrastructure();
    }

}
