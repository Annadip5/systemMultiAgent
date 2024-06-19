package org.multiagent_city.nature;

public abstract class Nature {
    protected String name;
    protected boolean buildable;

    public Nature(String name, boolean buildable) {
        this.name = name;
        this.buildable = buildable;
    }

    public String getName() {
        return name;
    }

    public boolean isBuildable() {
        return buildable;
    }
}
