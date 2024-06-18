package org.multiagent_city.zoneState;

public class EmptyState extends ZoneState{

    public EmptyState(int duration, Zone zone) {
        super(duration, zone);
    }

    @Override
    public void nextState() {
        LockedState state = new LockedState(this.duration, this.zone);
        this.zone.setZoneState(state);
    }
}
