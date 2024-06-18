package org.multiagent_city.environment;

public class Zone {
    private ZoneState state;
    private Infrastructure infrastructure;
    private Nature nature;

    public Zone() {
        this.state = new EmptyState();
    }

    public Zone(Nature nature) {
        this.state = new EmptyState();
        this.nature = nature;
    }

    public ZoneState getState() {
        return state;
    }
    public void setState(ZoneState state) {
        this.state = state;
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
