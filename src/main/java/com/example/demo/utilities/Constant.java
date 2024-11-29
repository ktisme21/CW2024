package com.example.demo.utilities;

import javafx.scene.input.KeyCode;

public class Constant {
    // General constants
    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;
    public static final String TITLE = "Sky Battle";

    public static final int PLAYER_INITIAL_HEALTH = 5;

    // Background images for levels
    public static final String LEVEL_ONE_BACKGROUND = "/com/example/demo/images/background1.jpg";
    public static final String LEVEL_TWO_BACKGROUND = "/com/example/demo/images/background2.jpg";
    public static final String LEVEL_THREE_BACKGROUND = "/com/example/demo/images/background3.jpg";

    // Enemy spawn properties
    public static final int LEVEL_ONE_TOTAL_ENEMIES = 5;
    public static final double LEVEL_ONE_ENEMY_SPAWN_PROBABILITY = 0.20;
    public static final int LEVEL_ONE_KILLS_TO_ADVANCE = 10;

    // Level transitions
    public static final String LEVEL_ONE_NEXT = "com.example.demo.level.LevelTwo";
    public static final String LEVEL_TWO_NEXT = "com.example.demo.level.LevelThree";

    // Shield properties
    public static final double SHIELD_OFFSET_X = -40.0;
    public static final double SHIELD_OFFSET_Y = 0.0;

    // Level Three constants
    public static final int LEVEL_THREE_TOTAL_BOSSES = 3;

    // Red container properties
    public static final String RED_CONTAINER_STROKE_COLOR = "RED";
    public static final String RED_CONTAINER_FILL_COLOR = "TRANSPARENT";
    public static final double RED_CONTAINER_STROKE_WIDTH = 2.0;
    public static final double RED_CONTAINER_SHRINK_FACTOR = 0.5;

    // Game over image constants
    public static final String GAME_OVER_IMAGE = "/com/example/demo/images/gameover.png";
    public static final double GAME_OVER_IMAGE_WIDTH = 750;
    public static final double GAME_OVER_IMAGE_HEIGHT = 600;

    // Health bar constants
    public static final double HEALTH_BAR_WIDTH = 200;
    public static final double HEALTH_BAR_HEIGHT = 20;
    public static final String BACKGROUND_COLOR = "DARKRED"; // Health bar background
    public static final String FOREGROUND_COLOR = "LIMEGREEN"; // Health bar foreground

    // Heart display constants
    public static final String HEART_IMAGE = "/com/example/demo/images/heart.png";
    public static final int HEART_HEIGHT = 50;
    public static final int FIRST_ITEM_INDEX = 0;

    // Shield image constants
    public static final String SHIELD_IMAGE = "/com/example/demo/images/shield.png";
    public static final int SHIELD_SIZE = 200;

    // Win image constants
    public static final String WIN_IMAGE = "/com/example/demo/images/youwin.png";
    public static final int WIN_IMAGE_WIDTH = 600;
    public static final int WIN_IMAGE_HEIGHT = 500;

    // Screen adjustments
    public static final double SCREEN_HEIGHT_ADJUSTMENT = 150;

    // Timing constants
    public static final int MILLISECOND_DELAY = 50;

    // Pause button styling
    public static final String PAUSE_BUTTON_STYLE = "-fx-font-size: 14px; -fx-padding: 5px 10px;";
    public static final double PAUSE_BUTTON_X_POSITION = 120;
    public static final double PAUSE_BUTTON_Y_POSITION = 20;

    // Timer label styling
    public static final String TIMER_LABEL_STYLE = "-fx-font-size: 14px; -fx-text-fill: white;";
    public static final double TIMER_LABEL_X_POSITION = 300;
    public static final double TIMER_LABEL_Y_POSITION = 25;

    // Timer formatting
    public static final String TIMER_FORMAT = "Time: %02d:%02d:%03d";

    // Elapsed time formatting
    public static final String ELAPSED_TIME_FORMAT = "%02d:%02d.%03d";

    // Player name (default for leaderboard)
    public static final String DEFAULT_PLAYER_NAME = "Player";

    // Key mappings
    public static final KeyCode KEY_UP = KeyCode.UP;
    public static final KeyCode KEY_DOWN = KeyCode.DOWN;
    public static final KeyCode KEY_LEFT = KeyCode.LEFT;
    public static final KeyCode KEY_RIGHT = KeyCode.RIGHT;
    public static final KeyCode KEY_FIRE = KeyCode.SPACE;
    public static final KeyCode KEY_QUIT = KeyCode.Q;

    //Active Actor Constants
    public static final String IMAGE_LOCATION = "/com/example/demo/images/";
    public static final double COLLISION_SHRINK_FACTOR = 0.5;

    // Enemy Plane Constant
    public static final double ENEMY_FIRE_RATE = 0.01;
    public static final int ENEMY_PLANE_IMAGE_HEIGHT = 150;
    public static final int ENEMY_INITIAL_HEALTH = 1;
    public static final String ENEMY_PLANE_IMAGE = "enemyplane.png";
    public static final double PROJECTILE_X_POSITION_OFFSET = -100;
    public static final double PROJECTILE_Y_POSITION_OFFSET = 50;
    public static final double ENEMY_COLLISION_SHRINK_FACTOR = .5;
    public static final double ENEMY_HORIZONTAL_VELOCITY = -6;

