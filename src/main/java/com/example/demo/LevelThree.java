package com.example.demo;

import com.example.demo.view.LevelView;
import javafx.scene.input.KeyCode;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_BOSSES = 3; // Total number of bosses

    private int spawnedBossCount = 0; // Track how many bosses are spawned
    private boolean isPlaneVisible = true; // Default visibility of the user plane

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        initializeUserPlane();
    }

    private void initializeUserPlane() {
        getUser().setVisible(true); // Initially make the user plane visible
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser()); // Add user plane to the game
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame(); // End the game if the player is destroyed
        }

        if (spawnedBossCount >= TOTAL_BOSSES && getCurrentNumberOfEnemies() == 0) {
            winGame(); // Trigger win if all bosses are defeated
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        // Spawn bosses until the TOTAL_BOSSES limit is reached
        while (spawnedBossCount < TOTAL_BOSSES) {
            spawnBoss();
        }
    }

    private void spawnBoss() {
        Boss boss = new Boss(); // Create a new boss
        addEnemyUnit(boss); // Add the boss to the game
        spawnedBossCount++; // Increment the boss spawn count
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    @Override
    public javafx.scene.Scene initializeScene() {
        javafx.scene.Scene scene = super.initializeScene();
        setupKeyListeners(scene);
        return scene;
    }

    private void setupKeyListeners(javafx.scene.Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D) {
                toggleUserPlaneVisibility();
            }
        });
    }

    private void toggleUserPlaneVisibility() {
        isPlaneVisible = !isPlaneVisible; // Toggle visibility state
        getUser().setVisible(isPlaneVisible); // Update plane visibility
    }
}
