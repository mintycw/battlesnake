package nl.hu.bep.battlesnake.webservices.game.config;

public class GameConfig {
    public static final int MAX_DEPTH_START = 3;
    public static final int MAX_DEPTH_EARLY = 4;
    public static final int MAX_DEPTH_MID = 5;
    public static final int MAX_DEPTH_LATE = 7;

    public static final double FOOD_PRIORITY_LOW_HP = 200.0;
    public static final double FOOD_PRIORITY_HIGH_HP = 50.0;
    public static final double FOOD_REWARD_BASE_VALUE = 2.0;
}