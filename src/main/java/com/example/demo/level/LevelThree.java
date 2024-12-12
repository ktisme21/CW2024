package com.example.demo.level;

import com.example.demo.manager.LevelManager;
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

/**
 * The {@code LevelThree} class represents the third level of the game.
 * This level includes unique mechanics, such as a toggleable visibility feature
 * for the user's plane and spawning multiple bosses with reduced health.
 */
public class LevelThree extends LevelManager {

    private int spawnedBossCount = 0;
    private boolean isPlaneVisible = true;
    private Rectangle redContainer;

    public LevelThree(double screenHeight, double screenWidth) {
        super(Constant.LEVEL_THREE_BACKGROUND, screenHeight, screenWidth, Constant.PLAYER_INITIAL_HEALTH, Constant.LEVEL_THREE_BOSS_HEALTH);
        initializeUserPlane();
    }

    private void initializeUserPlane() {
        getUser().setVisible(true);
        initializeRedContainer();
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
    protected void initializeFriendlyUnits() {
        if (!getRoot().getChildren().contains(getUser())) {
            getUser().addToParent(getRoot());
        }
        addRedContainerToRoot();
    }

    @Override
    protected void spawnEnemyUnits() {
        while (spawnedBossCount < Constant.LEVEL_THREE_TOTAL_BOSSES) {
            spawnBoss();
        }
    }

    private void spawnBoss() {
        Boss boss = createBossWithReducedHealth();
        addEnemyUnit(boss);
        spawnedBossCount++;
    }

    private Boss createBossWithReducedHealth() {
        Boss boss = new Boss();
        boss.setHealth(Constant.LEVEL_THREE_BOSS_HEALTH); // Use the constant for LevelThree boss health
        boss.setShieldProbability(Constant.LEVEL_THREE_BOSS_SHIELD_PROBABILITY); // Disable shields
        return boss;
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (spawnedBossCount >= Constant.LEVEL_THREE_TOTAL_BOSSES && getCurrentNumberOfEnemies() == 0) {
            winGame();
        }
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
        instructionLabel.setLayoutX(Constant.INSTRUCTION_LABEL_X);
        instructionLabel.setLayoutY(Constant.INSTRUCTION_LABEL_Y);

        getRoot().getChildren().add(instructionLabel);

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
                handleProjectileFiring();
            }
        });
    }

    private void handleProjectileFiring() {
        ActiveActorDestructible projectile = getUser().fireProjectile();
        if (projectile != null) {
            addProjectileToScene((UserProjectile) projectile);
        }
    }

    private void addProjectileToScene(UserProjectile projectile) {
        getRoot().getChildren().add(projectile);
        getUserProjectiles().add(projectile);
    }

    private void toggleUserPlaneVisibility() {
        isPlaneVisible = !isPlaneVisible;
        getUser().setVisible(isPlaneVisible);

        if (!isPlaneVisible) {
            getUserProjectiles().forEach(projectile -> projectile.setVisible(false));
            System.out.println("UserPlane is now invisible.");
        } else {
            System.out.println("UserPlane is now visible.");
        }
    }

    @Override
    protected void updateScene() {
        super.updateScene();
        updateRedContainerPosition();
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), Constant.PLAYER_INITIAL_HEALTH);
    }
}
