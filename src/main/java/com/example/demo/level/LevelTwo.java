package com.example.demo.level;

import com.example.demo.display.ShieldImage;
import com.example.demo.model.Boss;
import com.example.demo.view.LevelView;
import com.example.demo.view.LevelViewLevelTwo;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private final ShieldImage shieldImage;
	private LevelViewLevelTwo levelView;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
		this.shieldImage = new ShieldImage(1150, 500);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
			addShieldImage();
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	private void addShieldImage() {
		shieldImage.setVisible(false); // Initially hidden
		getRoot().getChildren().add(shieldImage); // Directly add ShieldImage
	}

	@Override
    protected void updateLevelView() {
        super.updateLevelView();

        // Update shield visibility based on boss state
        if (boss.isShielded()) {
            shieldImage.showShield();
        } else {
            shieldImage.hideShield();
        }
    }

}
