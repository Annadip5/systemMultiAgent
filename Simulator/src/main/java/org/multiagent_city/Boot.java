package org.multiagent_city;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
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
import org.multiagent_city.utils.strategy.StrategyAStar;
import org.multiagent_city.utils.strategy.StrategyRandom;
import org.multiagent_city.view.SimulatorView;
import org.multiagent_city.view.MapView;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Boot extends Game {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private MapView mapView;
    private int mapWidth = 50;
    private int mapHeight = 50;
    private int cellSize = 20;
    private SimulatorController simulatorController;
    private int counter = 0;
    private float elapsedTime = 100;
    private float updateInterval = 0.010f;
    private Boolean road=false;
    private Boolean aStarSelected=false;
    private boolean building=true;

    private final Map<Class<? extends Infrastructure>, Integer> infrastructureWeights = new HashMap<>();
    private final Random random = new Random();

    private Map<String, Texture> textureMap;

    // UI components
    private Stage stage;
    private TextField textField;
    private BitmapFont font;
    private String inputText = "";
    private int step = 0;
    private boolean waitForInput = true;
    private boolean changeWeights = false;
    private boolean configDone = false;
    private ButtonGroup<CheckBox> strategyButtonGroup;
    private Skin skin;
    private Table table;
    private Texture backgroundTexture;
    private ShapeRenderer shapeRenderer1;
    private Label strategyLabel ;
    private CheckBox aStarCheckBox ;
    private CheckBox randomCheckBox;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        shapeRenderer1 = new ShapeRenderer();
        //backgroundTexture = new Texture(Gdx.files.internal("black-rectangle-png.png"));

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
        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.checkboxOff = createDrawable(Color.DARK_GRAY);
        checkBoxStyle.checkboxOn = createDrawable(Color.GREEN);
        checkBoxStyle.font = font;
        checkBoxStyle.fontColor = Color.WHITE;

        table = new Table();
        table.setFillParent(true);
        //table.add(textField).width(100).height(40).pad(10);

        stage.addActor(table);

        strategyLabel = new Label("Sélectionnez une stratégie :", labelStyle);
        aStarCheckBox = new CheckBox("A star", checkBoxStyle);
        randomCheckBox = new CheckBox("Random", checkBoxStyle);

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




    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dessiner l'image de fond
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
        if (!configDone) {
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();

            spriteBatch.begin();
            font.draw(spriteBatch, "Bienvenue dans la simulation de ville !", 230, 550);
            switch (step) {
                case 0:

                    font.draw(spriteBatch, "Pour la création des routes:", 270, 470);
                    stage.draw();

                  //  font.draw(spriteBatch, "Tapez 1 pour une stratégie random ou 2 pour une stratégie A*.", 100, 300);
                    break;
                case 1:
                    font.draw(spriteBatch, "Pour la création des constructions:", 270, 470);
                  //  font.draw(spriteBatch, "Tapez 1 pour une stratégie random ou 2 pour une stratégie A*.", 100, 300);
                    stage.draw();
                    break;
                case 2:

                    table.removeActor(strategyLabel);
                    aStarCheckBox.clear();
                    randomCheckBox.clear();
                    textField.setPosition(380,320);
                    stage.addActor(textField);
                    font.setColor(Color.WHITE);
                    font.draw(spriteBatch, "Les valeurs par défaut des poids de chaque construction sont :", 100, 470);
                    font.draw(spriteBatch, "Routes : 20, Habitation : 10, ,Hôpital : 1, Ecole : 1, Commerce : 3", 90, 440);
                    font.draw(spriteBatch, "Voulez-vous les changer (O/N) ?", 250, 410);

                    break;
                case 3:
                    if (changeWeights) {
                        font.draw(spriteBatch, "Entrez les valeurs séparées par des virgules comme suit", 190, 450);
                        font.draw(spriteBatch, "Route,Habitation,Hopital,Ecole,Commerce", 220, 400);
                    } else {
                        configDone = true;
                        initializeSimulation();
                    }
                    break;
                case 4:
                    font.draw(spriteBatch, "La simulation va commencer", 100, 350);
                    break;
            }
            spriteBatch.end();

            handleInput();
        } else {
            float deltaTime = Gdx.graphics.getDeltaTime();
            elapsedTime += deltaTime;

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
            // Update infrastructures at set intervals
            if (elapsedTime >= updateInterval * deltaTime && this.counter <= 30000) {
                this.buildInfrastructures( road, building);
                this.counter++;
                elapsedTime = 0;
            }
            // Update the map
            this.simulatorController.updateView(deltaTime);
        }
    }

    private void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (step == 2 || step == 3) {

                inputText = textField.getText();
                textField.setText("Entrez ici");


            }
            processInput();
        }



    }

    private void processInput() {
        switch (step) {
            case 0:
                road=aStarSelected;
                if (road){

                    System.out.println("Step 1 completed with A* strategy, moving to step 2");
                }
                else {
                    System.out.println("Step 1 completed with Random strategy , moving to step 2");
                }
                step++;
                break;

            case 1:
                building=aStarSelected;
                if (building){

                    System.out.println("Step 2 completed with A* strategy, moving to step 3");
                }
                else {
                    System.out.println("Step 2 completed with Random strategy , moving to step 3");
                }
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

    private void setInfrastructureWeights() {
        // Define weights for each infrastructure type
        infrastructureWeights.put(Road.class, 20);
        infrastructureWeights.put(Dwelling.class, 10);
        infrastructureWeights.put(Hospital.class, 1);
        infrastructureWeights.put(School.class, 1);
        infrastructureWeights.put(Mall.class, 3);
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

    public void buildInfrastructures(boolean road ,boolean building) {
        // Create agents
        // Add road with weighted randomness
        int totalWeight = infrastructureWeights.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = random.nextInt(totalWeight);
        int currentWeight = 0;

        for (Map.Entry<Class<? extends Infrastructure>, Integer> entry : infrastructureWeights.entrySet()) {
            currentWeight += entry.getValue();
            if (randomValue < currentWeight) {
                if (Road.class.isAssignableFrom(entry.getKey())) {
                    if (road) {
                        this.simulatorController.addRoad(new StrategyAStar(), 50, 100, 4);
                        System.out.println("the road strategy is A*");
                    }else{
                        this.simulatorController.addRoad(new StrategyRandom(), 50, 100, 4);
                        System.out.println("the road strategy is Rand");
                    }

                } else if (Building.class.isAssignableFrom(entry.getKey())) {
                    // You need to cast entry.getKey() to the specific type you want
                    Class<? extends Building> buildingClass = (Class<? extends Building>) entry.getKey();
                    if(building){
                        this.simulatorController.addBuilding(new StrategyAStar(), buildingClass, 100, 150, 2);
                        System.out.println("the buildings strategy is A*");
                    }else{
                        this.simulatorController.addBuilding(new StrategyRandom(), buildingClass, 100, 150, 2);
                        System.out.println("the buildings strategy is rand");
                    }

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
        this.simulatorController.setTownHallPosition(15, 35);
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
}