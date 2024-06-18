package org.multiagent_city.zonestate;

import org.multiagent_city.environment.Zone;

public class EmptyState extends ZoneState{

    public EmptyState(Zone zone){
        super(0, zone);
    }
    public EmptyState(int duration, Zone zone) {
        super(duration, zone);
    }

    @Override
    public void nextState() {
        LockedState state = new LockedState(this.duration, this.zone);
        this.zone.setZoneState(state);
    }
}
