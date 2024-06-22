package org.multiagent_city.zonestate;

import org.multiagent_city.environment.Zone;

public class PruningState extends ZoneState{
    public PruningState(int duration, Zone zone) {
        super(duration, zone);
    }
    @Override
    public void nextState(int duration) {
        InConstructionState state = new InConstructionState(duration, this.zone);
        this.zone.setZoneState(state);
    }
}
