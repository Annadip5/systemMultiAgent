package org.multiagent_city.zonestate;

import org.multiagent_city.environment.Zone;
import org.multiagent_city.nature.nature_elements.Bush;
import org.multiagent_city.nature.nature_elements.Tree;
import org.multiagent_city.utils.Texture;

public class LockedState extends ZoneState{
    public LockedState(int duration, Zone zone) {
        super(duration, zone, Texture.lockedState);
    }
    @Override
    public void nextState(int duration) {
        if ( (this.zone.getNature() instanceof Tree) || (this.zone.getNature() instanceof Bush) ){
            PruningState state = new PruningState(duration, this.zone);
            this.zone.setZoneState(state);
        } else{
            InConstructionState state = new InConstructionState(duration, this.zone);
            this.zone.setZoneState(state);
        }

    }
}
