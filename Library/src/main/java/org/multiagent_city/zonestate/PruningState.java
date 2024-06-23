package org.multiagent_city.zonestate;

import org.multiagent_city.environment.Zone;
import org.multiagent_city.utils.Texture;

public class PruningState extends ZoneState{
    public PruningState(int duration, Zone zone) {
        super(duration, zone, Texture.PRUNING_STATE);
    }
    @Override
    public void nextState(int duration) {
        InConstructionState state = new InConstructionState(duration, this.zone);
        this.zone.setZoneState(state);
    }
}
