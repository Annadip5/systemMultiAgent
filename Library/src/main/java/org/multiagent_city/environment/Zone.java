package org.multiagent_city.environment;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.zonestate.EmptyState;
import org.multiagent_city.zonestate.ZoneState;
import org.multiagent_city.nature.Nature;

public class Zone {
    private ZoneState zoneState;
    private Infrastructure infrastructure;
    private Nature nature;

    public Zone() {
        this.zoneState = new EmptyState(this);
    }

    public Zone(Nature nature) {
        this.zoneState = new EmptyState(this);
        this.nature = nature;
    }

    public ZoneState getZoneState() {
        return zoneState;
    }
    public void setZoneState(ZoneState zoneState) {
        this.zoneState = zoneState;
    }

    public Infrastructure getInfrastructure() {
        return infrastructure;
    }
    public void setInfrastructure(Infrastructure infrastructure) {
        this.infrastructure = infrastructure;
    }

    public Nature getNature() {
        return nature;
    }
    public void setNature(Nature nature) {
        this.nature = nature;
    }

    // Methods
    public boolean isOccupied() {
        return this.infrastructure != null;
    }

    public boolean isBuildable() {
        return !this.isOccupied(); // TODO : Add nature into consideration
    }

    public void deleteInfrastructure() {
        this.infrastructure = null;
    }
}
