package org.multiagent_city.view;

/*
import javafx.application.Application;
//import javafx.scene.Scene;
import javafx.stage.Stage;
//import org.system.controller.SimulatorController;
*/
public class Main /*extends Application*/{
    /*@Override
    public void start(Stage primaryStage) {
        primaryStage.show();
    }*/

    public static void main(String[] args) {
        String appName = LibraryClass.getLibName();
        System.out.println("Hello from simulator! "+appName);
        String test = Test.getTest();
        System.out.println(test);
        //launch(args);
    }
    /*public static void main(String[] args) {
        String appName = LibraryClass.getLibName();
        System.out.println("Hello from simulator! "+appName);
        String test = Test.getTest();
        System.out.println(test);
    }*/
}