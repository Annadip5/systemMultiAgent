package org.multiagent_city.environment;

import java.util.List;

public abstract class Observable {
    protected List<IObserver> observers;

    public Observable() {
    }

    public void addObservers(IObserver observer) {
        this.observers.add(observer);
    }

    public void deleteObservers(IObserver observer) {
        this.observers.remove(observer);
    }

    // Methods
    public void notify(Zone[][] zones) {
        for (IObserver obs : this.observers) {
            obs.update(zones);
        }
    }
}
