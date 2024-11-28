package com.example.demo.level;

import com.example.demo.model.Boss;
import com.example.demo.view.LevelView;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_BOSSES = 3; // Total number of bosses

    private int spawnedBossCount = 0; // Track how many bosses are spawned
    private boolean isPlaneVisible = true; // Default visibility of the user plane

    private Rectangle redContainer; // Red container for the user plane

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        initializeUserPlane();
    }

    private void initializeUserPlane() {
        getUser().setVisible(true);
        initializeRedContainer(); // Create and initialize the red container
    }

    @Override
    protected void initializeFriendlyUnits() {
        getUser().addToParent(getRoot());
        addRedContainerToRoot(); // Add the red container to the root
    }

    private void initializeRedContainer() {
        redContainer = new Rectangle();
        redContainer.setStroke(Color.RED); // Red border
        redContainer.setFill(Color.TRANSPARENT); // Transparent fill
        redContainer.setStrokeWidth(2);
        updateRedContainerPosition(); // Position it initially
    }

    private void addRedContainerToRoot() {
        if (!getRoot().getChildren().contains(redContainer)) {
            getRoot().getChildren().add(redContainer);
        }
    }

    private void updateRedContainerPosition() {
        if (getUser() == null) return;

        // Get the user plane's bounds
        double userX = getUser().getLayoutX() + getUser().getTranslateX();
        double userY = getUser().getLayoutY() + getUser().getTranslateY();
        double userWidth = getUser().getBoundsInParent().getWidth();
        double userHeight = getUser().getBoundsInParent().getHeight();

        // Shrink the red container size
        double shrinkFactor = 0.5;
        double shrinkWidth = userWidth * shrinkFactor;
        double shrinkHeight = userHeight * shrinkFactor;

        // Update red container size and position
        redContainer.setWidth(shrinkWidth);
        redContainer.setHeight(shrinkHeight);
        redContainer.setX(userX + (userWidth - shrinkWidth) / 2);
        redContainer.setY(userY + (userHeight - shrinkHeight) / 2);
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
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        setupKeyListeners(scene);
        return scene;
    }

    private void setupKeyListeners(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D) {
                toggleUserPlaneVisibility();
            }
        });
    }

    private void toggleUserPlaneVisibility() {
        isPlaneVisible = !isPlaneVisible; // Toggle visibility state
        getUser().setVisible(isPlaneVisible); // Update plane visibility

        if (!isPlaneVisible) {
            System.out.println("UserPlane is now invisible. Boss projectiles will pass through.");
        } else {
            System.out.println("UserPlane is now visible. Boss projectiles can collide.");
        }
    }

    @Override
    protected void updateScene() {
        super.updateScene();
        updateRedContainerPosition(); // Update the red container's position
    }
}
