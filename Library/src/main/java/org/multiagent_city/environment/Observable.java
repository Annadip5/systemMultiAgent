package org.multiagent_city.environment;

import org.multiagent_city.utils.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    protected List<IObserver> observers;

    public Observable() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }

    public void deleteObserver(IObserver observer) {
        this.observers.remove(observer);
    }

    // Methods
    public void notify(Map map, Position updatedPostion) {
        for (IObserver obs : this.observers) {
            obs.update(map, updatedPostion);
        }
    }
}
