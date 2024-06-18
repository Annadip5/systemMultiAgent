package org.multiagent_city.zonestate;

import org.multiagent_city.environment.Zone;

public class PruningState extends ZoneState{
    public PruningState(int duration, Zone zone) {
        super(duration, zone);
    }
    @Override
    public void nextState() {
        InConstructionState state = new InConstructionState(this.duration, this.zone);
        this.zone.setZoneState(state);
    }
}
