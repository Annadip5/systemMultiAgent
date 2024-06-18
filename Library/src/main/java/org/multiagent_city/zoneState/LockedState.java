package org.multiagent_city.zoneState;

public class LockedState extends ZoneState{
    public LockedState(int duration, Zone zone) {
        super(duration, zone);
    }
    @Override
    public void nextState() {
        if ( (this.zone.getNature() instanceof Tree) || (this.zone.getNature() instanceof Bush) ){
            PruningState state = new PruningState(this.zone);
            this.zone.setZoneState(state);
        } else{
            InConstructionState state = new InConstructionState(this.duration, this.zone);
            this.zone.setZoneState(state);
        }

    }
}
