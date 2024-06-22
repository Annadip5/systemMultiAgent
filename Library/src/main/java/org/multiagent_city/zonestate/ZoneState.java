package org.multiagent_city.zonestate;

import org.multiagent_city.environment.Zone;
import org.multiagent_city.utils.Texture;

public abstract class ZoneState {
    protected Zone zone;
    protected int duration;
    protected double currentTime;
    protected String texture;

    public ZoneState(int duration, Zone zone, String texture) {
        this.duration = duration;
        this.currentTime = duration;
        this.zone = zone;
        this.texture = texture;
    }

    // Getters & Setters
    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    public void removeCurrentTime(double deltaTime) {
        this.currentTime -= deltaTime;

        if(this.currentTime < 0 || this.currentTime == 0) {
            this.currentTime = 0;
            this.nextState(0);
        }
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    // Methods
    public abstract void nextState(int duration) ;
    public void deleteInfrastructure() {
        this.zone.deleteInfrastructure();
    }

}
