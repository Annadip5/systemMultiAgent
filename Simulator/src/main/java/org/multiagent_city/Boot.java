package org.multiagent_city;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.multiagent_city.agents.buildings.Dwelling;
import org.multiagent_city.agents.buildings.Hospital;
import org.multiagent_city.agents.buildings.Mall;
import org.multiagent_city.agents.buildings.School;
import org.multiagent_city.controller.SimulatorController;
import org.multiagent_city.model.Simulator;
import org.multiagent_city.utils.FastNoiseLite;
import org.multiagent_city.utils.strategy.StrategyAStar;
import org.multiagent_city.utils.strategy.StrategyRandom;
import org.multiagent_city.view.SimulatorView;

import java.util.Random;

public class Boot extends Game {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private java.awt.Color[][] noiseMap;
    private int mapWidth = 50;
    private int mapHeight = 50;
    private int cellSize = 20;

    private Random random = new Random();
    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, mapWidth * cellSize, mapHeight * cellSize);
        shapeRenderer = new ShapeRenderer();

        Simulator simulator = new Simulator();
        SimulatorView simulatorView = new SimulatorView();
        SimulatorController simulatorController = new SimulatorController(simulatorView, simulator);
        simulatorController.setMapSize(mapWidth,mapHeight);

        // Create noise map for nature
        FastNoiseLite noise = new FastNoiseLite(1);
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        noise.SetFrequency(0.5f);

        int blurRadius = 1;
        simulatorController.createSimulation(noise, blurRadius);
        simulatorController.setTownHallPosition(15,35);

        // Create agents
        for (int i = 0; i < 500; i++) {
            simulatorController.addRoad(new StrategyRandom());
            // Add building with randomness
            int randomValue = random.nextInt(8);
            switch (randomValue) {
                case 0 -> simulatorController.addBuilding(new StrategyRandom(), Dwelling.class);
                case 1 -> simulatorController.addBuilding(new StrategyRandom(), Hospital.class);
                case 2 -> simulatorController.addBuilding(new StrategyRandom(), School.class);
                case 3 -> simulatorController.addBuilding(new StrategyRandom(), Mall.class);
                default -> {
                }
            }
        }

        // Display the colors
        noiseMap = new java.awt.Color[mapHeight][mapWidth];
        for (int x = 0; x < mapHeight; x++) {
            for (int y = 0; y < mapWidth; y++) {
                noiseMap[x][y] = simulatorController.getNatureColorFromZone(x, y);
            }
        }
        //simulatorController.updateView();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < mapHeight; x++) {
            for (int y = 0; y < mapWidth; y++) {
                java.awt.Color awtColor = noiseMap[x][y];
                Color color = new Color(awtColor.getRed() / 255f, awtColor.getGreen() / 255f, awtColor.getBlue() / 255f, 1);
                shapeRenderer.setColor(color);
                shapeRenderer.rect(y * cellSize, x * cellSize, cellSize, cellSize);
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
