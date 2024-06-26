package org.multiagent_city;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import org.multiagent_city.agents.Building;
import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.agents.Road;
import org.multiagent_city.agents.buildings.Dwelling;
import org.multiagent_city.agents.buildings.Hospital;
import org.multiagent_city.agents.buildings.Mall;
import org.multiagent_city.agents.buildings.School;
import org.multiagent_city.controller.SimulatorController;
import org.multiagent_city.model.Simulator;
import org.multiagent_city.utils.FastNoiseLite;
import org.multiagent_city.utils.strategy.IStrategy;
import org.multiagent_city.utils.strategy.StrategyAStar;
import org.multiagent_city.utils.strategy.StrategyRandom;
import org.multiagent_city.view.SimulatorView;
import org.multiagent_city.view.MapView;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Boot extends Game {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private SpriteBatch spriteBatchConfig;
    private MapView mapView;
    private int mapWidth = 20;//60
    private int mapHeight = 20;//60
    private int cellSize = 20;
    private SimulatorController simulatorController;
    private int counter = 0;
    private float elapsedTime = 0;
    private float updateInterval = 0.100f; // time in seconds


    private static final int ROAD_WEIGHT = 100;
    private static final int DWELLING_WEIGHT = 50;
    private static final int SCHOOL_WEIGHT = 1;
    private static final int MALL_WEIGHT = 10;
    private static final int HOSPITAL_WEIGHT = 1;

    private IStrategy roadStrategy;
    private IStrategy buildingStrategy;
    private Boolean aStarSelected=false;

    private final Map<Class<? extends Infrastructure>, Integer> infrastructureWeights = new HashMap<>();
    private final Random random = new Random();

    private Map<String, Texture> textureMap;

    // UI components
    private Stage stage;
    private TextField textField;
    private BitmapFont font;
    private String inputText = "";
    private int step = 0;
    private boolean changeWeights = false;
    private boolean configDone = false;
    private ButtonGroup<CheckBox> strategyButtonGroup;
    private Table table;
    private Texture backgroundTexture;
    private ShapeRenderer shapeRenderer1;
    private Label strategyLabel ;
    private CheckBox aStarCheckBox ;
    private CheckBox randomCheckBox;

    private boolean isPaused = false;
    private Rectangle playPauseButtonBounds;

    @Override
    public void create() {
        this.loadTextures();
        this.setInfrastructureWeights();
        this.createButtons();
        spriteBatch = new SpriteBatch();
        spriteBatchConfig = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, mapWidth * cellSize, mapHeight * cellSize);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer1 = new ShapeRenderer();

        backgroundTexture = new Texture(Gdx.files.internal("ville2.jpg"));
        // Initialize UI components
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;

        font.setColor(Color.WHITE);

        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.background=createDrawable(Color.WHITE);
        textField = new TextField("", textFieldStyle);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Création d'une police de base
        font = new BitmapFont();
        font.getData().setScale(2);

        // Création du style pour le Label
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        labelStyle.background = createDrawable(Color.BLACK);

        // Création du style pour le CheckBox
        CheckBox.CheckBoxStyle aStarCheckBoxStyle = createCheckBoxStyle(Color.DARK_GRAY, Color.GREEN, font, Color.WHITE);
        CheckBox.CheckBoxStyle randomCheckBoxStyle = createCheckBoxStyle(Color.DARK_GRAY, Color.GREEN, font, Color.WHITE);

        /*Drawable hoverDrawable = createDrawable(Color.LIGHT_GRAY);
        Drawable checkboxOnHover = createDrawable(Color.SKY);*/

        table = new Table();
        table.setFillParent(true);
        //table.add(textField).width(100).height(40).pad(10);

        stage.addActor(table);

        strategyLabel = new Label("Sélectionnez une stratégie puis appuyer sur Entrer:", labelStyle);
        aStarCheckBox = new CheckBox("A star", aStarCheckBoxStyle);
        randomCheckBox = new CheckBox("Random", randomCheckBoxStyle);

        // Ajout de listeners aux cases à cocher
        aStarCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (aStarCheckBox.isChecked()) {
                    aStarSelected = true;

                    System.out.println("A star selected");
                }
            }
        });

        randomCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (randomCheckBox.isChecked()) {
                    aStarSelected = false;

                    System.out.println("Random selected");
                }
            }
        });

        addHoverEffect(aStarCheckBox);
        addHoverEffect(randomCheckBox);

        // Adding checkboxes to the stage
        stage.addActor(aStarCheckBox);
        stage.addActor(randomCheckBox);
        strategyButtonGroup = new ButtonGroup<>(aStarCheckBox, randomCheckBox);
        strategyButtonGroup.setMaxCheckCount(1);
        strategyButtonGroup.setMinCheckCount(1);
        strategyButtonGroup.setChecked("Astar");

        table.row();
        table.add(strategyLabel).left().pad(10);
        table.row();
        table.add(aStarCheckBox).left().pad(10);
        table.row();
        table.add(randomCheckBox).left().pad(10);
    }

    private void clearGL() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render() {
        if (!configDone) {
            this.clearGL();
            // Dessiner l'image de fond
            spriteBatchConfig.begin();
            spriteBatchConfig.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            spriteBatchConfig.end();

            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();

            spriteBatchConfig.begin();
            font.draw(spriteBatchConfig, "Bienvenue dans la simulation de ville !", 230, 550);
            switch (step) {
                case 0:

                    font.draw(spriteBatchConfig, "Pour la création des routes:", 270, 470);
                    stage.draw();
                    break;
                case 1:
                    font.draw(spriteBatchConfig, "Pour la création des constructions:", 270, 470);
                    stage.draw();
                    break;
                case 2:
                    table.removeActor(strategyLabel);
                    aStarCheckBox.clear();
                    randomCheckBox.clear();
                    textField.setPosition(380,320);
                    stage.addActor(textField);
                    font.setColor(Color.WHITE);
                    font.draw(spriteBatchConfig, "Les valeurs par défaut des poids de chaque construction sont :", 100, 470);
                    font.draw(spriteBatchConfig, "Routes : "+ROAD_WEIGHT+", Habitation : "+DWELLING_WEIGHT+", Hôpital : "+HOSPITAL_WEIGHT+", École : "+SCHOOL_WEIGHT+", Commerce : "+MALL_WEIGHT, 90, 440);
                    font.draw(spriteBatchConfig, "Voulez-vous les changer (O/N) ?", 250, 410);

                    break;
                case 3:
                    if (changeWeights) {
                        font.draw(spriteBatchConfig, "Entrez les valeurs séparées par des virgules comme suit", 190, 450);
                        font.draw(spriteBatchConfig, "Route,Habitation,Hopital,Ecole,Commerce", 220, 400);
                    } else {
                        configDone = true;
                        initializeSimulation();
                    }
                    break;
                case 4:
                    font.draw(spriteBatchConfig, "La simulation va commencer", 100, 350);
                    break;
            }
            spriteBatchConfig.end();

            handleInput();
        } else {
            float deltaTime = Gdx.graphics.getDeltaTime();
            elapsedTime += deltaTime;

            if(!isPaused) {
                this.clearGL();
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
                            float originX = cellSize / 2;
                            float originY = cellSize / 2;
                            float rotation = 180.0f;
                            spriteBatch.draw(texture, y * cellSize, x * cellSize, originX, originY, cellSize, cellSize, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), true, false);
                        }
                    }
                }
                spriteBatch.end();

                // Update infrastructures at set intervals
                if (elapsedTime >= updateInterval) {
                    this.buildInfrastructures(roadStrategy, buildingStrategy);
                    elapsedTime = 0;
                }
                spriteBatchConfig.begin();
                font.draw(spriteBatchConfig, String.format("%d", this.counter), 20, 50);
                spriteBatchConfig.end();

                // Update the map
                this.simulatorController.updateView(deltaTime);
            }
            this.updateButtons(deltaTime);
        }
    }

    private void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(step==0){

                aStarCheckBox.getLabel().setColor(Color.WHITE);
                randomCheckBox.getLabel().setColor(Color.WHITE);
            }

            if (step == 2 || step == 3) {
                inputText = textField.getText();
                textField.setText("Entrez ici");

            }
            processInput();
        }
    }
    private IStrategy selectedStrategy(boolean aStarSelected) {
        if(aStarSelected) {
            return new StrategyAStar();
        }
        return new StrategyRandom();
    }

    private void processInput() {
        switch (step) {
            case 0:
                roadStrategy = this.selectedStrategy(aStarSelected);
                step++;
                break;

            case 1:
                buildingStrategy = this.selectedStrategy(aStarSelected);
                step++;
                break;
            case 2:
                if (inputText.equalsIgnoreCase("O")) {
                    changeWeights = true;
                    step++;
                    System.out.println("User opted to change weights, moving to step 4");
                } else if (inputText.equalsIgnoreCase("N")) {
                    changeWeights = false;
                    configDone = true;
                    initializeSimulation();
                    System.out.println("User opted not to change weights, initializing simulation");
                } else {
                    System.out.println("Invalid input for step 3");
                }
                break;
            case 3:
                if (changeWeights) {
                    try {
                        String[] weights = inputText.split(",");
                        setInfrastructureWeights(Integer.parseInt(weights[0].trim()),Integer.parseInt(weights[1].trim()),Integer.parseInt(weights[2].trim()),Integer.parseInt(weights[3].trim()),Integer.parseInt(weights[4].trim()));
                        configDone = true;
                        initializeSimulation();
                        System.out.println("Weights updated, initializing simulation");
                    } catch (Exception e) {
                        System.out.println("Invalid input for weights: " + e.getMessage());
                    }
                }
                break;
        }
    }
    private void loadTextures() {
        textureMap = new HashMap<>();
        // Infrastructures
        textureMap.put(org.multiagent_city.utils.Texture.ROAD, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.ROAD)));
        textureMap.put(org.multiagent_city.utils.Texture.DWELLING, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.DWELLING)));
        textureMap.put(org.multiagent_city.utils.Texture.SCHOOL, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.SCHOOL)));
        textureMap.put(org.multiagent_city.utils.Texture.HOSPITAL, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.HOSPITAL)));
        textureMap.put(org.multiagent_city.utils.Texture.MALL, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.MALL)));
        textureMap.put(org.multiagent_city.utils.Texture.TOWN_HALL, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.TOWN_HALL)));

        // Nature
        textureMap.put(org.multiagent_city.utils.Texture.WATER, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.WATER)));
        textureMap.put(org.multiagent_city.utils.Texture.ROCK, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.ROCK)));
        textureMap.put(org.multiagent_city.utils.Texture.TREE, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.TREE)));
        textureMap.put(org.multiagent_city.utils.Texture.BUSH, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.BUSH)));
        textureMap.put(org.multiagent_city.utils.Texture.GRASS, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.GRASS)));

        // ZoneState
        textureMap.put(org.multiagent_city.utils.Texture.PRUNING_STATE, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.PRUNING_STATE)));
        textureMap.put(org.multiagent_city.utils.Texture.LOCKED_STATE, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.LOCKED_STATE)));
        textureMap.put(org.multiagent_city.utils.Texture.IN_CONSTRUCTION_STATE, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.IN_CONSTRUCTION_STATE)));

        // Buttons
        textureMap.put(org.multiagent_city.utils.Texture.PLAY, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.PLAY)));
        textureMap.put(org.multiagent_city.utils.Texture.PAUSE, new Texture(Gdx.files.internal(org.multiagent_city.utils.Texture.PAUSE)));
    }

    private void setInfrastructureWeights() {
        // Define weights for each infrastructure type
        infrastructureWeights.put(Road.class, ROAD_WEIGHT);
        infrastructureWeights.put(Dwelling.class, DWELLING_WEIGHT);
        infrastructureWeights.put(Hospital.class, HOSPITAL_WEIGHT);
        infrastructureWeights.put(School.class, SCHOOL_WEIGHT);
        infrastructureWeights.put(Mall.class, MALL_WEIGHT);
    }
    private void setInfrastructureWeights(int roadWeight, int dwellingWeight, int hospitalWeight , int schoolWeight, int mallWeight) {
        // Define weights for each infrastructure type
        infrastructureWeights.put(Road.class, roadWeight);
        infrastructureWeights.put(Dwelling.class, dwellingWeight);
        infrastructureWeights.put(Hospital.class, hospitalWeight);
        infrastructureWeights.put(School.class, schoolWeight);
        infrastructureWeights.put(Mall.class, mallWeight);
        System.out.println("road :" + roadWeight + "/ndwelling : " + dwellingWeight + "/nhospital : " + hospitalWeight + "/nschool : " + schoolWeight + "/nmall : " + mallWeight  ) ;
    }

    public void buildInfrastructures(IStrategy roadStrategy, IStrategy buildingStrategy) {
        // Create agents
        // Add road with weighted randomness
        int totalWeight = infrastructureWeights.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = random.nextInt(totalWeight);
        int currentWeight = 0;

        for (Map.Entry<Class<? extends Infrastructure>, Integer> entry : infrastructureWeights.entrySet()) {
            currentWeight += entry.getValue();
            if (randomValue < currentWeight) {
                if (Road.class.isAssignableFrom(entry.getKey())) {
                    this.simulatorController.addRoad(roadStrategy, 50, 100, 4);

                } else if (Building.class.isAssignableFrom(entry.getKey())) {
                    // You need to cast entry.getKey() to the specific type you want
                    Class<? extends Building> buildingClass = (Class<? extends Building>) entry.getKey();
                    this.addBuildingIfMatch(buildingStrategy, buildingClass, Dwelling.class, 100, 150, 2);
                    this.addBuildingIfMatch(buildingStrategy, buildingClass, School.class, 100, 150, 3);
                    this.addBuildingIfMatch(buildingStrategy, buildingClass, Hospital.class, 100, 150, 2);
                    this.addBuildingIfMatch(buildingStrategy, buildingClass, Mall.class, 100, 150, 4);
                }
                break;
            }
        }
        this.counter++;
    }

    private void initializeSimulation() {
        this.loadTextures();
        this.setInfrastructureWeights();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, mapWidth * cellSize, mapHeight * cellSize);
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        Simulator simulator = new Simulator();
        SimulatorView simulatorView = new SimulatorView();
        this.simulatorController = new SimulatorController(simulatorView, simulator);
        this.mapView = new MapView(simulator.getMap());
        this.simulatorController.addObserver(this.mapView);
        this.simulatorController.setMapSize(mapWidth, mapHeight);

        // Create noise map for nature
        FastNoiseLite noise = new FastNoiseLite(1);
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        noise.SetFrequency(0.5f);

        int blurRadius = 1;
        this.simulatorController.createSimulation(noise, blurRadius);
        this.simulatorController.setTownHallPosition(10, 10);
    }
    private BaseDrawable createDrawable(final Color color) {
        return new BaseDrawable() {
            @Override
            public void draw(Batch batch, float x, float y, float width, float height) {
                batch.end(); // End the batch to switch to ShapeRenderer
                ShapeRenderer shapeRenderer = new ShapeRenderer();
                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
                shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(color);
                shapeRenderer.rect(x, y, width, height);
                shapeRenderer.end();
                shapeRenderer.dispose();
                batch.begin(); // Restart the batch after drawing with ShapeRenderer
            }
        };
    }

    private void createButtons() {
        playPauseButtonBounds = new Rectangle(0, 0, 50, 50);
    }

    private void updateButtons(double deltaTime) {
        // Gestion des événements de clic pour le bouton play/pause
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if (playPauseButtonBounds.contains(touch.x, touch.y)) {
                isPaused = !isPaused; // Inverser l'état play/pause
            }
        }

        // Rendu du bouton play/pause
        spriteBatch.begin();
        if (isPaused) {
            spriteBatch.draw(textureMap.get(org.multiagent_city.utils.Texture.PLAY), playPauseButtonBounds.x, playPauseButtonBounds.y, playPauseButtonBounds.width, playPauseButtonBounds.height);
        } else {
            spriteBatch.draw(textureMap.get(org.multiagent_city.utils.Texture.PAUSE), playPauseButtonBounds.x, playPauseButtonBounds.y, playPauseButtonBounds.width, playPauseButtonBounds.height);
        }
        spriteBatch.end();
    }
    private void addBuildingIfMatch(IStrategy strategy, Class<? extends Building> buildingClass, Class<? extends Building> classToCheck, int minHealth, int maxHealth, float usuryCoefficient){
        if (buildingClass == classToCheck) {
            this.simulatorController.addBuilding(strategy, buildingClass, minHealth, maxHealth, usuryCoefficient);
        }
    }

    @Override
    public void dispose() {
        // Dispose of resources to prevent memory leaks

        // Dispose of the textures
        if (textureMap != null) {
            for (Texture texture : textureMap.values()) {
                if (texture != null) {
                    texture.dispose();
                }
            }
        }

        // Dispose of sprite batches
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
        if (spriteBatchConfig != null) {
            spriteBatchConfig.dispose();
        }

        // Dispose of shape renderers
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (shapeRenderer1 != null) {
            shapeRenderer1.dispose();
        }

        // Dispose of the stage
        if (stage != null) {
            stage.dispose();
        }

        // Dispose of the font
        if (font != null) {
            font.dispose();
        }

        // Dispose of the background texture
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }

        // Additional disposables, if any
        if (camera != null) {
            camera = null;
        }
    }


    private void addHoverEffect(final CheckBox checkBox) {
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                aStarCheckBox.getLabel().setColor(Color.WHITE);
                randomCheckBox.getLabel().setColor(Color.WHITE);

                if (checkBox.isChecked()) {
                    checkBox.getLabel().setColor(Color.GREEN);
                }
            }
        });
    }

    private CheckBox.CheckBoxStyle createCheckBoxStyle(Color checkboxOffColor, Color checkboxOnColor, BitmapFont font, Color fontColor) {
        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.checkboxOff = createDrawable(checkboxOffColor);
        checkBoxStyle.checkboxOn = createDrawable(checkboxOnColor);
        checkBoxStyle.font = font;
        checkBoxStyle.fontColor = fontColor;
        return checkBoxStyle;
    }



}