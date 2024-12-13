package com.example.demo.utilities;

import javafx.scene.input.KeyCode;

/**
 * Provides a centralized location for storing constants used throughout the application.
 * This class includes constants for screen dimensions, game settings, UI styling,
 * images, enemy and player properties, and level configurations.
 *
 * <p>Note: All constants are declared as static and final.</p>
 */
public class Constant {
    // General constants
    /** The width of the game screen. */
    public static final int SCREEN_WIDTH = 1300;
    /** The height of the game screen. */
    public static final int SCREEN_HEIGHT = 750;
    /** The title of the game. */
    public static final String TITLE = "Sky Battle";
    /** The initial health of the player. */
    public static final int PLAYER_INITIAL_HEALTH = 5;

    // Button image and styling
    /** Path to the default text box image for buttons. */
    public static final String TEXTBOX_IMAGE_PATH = "/com/example/demo/icon/textbar.png";
    /** Path to an alternate text box image for buttons. */
    public static final String TEXTBAR2_IMAGE_PATH = "/com/example/demo/icon/textbar2.png";
    /** Width of the button image. */
    public static final double BUTTON_IMAGE_WIDTH = 300.0;
    /** Height of the button image. */
    public static final double BUTTON_IMAGE_HEIGHT = 60.0;

    public static final double SMALL_BUTTON_WIDTH = 100.0; // Width of button image
    public static final double SMALL_BUTTON_HEIGHT = 50.0; // Height of button image
    public static final double RETURN_BUTTON_WIDTH = 150.0; // Width of button image
    public static final double RETURN_BUTTON_HEIGHT = 60.0; // Height of button image
    public static final String BUTTON_TEXT_STYLE = "-fx-font-size: 18px; -fx-fill: white;"; // Text style
    public static final double BUTTON_TEXT_TRANSLATE_Y = -5.0; // Offset for text position in the text box

    public static final String PAUSE_BUTTON_STYLE = "-fx-font-size: 16px;";

    // Background Images for Levels
    /** Path to the background image for Level 1. */
    public static final String LEVEL_ONE_BACKGROUND = "/com/example/demo/images/background1.jpg";
    /** Path to the background image for Level 2. */
    public static final String LEVEL_TWO_BACKGROUND = "/com/example/demo/images/background2.jpg";
    /** Path to the background image for Level 3. */
    public static final String LEVEL_THREE_BACKGROUND = "/com/example/demo/images/background3.jpg";
    public static final String BACKGROUND_IMAGE_6_PATH = "/com/example/demo/images/background6.jpg";

    // Enemy spawn properties
    /** Number of enemies in Level 1. */
    public static final int LEVEL_ONE_TOTAL_ENEMIES = 10;
    public static final double LEVEL_ONE_ENEMY_SPAWN_PROBABILITY = 0.2;
    /** Kill count required to advance from Level 1. */
    public static final int LEVEL_ONE_KILLS_TO_ADVANCE = 10;

    // Level transitions
    /** Class name of the next level after Level 1. */
    public static final String LEVEL_ONE_NEXT = "com.example.demo.level.LevelTwo";
    public static final String LEVEL_TWO_NEXT = "com.example.demo.level.LevelThree";

    // Shield Properties
    /** Stroke color for the red container surrounding the user plane. */
    public static final String RED_CONTAINER_STROKE_COLOR = "RED";
    /** Fill color for the red container surrounding the user plane. */
    public static final String RED_CONTAINER_FILL_COLOR = "TRANSPARENT";
    public static final double RED_CONTAINER_STROKE_WIDTH = 2.0;
    public static final double RED_CONTAINER_SHRINK_FACTOR = 0.5;

    // Game Over Image Constants
    /** Path to the game over image. */
    public static final String GAME_OVER_IMAGE = "/com/example/demo/images/gameover.png";
    /** Width of the game over image. */
    public static final double GAME_OVER_IMAGE_WIDTH = 750;
    /** Height of the game over image. */
    public static final double GAME_OVER_IMAGE_HEIGHT = 600;

    // Heart Display Constants
    /** Path to the heart image for health display. */
    public static final String HEART_IMAGE = "/com/example/demo/images/heart.png";
    /** Height of the heart image. */
    public static final int HEART_HEIGHT = 50;
    /** Index of the first heart in the display. */
    public static final int FIRST_ITEM_INDEX = 0;

    // Shield Image Constants
    /** Path to the shield image. */
    public static final String SHIELD_IMAGE = "/com/example/demo/images/shield.png";
    /** Size of the shield image. */
    public static final int SHIELD_SIZE = 200;

