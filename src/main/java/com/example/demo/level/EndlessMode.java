package com.example.demo.level;

import com.example.demo.manager.LevelManager;
import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.EnemyPlane;
import com.example.demo.utilities.Constant;
import com.example.demo.view.LevelView;

/**
 * Represents the Endless Mode of the game.
 * In this mode, enemies spawn indefinitely, and the player must survive as long as possible.
 * The difficulty increases gradually as the spawn probability of enemies increases over time.
 */
public class EndlessMode extends LevelManager {

    /** The probability of spawning a new enemy during each frame. */
    private double spawnProbability = Constant.LEVEL_ONE_ENEMY_SPAWN_PROBABILITY;

    /**
     * Constructs an instance of the Endless Mode.
     *
     * @param screenHeight the height of the game screen.
     * @param screenWidth the width of the game screen.
     */
    public EndlessMode(double screenHeight, double screenWidth) {
        super(Constant.LEVEL_ONE_BACKGROUND, screenHeight, screenWidth, Constant.PLAYER_INITIAL_HEALTH, 0);
    }

    /**
     * Checks if the game is over by evaluating the player's state.
     * If the player's plane is destroyed, the game is marked as lost.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
    }

    /**
     * Spawns enemy units on the screen at random positions.
     * The spawn probability increases over time to make the game progressively more difficult.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (Math.random() < spawnProbability) {
            double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
            ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
            addEnemyUnit(newEnemy);
        }
        adjustDifficulty();
    }

    /**
     * Gradually increases the difficulty of the game by raising the spawn probability of enemies.
     * The spawn probability is capped at a defined maximum value.
     */
    private void adjustDifficulty() {
        spawnProbability = Math.min(spawnProbability + Constant.ENDLESS_MODE_SPAWN_INCREMENT, Constant.ENDLESS_MODE_MAX_SPAWN_PROBABILITY);
    }

    /**
     * Creates and returns the LevelView for this mode.
     * The LevelView is responsible for displaying game-related UI components, such as health bars and score.
     *
     * @return the LevelView instance for Endless Mode.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), Constant.PLAYER_INITIAL_HEALTH);
    }

    public double getSpawnProbability() {
        return spawnProbability;
    }

}
