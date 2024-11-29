package com.example.demo.level;

import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.EnemyPlane;
import com.example.demo.utilities.Constant;
import com.example.demo.view.LevelView;

public class LevelOne extends LevelParent {

    public LevelOne(double screenHeight, double screenWidth) {
        super(Constant.LEVEL_ONE_BACKGROUND, screenHeight, screenWidth, Constant.PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            goToNextLevel(Constant.LEVEL_ONE_NEXT);
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        getUser().addToParent(getRoot());
    }

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

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), Constant.PLAYER_INITIAL_HEALTH);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= Constant.LEVEL_ONE_KILLS_TO_ADVANCE && !isTransitioned();
    }
}
