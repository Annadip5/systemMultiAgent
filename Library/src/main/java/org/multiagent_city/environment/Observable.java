package org.multiagent_city.environment;

import java.util.List;

public abstract class Observable {
    protected List<Observer> observers;

    public Observable() {
    }

    public void addObservers(Observer observer) {
        this.observers.add(observer);
    }

    public void deleteObservers(Observer observer) {
        this.observers.remove(observer);
    }

    // Methods
    public void notify(Zone[][] zones) {
        for (Observer obs : this.observers) {
            obs.update(zones);
        }
    }
}
