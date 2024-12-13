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
 * This level includes unique mechanics such as:
 * - Multiple bosses with reduced health.
 * - Toggleable visibility for the user's plane.
 * - A visual red container surrounding the user plane.
 */
public class LevelThree extends LevelManager {

    private int spawnedBossCount = 0;
    private boolean isPlaneVisible = true;
    private Rectangle redContainer;

    /**
     * Constructs a new {@code LevelThree} instance.
     *
     * @param screenHeight The height of the game screen.
     * @param screenWidth  The width of the game screen.
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(Constant.LEVEL_THREE_BACKGROUND, screenHeight, screenWidth, Constant.PLAYER_INITIAL_HEALTH, Constant.LEVEL_THREE_TOTAL_BOSSES);
        initializeUserPlane();
    }

    /**
     * Initializes the user's plane and sets its visibility to true.
     */
    private void initializeUserPlane() {
        getUser().setVisible(true);
        initializeRedContainer();
    }

    /**
     * Initializes the red container surrounding the user's plane.
     */
    private void initializeRedContainer() {
        redContainer = new Rectangle();
        redContainer.setStroke(Color.valueOf(Constant.RED_CONTAINER_STROKE_COLOR));
        redContainer.setFill(Color.valueOf(Constant.RED_CONTAINER_FILL_COLOR));
        redContainer.setStrokeWidth(Constant.RED_CONTAINER_STROKE_WIDTH);
        updateRedContainerPosition();
    }

    /**
     * Adds the red container to the scene if not already present.
     */
    private void addRedContainerToRoot() {
        if (!getRoot().getChildren().contains(redContainer)) {
            getRoot().getChildren().add(redContainer);
        }
    }

    /**
     * Updates the position of the red container to follow the user's plane.
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

    /**
     * Adds friendly units, including the user's plane and red container, to the scene.
     */
    @Override
    protected void initializeFriendlyUnits() {
        if (!getRoot().getChildren().contains(getUser())) {
            getUser().addToParent(getRoot());
        }
        addRedContainerToRoot();
    }

    /**
     * Spawns enemy units (bosses) until the required number is reached.
     */
    @Override
    protected void spawnEnemyUnits() {
        while (spawnedBossCount < Constant.LEVEL_THREE_TOTAL_BOSSES) {
            spawnBoss();
        }
    }

    /**
     * Spawns a boss with reduced health and increments the boss spawn count.
     */
    private void spawnBoss() {
        Boss boss = createBossWithReducedHealth();
        addEnemyUnit(boss);
        spawnedBossCount++;
    }

    /**
     * Creates a boss instance with reduced health and no shields.
     *
     * @return A {@link Boss} instance with the specified configurations.
     */
    private Boss createBossWithReducedHealth() {
        Boss boss = new Boss();
        boss.setHealth(Constant.LEVEL_THREE_BOSS_HEALTH);
        boss.setShieldProbability(Constant.LEVEL_THREE_BOSS_SHIELD_PROBABILITY);
        return boss;
    }

    /**
     * Checks if the game is over or if the player has won.
     * The game ends if the user plane is destroyed or all bosses are defeated.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (spawnedBossCount >= Constant.LEVEL_THREE_TOTAL_BOSSES && getCurrentNumberOfEnemies() == 0) {
            winGame();
        }
    }

    /**
     * Initializes the scene with additional setup for key listeners and instructions.
     *
     * @return The initialized {@link Scene}.
     */
    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        setupKeyListeners(scene);
        displayInstruction();
        return scene;
    }

    /**
     * Displays an instruction message to the player for toggling visibility.
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
     * Sets up key listeners for toggling plane visibility and firing projectiles.
     *
     * @param scene The scene to attach the listeners to.
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
     * Adds a user projectile to the scene and projectile list.
     *
     * @param projectile The projectile to be added.
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

    /**
     * Updates the scene, including the position of the red container.
     */
    @Override
    protected void updateScene() {
        super.updateScene();
        updateRedContainerPosition();
    }

    /**
     * Instantiates the {@link LevelView} for this level.
     *
     * @return A new instance of {@link LevelView}.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), Constant.PLAYER_INITIAL_HEALTH);
    }
}