    // Win image constants
    public static final String WIN_IMAGE = "/com/example/demo/images/youwin.png";
    public static final int WIN_IMAGE_WIDTH = 600;
    public static final int WIN_IMAGE_HEIGHT = 500;
    public static final String WIN_IMAGE_LABEL_STYLE = "-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 6px;";
    public static final double WIN_IMAGE_LABEL_PADDING = 10.0;

    // Screen adjustments
    public static final double SCREEN_HEIGHT_ADJUSTMENT = 150;

    // Timing constants
    public static final int MILLISECOND_DELAY = 50;

    // Button Configuration
    public static final double PAUSE_BUTTON_WIDTH = 40; // Set width of the button
    public static final double PAUSE_BUTTON_HEIGHT = 40; // Set height of the button
    public static final double PAUSE_BUTTON_X_POSITION = 100; // Distance from the right edge
    public static final double PAUSE_BUTTON_Y_POSITION = 20;  // Distance from the top

    // Timer Label Styling and Position
    /** X position of the timer label. */
    public static final double TIMER_LABEL_X_POSITION = 225; // Distance from the right edge
    /** Y position of the timer label. */
    public static final double TIMER_LABEL_Y_POSITION = 35;  // Distance from the top
    /** Styling for the timer label. */
    public static final String TIMER_LABEL_STYLE = "-fx-font-size: 14px; -fx-text-fill: white;";

    // Timer formatting
    public static final String TIMER_FORMAT = "Timer: %02d:%02d:%03d";

    // Elapsed time formatting
    public static final String ELAPSED_TIME_FORMAT = "%02d:%02d.%03d";

    // Player name (default for leaderboard)
    public static final String DEFAULT_PLAYER_NAME = "Player";

    // Key Mappings
    /** Key for moving up. */
    public static final KeyCode KEY_UP = KeyCode.UP;

    /** Key for moving down. */
    public static final KeyCode KEY_DOWN = KeyCode.DOWN;

    /** Key for moving left. */
    public static final KeyCode KEY_LEFT = KeyCode.LEFT;

    /** Key for moving right. */
    public static final KeyCode KEY_RIGHT = KeyCode.RIGHT;

    /** Key for firing projectiles. */
    public static final KeyCode KEY_FIRE = KeyCode.SPACE;

    /** Key for quitting the game. */
    public static final KeyCode KEY_QUIT = KeyCode.Q;

    //Active Actor Constants
    public static final String IMAGE_LOCATION = "/com/example/demo/images/";
    public static final double COLLISION_SHRINK_FACTOR = 0.5;

    // Enemy Plane Constant
    public static double ENEMY_FIRE_RATE = 0.01;
    public static final int ENEMY_PLANE_IMAGE_HEIGHT = 150;
    public static final int ENEMY_INITIAL_HEALTH = 1;
    public static final String ENEMY_PLANE_IMAGE = "enemyplane.png";
    public static final double PROJECTILE_X_POSITION_OFFSET = -100;
    public static final double PROJECTILE_Y_POSITION_OFFSET = 50;
    public static final double ENEMY_COLLISION_SHRINK_FACTOR = .5;
    public static final double ENEMY_HORIZONTAL_VELOCITY = -6;

    // General Boss Constants
    /** Image name for the boss plane. */
    public static final String BOSS_PLANE_IMAGE = "bossplane.png";
    public static final double BOSS_INITIAL_X_POSITION = 1000.0;
    public static final double BOSS_INITIAL_Y_POSITION = 400.0;
    public static final double BOSS_PROJECTILE_Y_OFFSET = 75.0;
    public static final double BOSS_FIRE_RATE = 0.04;
    public static final double BOSS_SHIELD_PROBABILITY = 0.002;
    public static final int BOSS_IMAGE_HEIGHT = 300;
    public static final int BOSS_VERTICAL_VELOCITY = 8;
    /** Initial health of the boss. */
    public static final int BOSS_HEALTH = 50;
    public static final int BOSS_COUNT = 1;
    public static final int BOSS_MOVE_FREQUENCY = 5;
    public static final int BOSS_MAX_FRAMES_WITH_SAME_MOVE = 10;
    public static final int BOSS_Y_POSITION_UPPER_BOUND = -100;
    public static final int BOSS_Y_POSITION_LOWER_BOUND = 550;
    public static final int BOSS_MAX_FRAMES_WITH_SHIELD = 500;
    public static final int ZERO = 0;

    // Boss constants for LevelThree
    public static final int LEVEL_THREE_BOSS_HEALTH = 60;
    public static final int LEVEL_THREE_TOTAL_BOSSES = 3;
    public static final int LEVEL_THREE_BOSS_SHIELD_PROBABILITY = 0;