    // General Boss Constants
    public static final String BOSS_PLANE_IMAGE = "bossplane.png";
    public static final double BOSS_INITIAL_X_POSITION = 1000.0;
    public static final double BOSS_INITIAL_Y_POSITION = 400.0;
    public static final double BOSS_PROJECTILE_Y_OFFSET = 75.0;
    public static final double BOSS_FIRE_RATE = 0.04;
    public static final double BOSS_SHIELD_PROBABILITY = 0.002;
    public static final int BOSS_IMAGE_HEIGHT = 300;
    public static final int BOSS_VERTICAL_VELOCITY = 8;
    public static final int BOSS_HEALTH = 1;
    public static final int BOSS_MOVE_FREQUENCY = 5;
    public static final int BOSS_MAX_FRAMES_WITH_SAME_MOVE = 10;
    public static final int BOSS_Y_POSITION_UPPER_BOUND = -100;
    public static final int BOSS_Y_POSITION_LOWER_BOUND = 475;
    public static final int BOSS_MAX_FRAMES_WITH_SHIELD = 500;
    public static final int ZERO = 0;

    // General UserPlane Constants
    public static final String USER_PLANE_IMAGE_NAME = "userplane.png";
    public static final double USER_PLANE_X_LEFT_BOUND = -30.0;
    public static final double USER_PLANE_X_RIGHT_BOUND = 1110.0;
    public static final double USER_PLANE_Y_UPPER_BOUND = -40.0;
    public static final double USER_PLANE_Y_LOWER_BOUND = 600.0;
    public static final double USER_PLANE_INITIAL_X = 5.0;
    public static final double USER_PLANE_INITIAL_Y = 300.0;
    public static final int USER_PLANE_IMAGE_HEIGHT = 150;
    public static final int USER_PLANE_VERTICAL_VELOCITY = 8;
    public static final int USER_PLANE_HORIZONTAL_VELOCITY = 8;
    public static final int USER_PROJECTILE_X_POSITION = 110;
    public static final int USER_PROJECTILE_Y_POSITION_OFFSET = 20;

    // General Projectile Constants
    public static final String BOSS_PROJECTILE_IMAGE = "fireball.png";
    public static final int BOSS_PROJECTILE_IMAGE_HEIGHT = 75;
    public static final int BOSS_PROJECTILE_HORIZONTAL_VELOCITY = -15;
    public static final int BOSS_PROJECTILE_INITIAL_X = 950;

    public static final String ENEMY_PROJECTILE_IMAGE = "enemyFire.png";
    public static final int ENEMY_PROJECTILE_IMAGE_HEIGHT = 50;
    public static final int ENEMY_PROJECTILE_HORIZONTAL_VELOCITY = -10;

    public static final String USER_PROJECTILE_IMAGE = "userfire.png";
    public static final int USER_PROJECTILE_IMAGE_HEIGHT = 125;
    public static final int USER_PROJECTILE_HORIZONTAL_VELOCITY = 15;

    // Background image paths
    public static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/background4.jpg";
    public static final String LEADERBOARD_STACKPANE_IMAGE_PATH = "/com/example/demo/images/stackpane.png";

    // GameStartScreen Constants
    public static final double GAME_START_BUTTON_SPACING = 20.0;
    public static final String TITLE_STYLE = "-fx-font-size: 24px; -fx-font-weight: bold;";
    public static final String GAME_START_BUTTON_STYLE = "-fx-font-size: 18px;";

    // LeaderBoard Constants
    public static final String LEADERBOARD_SCORE_STYLE = "-fx-font-size: 18px; -fx-text-fill: white;";
    public static final String LEADERBOARD_BUTTON_STYLE = "-fx-font-size: 18px;";
    public static final int LEADERBOARD_SPACING = 20;
    public static final double LEADERBOARD_STACKPANE_WIDTH = 400.0;
    public static final double LEADERBOARD_STACKPANE_HEIGHT = 300.0;

    // LevelView constants
    public static final double HEART_DISPLAY_X_POSITION = 15;
    public static final double HEART_DISPLAY_Y_POSITION = 20;
    public static final int WIN_IMAGE_X_POSITION = 355;
    public static final int WIN_IMAGE_Y_POSITION = 175;
    public static final int LOSS_SCREEN_X_POSITION = 300;
    public static final int LOSS_SCREEN_Y_POSITION = 50;

    // Shield display constants
    public static final int SHIELD_X_POSITION = 1150;
    public static final int SHIELD_Y_POSITION = 500;

    // PauseScreen constants
    public static final double PAUSE_SPACING = 20.0;
    public static final double PAUSE_SCENE_WIDTH = 500.0;
    public static final double PAUSE_SCENE_HEIGHT = 400.0;
    public static final double PAUSE_DEFAULT_VOLUME = 50.0;
    public static final String PAUSE_TITLE_STYLE = null;
    public static final String BUTTON_STYLE = null;

    // Settings Page constants
    public static final String SETTINGS_BUTTON_STYLE = "-fx-font-size: 18px;";
    public static final double SETTINGS_SPACING = 20.0;
    public static final double SETTINGS_DEFAULT_VOLUME = 50.0;
    public static final double SETTINGS_SLIDER_WIDTH = 300.0;
}
