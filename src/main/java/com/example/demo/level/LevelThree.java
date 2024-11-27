package com.example.demo.level;

import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.Boss;
import com.example.demo.view.LevelView;

import javafx.scene.Scene;
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
        getUser().addToParent(getRoot());
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
            // } else if (event.getCode() == KeyCode.SPACE) {
            //     handleSpacebarAction(); // Check before firing projectiles
            }
        });
    }

    // private void handleSpacebarAction() {
    //     if (getUser().isVisible()) {
    //         // Fire a projectile from the UserPlane
    //         ActiveActorDestructible projectile = getUser().fireProjectile();
    //         if (projectile != null) {
    //             // Set the projectile's position relative to the UserPlane
    //             double projectileX = getUser().getLayoutX() + getUser().getTranslateX() + 110; // Adjust X offset
    //             double projectileY = getUser().getLayoutY() + getUser().getTranslateY() + 20; // Adjust Y offset
    //             projectile.setLayoutX(projectileX);
    //             projectile.setLayoutY(projectileY);
    
    //             // Add the projectile to the scene
    //             getRoot().getChildren().add(projectile);
    //         }
    //     } else {
    //         System.out.println("Cannot fire projectiles while the UserPlane is invisible.");
    //     }
    // }
    

    private void toggleUserPlaneVisibility() {
        isPlaneVisible = !isPlaneVisible; // Toggle visibility state
        getUser().setVisible(isPlaneVisible); // Update plane visibility
    
        if (!isPlaneVisible) {
            System.out.println("UserPlane is now invisible. Boss projectiles will pass through.");
        } else {
            System.out.println("UserPlane is now visible. Boss projectiles can collide.");
        }
    }
    
}
