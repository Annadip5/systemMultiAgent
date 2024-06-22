package org.multiagent_city.utils;

public class Texture {
    private static final String path = "assets/textures/";
    private static final String nature = path + "nature/";
    private static final String infrastructures = path + "infrastructures/";
    private static final String zonestates = path + "zonestates/";
    private static final String buttons = path + "buttons/";

    // Infrastructures
    // Road
    public static final String road = infrastructures + "road.png";

    // Buildings
    public static final String townHall = infrastructures + "townHall.png";
    public static final String school = infrastructures + "school.png";
    public static final String mall = infrastructures + "mall.png";
    public static final String hospital = infrastructures + "hospital.png";
    public static final String dwelling = infrastructures + "dwelling.png";

    // Nature
    public static final String water = nature + "water.png";
    public static final String rock = nature + "rock.png";
    public static final String grass = nature + "grass.png";
    public static final String bush = nature + "bush.png";
    public static final String tree = nature + "tree.png";

    // ZoneState
    public static final String pruningState = zonestates + "pruningState.png";
    public static final String lockedState = zonestates + "lockedState.png";
    public static final String inConstructionState = zonestates + "inConstructionState.png";

    // Buttons
    public static final String play = buttons + "play.png";
    public static final String pause = buttons + "pause.png";
}
