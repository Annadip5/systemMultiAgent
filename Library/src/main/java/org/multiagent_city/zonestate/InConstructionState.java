package org.multiagent_city.zonestate;

import org.multiagent_city.environment.Zone;
import org.multiagent_city.utils.Texture;

public class InConstructionState extends ZoneState{
    public InConstructionState(int duration, Zone zone) {
        super(duration, zone, Texture.IN_CONSTRUCTION_STATE);
    }

    @Override
    public void nextState(int duration) {
        BuildedState state = new BuildedState(duration, this.zone);
        this.zone.setZoneState(state);
    }
}
