package org.multiagent_city.zoneState;

public class BuildedState extends ZoneState{

    public BuildedState(int duration, Zone zone) {
        super(duration, zone);
    }
    @Override
    public void nextState() {
        BuildedState state = new BuildedState(this.duration, this.zone);
        this.zone.setZoneState(state);

    }
}
