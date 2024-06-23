package org.multiagent_city.utils;

public class Texture {
    private static final String PATH = "assets/textures/";
    private static final String NATURE = PATH + "nature/";
    private static final String INFRASTRUCTURES = PATH + "infrastructures/";
    private static final String ZONE_STATES = PATH + "zonestates/";
    private static final String BUTTONS = PATH + "buttons/";

    // Infrastructures
    // Road
    public static final String ROAD = INFRASTRUCTURES + "road.png";

    // Buildings
    public static final String TOWN_HALL = INFRASTRUCTURES + "townHall.png";
    public static final String SCHOOL = INFRASTRUCTURES + "school.png";
    public static final String MALL = INFRASTRUCTURES + "mall.png";
    public static final String HOSPITAL = INFRASTRUCTURES + "hospital.png";
    public static final String DWELLING = INFRASTRUCTURES + "dwelling.png";

    // Nature
    public static final String WATER = NATURE + "water.png";
    public static final String ROCK = NATURE + "rock.png";
    public static final String GRASS = NATURE + "grass.png";
    public static final String BUSH = NATURE + "bush.png";
    public static final String TREE = NATURE + "tree.png";

    // ZoneState
    public static final String PRUNING_STATE = ZONE_STATES + "pruningState.png";
    public static final String LOCKED_STATE = ZONE_STATES + "lockedState.png";
    public static final String IN_CONSTRUCTION_STATE = ZONE_STATES + "inConstructionState.png";

    // Buttons
    public static final String PLAY = BUTTONS + "play.png";
    public static final String PAUSE = BUTTONS + "pause.png";
}
