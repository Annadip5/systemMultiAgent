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
import org.multiagent_city.view.MapView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Boot extends Game {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private MapView mapView;
    private int mapWidth = 100;
    private int mapHeight = 100;
    private int cellSize = 20;
    private SimulatorController simulatorController;
    private Random random = new Random();
    private int counter = 0;
    private float elapsedTime = 100;
    private float updateInterval = 0.010f;

    private Map<String, Texture> textureMap;

    @Override
    public void create() {
        this.loadTextures();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, mapWidth * cellSize, mapHeight * cellSize);
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        Simulator simulator = new Simulator();
        SimulatorView simulatorView = new SimulatorView();
        this.simulatorController = new SimulatorController(simulatorView, simulator);
        this.mapView = new MapView(simulator.getMap());
        this.simulatorController.addObserver(this.mapView);
        this.simulatorController.setMapSize(mapWidth,mapHeight);

        // Create noise map for nature
        FastNoiseLite noise = new FastNoiseLite(1);
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        noise.SetFrequency(0.5f);

        int blurRadius = 1;
        this.simulatorController.createSimulation(noise, blurRadius);
        this.simulatorController.setTownHallPosition(15,35);

    }

    private void loadTextures() {
        textureMap = new HashMap<>();
        // Infrastructures
        textureMap.put(org.multiagent_city.utils.Texture.road, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.road)));
        textureMap.put(org.multiagent_city.utils.Texture.dwelling, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.dwelling)));
        textureMap.put(org.multiagent_city.utils.Texture.school, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.school)));
        textureMap.put(org.multiagent_city.utils.Texture.hospital, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.hospital)));
        textureMap.put(org.multiagent_city.utils.Texture.mall, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.mall)));
        textureMap.put(org.multiagent_city.utils.Texture.townHall, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.townHall)));

        // Nature
        textureMap.put(org.multiagent_city.utils.Texture.water, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.water)));
        textureMap.put(org.multiagent_city.utils.Texture.rock, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.rock)));
        textureMap.put(org.multiagent_city.utils.Texture.tree, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.tree)));
        textureMap.put(org.multiagent_city.utils.Texture.bush, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.bush)));
        textureMap.put(org.multiagent_city.utils.Texture.grass, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.grass)));

        // ZoneState
        textureMap.put(org.multiagent_city.utils.Texture.pruningState, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.pruningState)));
        textureMap.put(org.multiagent_city.utils.Texture.lockedState, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.lockedState)));
        textureMap.put(org.multiagent_city.utils.Texture.inConstructionState, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.inConstructionState)));
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        elapsedTime += deltaTime;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);

        // Draw the background colors first
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < mapHeight; x++) {
            for (int y = 0; y < mapWidth; y++) {
                java.awt.Color awtColor = mapView.getUiMap()[x][y].getColor();
                Color color = new Color(awtColor.getRed() / 255f, awtColor.getGreen() / 255f, awtColor.getBlue() / 255f, 1);
                shapeRenderer.setColor(color);
                shapeRenderer.rect(y * cellSize, x * cellSize, cellSize, cellSize);
            }
        }
        shapeRenderer.end();

        // Draw the textures on top of the background colors
        spriteBatch.begin();
        for (int x = 0; x < mapHeight; x++) {
            for (int y = 0; y < mapWidth; y++) {
                Texture texture = this.textureMap.get(mapView.getUiMap()[x][y].getTexture());
                if (texture != null) {
                    //spriteBatch.draw(texture, y * cellSize, x * cellSize, cellSize, cellSize);
                    float originX = cellSize / 2;
                    float originY = cellSize / 2;
                    float rotation = 180.0f;
                    spriteBatch.draw(texture, y * cellSize, x * cellSize, originX, originY, cellSize, cellSize, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), true, false);
                }
            }
        }
        spriteBatch.end();

        // Update infrastructures at set intervals
        if (elapsedTime >= updateInterval && this.counter <= 30000) {
            this.buildInfrastructures();
            this.counter++;
            elapsedTime = 0;
        }

        // Update the map
        this.simulatorController.updateView(deltaTime);
    }

    public void buildInfrastructures() {
        // Create agents
        this.simulatorController.addRoad(new StrategyAStar(), 50, 100, 7);
        // Add building with randomness
        int randomValue = random.nextInt(8);
        switch (randomValue) {
            case 0 -> this.simulatorController.addBuilding(new StrategyRandom(), Dwelling.class, 100, 150, 2);
            case 1 -> this.simulatorController.addBuilding(new StrategyRandom(), Hospital.class, 100, 150, 2);
            case 2 -> this.simulatorController.addBuilding(new StrategyRandom(), School.class, 100, 150, 2);
            case 3 -> this.simulatorController.addBuilding(new StrategyRandom(), Mall.class, 100, 150, 3);
            default -> {
            }
        }
        this.counter++;
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        for (Texture texture : textureMap.values()) {
            texture.dispose();
        }
    }
}
