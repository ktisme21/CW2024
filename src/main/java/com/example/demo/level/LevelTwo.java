package com.example.demo.level;

import com.example.demo.display.ShieldImage;
import com.example.demo.model.Boss;
import com.example.demo.view.LevelView;
import com.example.demo.view.LevelViewLevelTwo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private final ShieldImage shieldImage;
	private LevelViewLevelTwo levelView;
	private boolean isBlinking;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
		this.shieldImage = new ShieldImage(0, 0);
		this.isBlinking = false;
	}

	@Override
	protected void initializeFriendlyUnits() {
		getUser().addToParent(getRoot());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	//Handle error?
	@Override
	protected void spawnEnemyUnits() {
		// Ensure only one boss is added
		if (getCurrentNumberOfEnemies() < 1) {
			addEnemyUnit(boss);

			// Add shield image only once
			if (!getRoot().getChildren().contains(shieldImage)) {
				addShieldImage();
			}
		}
	}


	private void addShieldImage() {
		shieldImage.setVisible(false); // Initially hidden
		shieldImage.setOpacity(1.0); // Fully visible when not blinking
		if (!getRoot().getChildren().contains(shieldImage)) { // Check before adding
			getRoot().getChildren().add(shieldImage);
		}
	}


	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
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
        double shieldOffsetX = 0; // Horizontal offset
        double shieldOffsetY = 0; // Vertical offset

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
			isBlinking = false; // Reset blinking state
        });
        blinkTimeline.play();
    }

}
