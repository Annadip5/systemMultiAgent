package org.multiagent_city.zoneState;

public abstract class ZoneState {
    protected Zone zone;
    protected int duration;

    public ZoneState(int duration,Zone zone) {
        this.duration = duration;
        this.zone = zone;
    }

    public abstract void nextState() ;


}
