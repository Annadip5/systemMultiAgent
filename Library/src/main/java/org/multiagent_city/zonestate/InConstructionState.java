package org.multiagent_city.zonestate;

import org.multiagent_city.environment.Zone;

public class InConstructionState extends ZoneState{
    public InConstructionState(int duration, Zone zone) {
        super(duration, zone);
    }

    @Override
    public void nextState(int duration) {
        BuildedState state = new BuildedState(duration, this.zone);
        this.zone.setZoneState(state);
    }
}
