package com.example.demo.level;

import com.example.demo.display.ShieldImage;
import com.example.demo.manager.LevelManager;
import com.example.demo.model.Boss;
import com.example.demo.utilities.Constant;
import com.example.demo.view.LevelView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * The {@code LevelTwo} class represents the second level of the game.
 * It features a boss enemy with a shield and progression logic.
 */
public class LevelTwo extends LevelManager {

    private final Boss boss;
    private final ShieldImage shieldImage;
    private boolean isBlinking;

    /**
     * Constructs a new {@code LevelTwo} instance.
     *
     * @param screenHeight The height of the game screen.
     * @param screenWidth The width of the game screen.
     */
    public LevelTwo(double screenHeight, double screenWidth) {
        super(Constant.LEVEL_TWO_BACKGROUND, screenHeight, screenWidth, Constant.PLAYER_INITIAL_HEALTH, Constant.BOSS_COUNT);
        boss = new Boss();
        this.shieldImage = new ShieldImage(0, 0);
        this.isBlinking = false;
    }

    /**
     * Initializes the player's plane as a friendly unit.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getUser().addToParent(getRoot());
    }

    /**
     * Checks if the game is over or if the boss is destroyed to advance to the next level.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            goToNextLevel(Constant.LEVEL_TWO_NEXT);
        }
    }

    /**
     * Spawns the boss enemy unit and its shield if not already present.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() < 1) {
            addEnemyUnit(boss);

            if (!getRoot().getChildren().contains(shieldImage)) {
                addShieldImage();
            }
        }
    }

    private void addShieldImage() {
        shieldImage.setVisible(false);
        shieldImage.setOpacity(1.0);
        if (!getRoot().getChildren().contains(shieldImage)) {
            getRoot().getChildren().add(shieldImage);
        }
    }

    /**
     * Updates the shield's visibility and position if the boss is shielded.
     */
    @Override
    protected void updateLevelView() {
        super.updateLevelView();

        if (boss.isShielded()) {
            if (!shieldImage.isVisible() && !isBlinking) {
                startShieldBlinkAnimation();
            } else {
                updateShieldPosition();
            }
        } else {
            shieldImage.hideShield();
        }
    }

    private void updateShieldPosition() {
        double shieldOffsetX = -40;
        double shieldOffsetY = 0;

        shieldImage.setLayoutX(boss.getLayoutX() + boss.getTranslateX() + shieldOffsetX);
        shieldImage.setLayoutY(boss.getLayoutY() + boss.getTranslateY() + shieldOffsetY);
    }

    private void startShieldBlinkAnimation() {
        isBlinking = true;

        Timeline blinkTimeline = new Timeline(
            new KeyFrame(Duration.millis(300), e -> {
                if (shieldImage.isVisible()) {
                    shieldImage.setVisible(false);
                } else {
                    shieldImage.setVisible(true);
                    shieldImage.setOpacity(0.5);
                }
            })
        );
        blinkTimeline.setCycleCount(8);
        blinkTimeline.setOnFinished(e -> {
            shieldImage.setVisible(true);
            shieldImage.setOpacity(1.0);
            isBlinking = false;
        });
        blinkTimeline.play();
    }

    /**
     * Instantiates the view for this level.
     *
     * @return A {@link LevelView} instance.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), Constant.PLAYER_INITIAL_HEALTH);
    }
}
