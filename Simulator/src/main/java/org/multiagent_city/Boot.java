package org.multiagent_city;

import com.badlogic.gdx.Game;
import org.multiagent_city.controller.SimulatorController;
import org.multiagent_city.model.Simulator;
import org.multiagent_city.view.SimulatorView;

public class Boot extends Game {
    @Override
    public void create() {
        Simulator simulator = new Simulator();
        SimulatorView simulatorView = new SimulatorView();
        SimulatorController simulatorController = new SimulatorController(simulatorView, simulator);
        simulatorController.setMapSize(7,5);
        simulatorController.setTownHallPosition(3,4);
        simulatorController.createSimulation();
        simulatorController.updateView();


    }
}
