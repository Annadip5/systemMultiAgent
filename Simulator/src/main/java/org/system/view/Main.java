package org.system.view;

import org.system.LibraryClass;
import org.system.Test;
import javafx.application.Application;
//import javafx.scene.Scene;
import javafx.stage.Stage;
//import org.system.controller.SimulatorController;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) {
        primaryStage.show();
    }

    public static void main(String[] args) {
        String appName = LibraryClass.getLibName();
        System.out.println("Hello from simulator! "+appName);
        String test = Test.getTest();
        System.out.println(test);
        launch(args);
    }
    /*public static void main(String[] args) {
        String appName = LibraryClass.getLibName();
        System.out.println("Hello from simulator! "+appName);
        String test = Test.getTest();
        System.out.println(test);
    }*/
}