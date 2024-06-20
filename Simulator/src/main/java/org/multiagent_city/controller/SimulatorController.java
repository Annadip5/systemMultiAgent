package org.multiagent_city.controller;

/*import javafx.event.ActionEvent;
import javafx.event.EventHandler;*/
import org.multiagent_city.agents.buildings.TownHall;
import org.multiagent_city.environment.Map;
import org.multiagent_city.model.Simulator;
import org.multiagent_city.utils.FastNoiseLite;
import org.multiagent_city.utils.Position;
import org.multiagent_city.view.SimulatorView;

import java.awt.*;

public class SimulatorController {
    private SimulatorView view;
    private Simulator simulator ;
    public SimulatorController(SimulatorView view, Simulator simulator) {
        this.view = view;
        this.simulator = simulator;

    }

    public SimulatorView getView() {
        return view;
    }

    public void setView(SimulatorView view) {
        this.view = view;
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }
    public void setMapSize(int width, int height) {
        this.simulator.getMap().setHeight(height);
        this.simulator.getMap().setWidth(width);
    }
    public void setTownHallPosition(int x, int y){
          this.simulator.getMap().setTownHall(new TownHall(x, y));
    }
    public void createSimulation(FastNoiseLite noise, int blurRadius) {
        this.simulator.getMap().buildMap(noise, blurRadius);

    }

    public Color getNatureColorFromZone(int x, int y) {
        TownHall townHall = this.simulator.getMap().getTownHall();
        if (townHall.getPosition().isEqual(x, y)) {
            return townHall.getType().getColor();
        }
        return this.simulator.getMap().getZones()[x][y].getNature().getColor();
    }
    public void updateView(){
        this.view.printMap(this.simulator.getMap());
    }


}