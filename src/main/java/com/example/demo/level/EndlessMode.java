package com.example.demo.level;

import com.example.demo.manager.LevelManager;
import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.EnemyPlane;
import com.example.demo.utilities.Constant;
import com.example.demo.view.LevelView;

public class EndlessMode extends LevelManager {

    private double spawnProbability = Constant.LEVEL_ONE_ENEMY_SPAWN_PROBABILITY;

    public EndlessMode(double screenHeight, double screenWidth) {
        super(Constant.LEVEL_ONE_BACKGROUND, screenHeight, screenWidth, Constant.PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        if (Math.random() < spawnProbability) {
            double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
            ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
            addEnemyUnit(newEnemy);
        }
        adjustDifficulty();
    }

    private void adjustDifficulty() {
        spawnProbability = Math.min(spawnProbability + Constant.ENDLESS_MODE_SPAWN_INCREMENT, Constant.ENDLESS_MODE_MAX_SPAWN_PROBABILITY);
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), Constant.PLAYER_INITIAL_HEALTH);
    }
}
