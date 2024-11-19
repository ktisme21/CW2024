package com.example.demo;

import com.example.demo.view.LevelView;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void initializeFriendlyUnits() {
        // Add user plane to the root, no additional units for now
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        // Currently empty, implement game over logic later
    }

    @Override
    protected void spawnEnemyUnits() {
        // Currently empty, implement enemy spawn logic later
    }

    @Override
    protected LevelView instantiateLevelView() {
        // Instantiate the basic LevelView
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }
}
