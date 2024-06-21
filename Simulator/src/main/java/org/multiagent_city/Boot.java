package org.multiagent_city;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.multiagent_city.agents.buildings.Dwelling;
import org.multiagent_city.agents.buildings.Hospital;
import org.multiagent_city.agents.buildings.Mall;
import org.multiagent_city.agents.buildings.School;
import org.multiagent_city.controller.SimulatorController;
import org.multiagent_city.model.Simulator;
import org.multiagent_city.utils.FastNoiseLite;
import org.multiagent_city.utils.strategy.StrategyRandom;
import org.multiagent_city.view.SimulatorView;
import org.multiagent_city.view.MapView;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Boot extends Game {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private MapView mapView;
    private int mapWidth = 50;
    private int mapHeight = 50;
    private int cellSize = 20;
    private SimulatorController simulatorController;
    private Random random = new Random();
    private int counter = 0;
    private ScheduledExecutorService scheduler;
    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, mapWidth * cellSize, mapHeight * cellSize);
        shapeRenderer = new ShapeRenderer();

        Simulator simulator = new Simulator();
        SimulatorView simulatorView = new SimulatorView();
        this.simulatorController = new SimulatorController(simulatorView, simulator);
        this.mapView = new MapView(mapWidth, mapHeight);
        this.simulatorController.addObserver(this.mapView);
        this.simulatorController.setMapSize(mapWidth,mapHeight);

        // Create noise map for nature
        FastNoiseLite noise = new FastNoiseLite(1);
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        noise.SetFrequency(0.5f);

        int blurRadius = 1;
        this.simulatorController.createSimulation(noise, blurRadius);
        this.simulatorController.setTownHallPosition(15,35);

        // Schedule infrastructure building
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::buildInfrastructures, 0, 100, TimeUnit.MILLISECONDS);
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
                java.awt.Color awtColor = mapView.getUiMap()[x][y];
                Color color = new Color(awtColor.getRed() / 255f, awtColor.getGreen() / 255f, awtColor.getBlue() / 255f, 1);
                shapeRenderer.setColor(color);
                shapeRenderer.rect(y * cellSize, x * cellSize, cellSize, cellSize);
            }
        }
        shapeRenderer.end();
    }

    public void buildInfrastructures() {
        if (this.counter >= 50) {
            scheduler.shutdown();
            return;
        }
        // Create agents
        this.simulatorController.addRoad(new StrategyRandom());
        // Add building with randomness
        int randomValue = random.nextInt(8);
        switch (randomValue) {
            case 0 -> this.simulatorController.addBuilding(new StrategyRandom(), Dwelling.class);
            case 1 -> this.simulatorController.addBuilding(new StrategyRandom(), Hospital.class);
            case 2 -> this.simulatorController.addBuilding(new StrategyRandom(), School.class);
            case 3 -> this.simulatorController.addBuilding(new StrategyRandom(), Mall.class);
            default -> {
            }
        }
        this.counter++;
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
