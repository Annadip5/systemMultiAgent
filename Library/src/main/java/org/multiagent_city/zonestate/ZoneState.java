package org.multiagent_city.zonestate;

import org.multiagent_city.environment.Zone;

public abstract class ZoneState {
    protected Zone zone;
    protected int duration;

    public ZoneState(int duration, Zone zone) {
        this.duration = duration;
        this.zone = zone;
    }

    public abstract void nextState() ;
    public void deleteInfrastructure() {
        this.zone.deleteInfrastructure();
    }

}
