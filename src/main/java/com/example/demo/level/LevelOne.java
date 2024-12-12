package com.example.demo.level;

import com.example.demo.manager.LevelManager;
import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.EnemyPlane;
import com.example.demo.utilities.Constant;
import com.example.demo.view.LevelView;

/**
 * The {@code LevelOne} class represents the first level of the game.
 * It defines enemy spawning, game-over checks, and progression logic.
 */
public class LevelOne extends LevelManager {

    /**
     * Constructs a new {@code LevelOne} instance.
     *
     * @param screenHeight The height of the game screen.
     * @param screenWidth The width of the game screen.
     */
    public LevelOne(double screenHeight, double screenWidth) {
        super(Constant.LEVEL_ONE_BACKGROUND, screenHeight, screenWidth, Constant.PLAYER_INITIAL_HEALTH, Constant.LEVEL_ONE_TOTAL_ENEMIES);
    }

    /**
     * Checks if the game is over or if the player has met the criteria to advance to the next level.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            goToNextLevel(Constant.LEVEL_ONE_NEXT);
        }
    }

    /**
     * Initializes the player's plane as a friendly unit.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getUser().addToParent(getRoot());
    }

    /**
     * Spawns enemy units based on the spawn probability and ensures the total number of enemies does not exceed the limit.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();

        if (currentNumberOfEnemies < Constant.LEVEL_ONE_TOTAL_ENEMIES) {
            if (Math.random() < Constant.LEVEL_ONE_ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);

                addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * Instantiates the view for this level.
     *
     * @return A {@link LevelView} instance.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), Constant.PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the player has achieved the required number of kills to advance to the next level.
     *
     * @return {@code true} if the player has reached the kill target; {@code false} otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= Constant.LEVEL_ONE_KILLS_TO_ADVANCE && !isTransitioned();
    }
}
