package com.example.demo.level;

import com.example.demo.display.ShieldImage;
import com.example.demo.model.Boss;
import com.example.demo.view.LevelView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.level.LevelThree";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int SHIELD_X_POSITION = 1150;
    private static final int SHIELD_Y_POSITION = 500;

    private final Boss boss;
    private final ShieldImage shieldImage;
    private boolean isBlinking;

    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.boss = new Boss();
        this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
        this.isBlinking = false;

        addShieldImageToRoot();
    }

    private void addShieldImageToRoot() {
        shieldImage.setVisible(false); // Initially hidden
        shieldImage.setOpacity(1.0); // Fully visible when not blinking
        getRoot().getChildren().add(shieldImage); // Add shield image to the root
    }

    @Override
    protected void initializeFriendlyUnits() {
        getUser().addToParent(getRoot()); // Add the user's plane to the root
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            goToNextLevel(NEXT_LEVEL);
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        // Ensure only one boss is added
        if (getCurrentNumberOfEnemies() < 1) {
            addEnemyUnit(boss);
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH); // Use LevelView directly
    }

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
        // Position the shield slightly in front and above the boss
        double shieldOffsetX = -40; // Horizontal offset
        double shieldOffsetY = 0;   // Vertical offset

        shieldImage.setLayoutX(boss.getLayoutX() + boss.getTranslateX() + shieldOffsetX);
        shieldImage.setLayoutY(boss.getLayoutY() + boss.getTranslateY() + shieldOffsetY);
    }

    private void startShieldBlinkAnimation() {
        isBlinking = true;

        // Create a timeline for blinking animation
        Timeline blinkTimeline = new Timeline(
            new KeyFrame(Duration.millis(300), e -> {
                if (shieldImage.isVisible()) {
                    shieldImage.setVisible(false);
                } else {
                    shieldImage.setVisible(true);
                    shieldImage.setOpacity(0.5); // Semi-transparent while blinking
                }
            })
        );
        blinkTimeline.setCycleCount(8); // Blink 4 times (on and off)
        blinkTimeline.setOnFinished(e -> {
            shieldImage.setVisible(true); // Ensure the shield is visible after blinking
            shieldImage.setOpacity(1.0); // Fully opaque after blinking
            isBlinking = false;          // Reset blinking state
        });
        blinkTimeline.play();
    }

    public void showShield() {
        shieldImage.showShield();
    }

    public void hideShield() {
        shieldImage.hideShield();
    }
}