    // General UserPlane Constants
    public static final int USER_PLANE_INITIAL_HEALTH = 5;
    public static final String USER_PLANE_IMAGE_NAME = "userplane.png";
    public static final double USER_PLANE_X_LEFT_BOUND = -30.0;
    public static final double USER_PLANE_X_RIGHT_BOUND = 1110.0;
    public static final double USER_PLANE_Y_UPPER_BOUND = -40.0;
    public static final double USER_PLANE_Y_LOWER_BOUND = 640.0;
    /** Initial X position of the user plane. */
    public static final double USER_PLANE_INITIAL_X = 5.0;
    /** Initial Y position of the user plane. */
    public static final double USER_PLANE_INITIAL_Y = 300.0;
    public static final int USER_PLANE_IMAGE_HEIGHT = 150;
    public static final int USER_PLANE_VERTICAL_VELOCITY = 10;
    public static final int USER_PLANE_HORIZONTAL_VELOCITY = 10;
    public static final int USER_PROJECTILE_X_POSITION = 110;
    public static final int USER_PROJECTILE_Y_POSITION_OFFSET = 20;

    // General Projectile Constants
    /** Image name for the boss plane. */
    public static final String BOSS_PROJECTILE_IMAGE = "fireball.png";
    /** Height of the boss projectile image. */
    public static final int BOSS_PROJECTILE_IMAGE_HEIGHT = 75;
    public static final int BOSS_PROJECTILE_HORIZONTAL_VELOCITY = -15;
    public static final int BOSS_PROJECTILE_INITIAL_X = 950;

    public static final String ENEMY_PROJECTILE_IMAGE = "enemyFire.png";
    public static final int ENEMY_PROJECTILE_IMAGE_HEIGHT = 50;
    public static final int ENEMY_PROJECTILE_HORIZONTAL_VELOCITY = -10;

    public static final String USER_PROJECTILE_IMAGE = "userfire.png";
    public static final int USER_PROJECTILE_IMAGE_HEIGHT = 125;
    public static final int USER_PROJECTILE_HORIZONTAL_VELOCITY = 16;

    // Background image paths
    public static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/background4.jpg";
    public static final String LEADERBOARD_STACKPANE_IMAGE_PATH = "/com/example/demo/images/stackpane.png";

    // MainMenu Constants
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
    public static final double HEALTH_DISPLAY_X_POSITION = 15;
    public static final double HEALTH_DISPLAY_Y_POSITION = 30;
    public static final int WIN_IMAGE_X_POSITION = 355;
    public static final int WIN_IMAGE_Y_POSITION = 175;
    public static final int LOSS_SCREEN_X_POSITION = 300;
    public static final int LOSS_SCREEN_Y_POSITION = 50;
    public static final String HEALTH_BAR_STYLE = "-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 5px;";

    // Endless Mode Constants
    public static final double ENDLESS_MODE_SPAWN_INCREMENT = 0.01; // Increment rate for spawn probability
    public static final double ENDLESS_MODE_MAX_SPAWN_PROBABILITY = 0.09; // Maximum spawn probability

    // Shield display constants
    public static final int SHIELD_X_POSITION = 1150;
    public static final int SHIELD_Y_POSITION = 500;

    // PauseScreen constants
    public static final double PAUSE_SPACING = 20.0;
    public static final double PAUSE_SCENE_WIDTH = 600.0;
    public static final double PAUSE_SCENE_HEIGHT = 400.0;
    public static final double PAUSE_DEFAULT_VOLUME = 50.0;
    public static final String PAUSE_TITLE_STYLE = null;
    public static final String BUTTON_STYLE = null;

    // Settings Page constants
    public static final String SETTINGS_BUTTON_STYLE = "-fx-font-size: 18px;";
    public static final double SETTINGS_SPACING = 20.0;
    public static final double SETTINGS_DEFAULT_VOLUME = 50.0;
    public static final double SETTINGS_SLIDER_WIDTH = 300.0;

    // Timer Background Constants
    public static final double TIMER_BACKGROUND_WIDTH = 123; // Width of the background
    public static final double TIMER_BACKGROUND_HEIGHT = 30; // Height of the background
    public static final String TIMER_BACKGROUND_COLOR = "#00000080"; // Black with 50% opacity
    public static final double TIMER_BACKGROUND_CORNER_RADIUS = 10; // Rounded corner radius

    // Font size and color for message labels
    public static final String MESSAGE_LABEL_STYLE = "-fx-font-size: 30px; -fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 10px;";

    // Positions for message display
    public static final double INSTRUCTION_LABEL_X = 490;
    public static final double INSTRUCTION_LABEL_Y = 300;

    // Message display durations
    public static final double INSTRUCTION_MESSAGE_DURATION = 5; // In seconds

}