package com.example.demo.level;

import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.Boss;
import com.example.demo.projectiles.UserProjectile;
import com.example.demo.utilities.Constant;
import com.example.demo.view.LevelView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LevelThree extends LevelParent {

    private int spawnedBossCount = 0;
    private boolean isPlaneVisible = true;
    private Rectangle redContainer;

    public LevelThree(double screenHeight, double screenWidth) {
        super(Constant.LEVEL_THREE_BACKGROUND, screenHeight, screenWidth, Constant.PLAYER_INITIAL_HEALTH);
        initializeUserPlane();
    }

    private void initializeUserPlane() {
        getUser().setVisible(true);
        initializeRedContainer();
    }

    @Override
    protected void initializeFriendlyUnits() {
        getUser().addToParent(getRoot());
        addRedContainerToRoot();
    }

    private void initializeRedContainer() {
        redContainer = new Rectangle();
        redContainer.setStroke(Color.valueOf(Constant.RED_CONTAINER_STROKE_COLOR));
        redContainer.setFill(Color.valueOf(Constant.RED_CONTAINER_FILL_COLOR));
        redContainer.setStrokeWidth(Constant.RED_CONTAINER_STROKE_WIDTH);
        updateRedContainerPosition();
    }

    private void addRedContainerToRoot() {
        if (!getRoot().getChildren().contains(redContainer)) {
            getRoot().getChildren().add(redContainer);
        }
    }

    private void updateRedContainerPosition() {
        if (getUser() == null) return;

        double userX = getUser().getLayoutX() + getUser().getTranslateX();
        double userY = getUser().getLayoutY() + getUser().getTranslateY();
        double userWidth = getUser().getBoundsInParent().getWidth();
        double userHeight = getUser().getBoundsInParent().getHeight();

        double shrinkWidth = userWidth * Constant.RED_CONTAINER_SHRINK_FACTOR;
        double shrinkHeight = userHeight * Constant.RED_CONTAINER_SHRINK_FACTOR;

        redContainer.setWidth(shrinkWidth);
        redContainer.setHeight(shrinkHeight);
        redContainer.setX(userX + (userWidth - shrinkWidth) / 2);
        redContainer.setY(userY + (userHeight - shrinkHeight) / 2);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }

        if (spawnedBossCount >= Constant.LEVEL_THREE_TOTAL_BOSSES && getCurrentNumberOfEnemies() == 0) {
            winGame();
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        while (spawnedBossCount < Constant.LEVEL_THREE_TOTAL_BOSSES) {
            spawnBoss();
        }
    }

    private void spawnBoss() {
        Boss boss = new Boss();
        addEnemyUnit(boss);
        spawnedBossCount++;
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), Constant.PLAYER_INITIAL_HEALTH);
    }

    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        setupKeyListeners(scene);
        displayInstruction();
        return scene;
    }

    private void displayInstruction() {
        Label instructionLabel = new Label("Press 'D' to toggle visibility!");
        instructionLabel.setStyle(Constant.MESSAGE_LABEL_STYLE);
    
        // Set predefined position
        instructionLabel.setLayoutX(Constant.INSTRUCTION_LABEL_X);
        instructionLabel.setLayoutY(Constant.INSTRUCTION_LABEL_Y);
    
        getRoot().getChildren().add(instructionLabel);
    
        // Remove the message after the specified duration
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(Constant.INSTRUCTION_MESSAGE_DURATION),
                event -> getRoot().getChildren().remove(instructionLabel))
        );
        timeline.play();
    }

    private void setupKeyListeners(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D) {
                toggleUserPlaneVisibility();
            } else if (event.getCode() == KeyCode.SPACE) {
                // Delegate projectile firing to the UserPlane's fireProjectile method
                ActiveActorDestructible projectile = getUser().fireProjectile();
                if (projectile != null) { // Ensure a projectile was created
                    addProjectileToScene((UserProjectile) projectile);
                }
            }
        });
    }
    
    // Helper method to add projectile to the scene
    private void addProjectileToScene(UserProjectile projectile) {
        getRoot().getChildren().add(projectile);
        getUserProjectiles().add(projectile);
    }

    private void toggleUserPlaneVisibility() {
        isPlaneVisible = !isPlaneVisible;
        getUser().setVisible(isPlaneVisible);
    
        if (!isPlaneVisible) {
            // Clear or stop projectiles when the user plane becomes invisible
            getUserProjectiles().forEach(projectile -> projectile.setVisible(false));
            System.out.println("UserPlane is now invisible. ");
        } else {
            System.out.println("UserPlane is now visible.");
        }
    }

    @Override
    protected void updateScene() {
        super.updateScene();
        updateRedContainerPosition();
    }
}
