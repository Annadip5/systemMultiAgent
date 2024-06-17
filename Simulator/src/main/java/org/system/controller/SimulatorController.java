package org.system.controller;

/*import javafx.event.ActionEvent;
import javafx.event.EventHandler;*/
import org.system.view.SimulatorView;

public class SimulatorController {
    private SimulatorView view;

    public SimulatorController(SimulatorView view) {
        this.view = view;
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        /*view.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Simulation Started");
            }
        });

        view.getStopButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Simulation Stopped");
            }
        });*/
    }
}