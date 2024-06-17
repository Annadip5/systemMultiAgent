package org.system;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLaucher {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setIdleFPS(60);
        config.useVsync(true);
        config.setTitle("Simulator");

        // Window
        config.setWindowedMode(960,640);
        //config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

        new Lwjgl3Application(new Boot(), config);
    }
}
