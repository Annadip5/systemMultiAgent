package org.multiagent_city.zonestate;

import org.multiagent_city.environment.Zone;

public class BuildedState extends ZoneState{

    public BuildedState(int duration, Zone zone) {
        super(duration, zone);
    }
    @Override
    public void nextState() {
        Degradedstate state = new Degradedstate(this.duration, this.zone);
        this.zone.setZoneState(state);

    }
}
