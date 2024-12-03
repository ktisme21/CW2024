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

    /**
     * Constructs a new {@code LevelThree} instance.
     *
     * @param screenHeight The height of the game screen.
     * @param screenWidth The width of the game screen.
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(Constant.LEVEL_THREE_BACKGROUND, screenHeight, screenWidth, Constant.PLAYER_INITIAL_HEALTH);
        initializeUserPlane();
    }

    /**
     * Initializes the user's plane and its associated red container for display.
     */
    private void initializeUserPlane() {
        getUser().setVisible(true);
        initializeRedContainer();
    }

    /**
     * Initializes the red container, which serves as a visual highlight for the user's plane.
     */
    private void initializeRedContainer() {
        redContainer = new Rectangle();
        redContainer.setStroke(Color.valueOf(Constant.RED_CONTAINER_STROKE_COLOR));
        redContainer.setFill(Color.valueOf(Constant.RED_CONTAINER_FILL_COLOR));
        redContainer.setStrokeWidth(Constant.RED_CONTAINER_STROKE_WIDTH);
        updateRedContainerPosition();
    }

    /**
     * Adds the red container to the game root if it is not already present.
     */
    private void addRedContainerToRoot() {
        if (!getRoot().getChildren().contains(redContainer)) {
            getRoot().getChildren().add(redContainer);
        }
    }

    /**
     * Updates the position and size of the red container to match the user's plane.
     */
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
        getUser().addToParent(getRoot());
        addRedContainerToRoot();
    }

    @Override
    protected void spawnEnemyUnits() {
        while (spawnedBossCount < Constant.LEVEL_THREE_TOTAL_BOSSES) {
            spawnBoss();
        }
    }

    /**
     * Spawns a boss enemy with reduced health and adds it to the game.
     */
    private void spawnBoss() {
        Boss boss = createBossWithReducedHealth();
        addEnemyUnit(boss);
        spawnedBossCount++;
    }

    /**
     * Creates a new boss instance with reduced health and disabled shield functionality.
     *
     * @return A {@link Boss} instance with custom attributes.
     */
    private Boss createBossWithReducedHealth() {
        Boss boss = new Boss();
        boss.setHealth(20);
        boss.setShieldProbability(0.0);
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

    /**
     * Displays an instructional label for the user, showing the available controls.
     */
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

    /**
     * Sets up key listeners for toggling visibility and firing projectiles.
     *
     * @param scene The game scene.
     */
    private void setupKeyListeners(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D) {
                toggleUserPlaneVisibility();
            } else if (event.getCode() == KeyCode.SPACE) {
                handleProjectileFiring();
            }
        });
    }

    /**
     * Handles the firing of projectiles by the user's plane.
     */
    private void handleProjectileFiring() {
        ActiveActorDestructible projectile = getUser().fireProjectile();
        if (projectile != null) {
            addProjectileToScene((UserProjectile) projectile);
        }
    }

    /**
     * Adds a fired projectile to the scene.
     *
     * @param projectile The user's fired projectile.
     */
    private void addProjectileToScene(UserProjectile projectile) {
        getRoot().getChildren().add(projectile);
        getUserProjectiles().add(projectile);
    }

    /**
     * Toggles the visibility of the user's plane and its projectiles.
     */
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
