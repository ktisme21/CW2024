package com.example.demo.view;

import com.example.demo.display.GameOverImage;
import com.example.demo.display.HeartDisplay;
import com.example.demo.display.ShieldImage;
import com.example.demo.display.WinImage;
import com.example.demo.utilities.Constant;

import javafx.scene.Group;

public class LevelView {
	
	private final Group root;
	private final ShieldImage shieldImage;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	
	public LevelView(Group root, int heartsToDisplay) {
        this.root = root;
        this.heartDisplay = new HeartDisplay(Constant.HEART_DISPLAY_X_POSITION, Constant.HEART_DISPLAY_Y_POSITION, heartsToDisplay);
        this.winImage = new WinImage(Constant.WIN_IMAGE_X_POSITION, Constant.WIN_IMAGE_Y_POSITION);
        this.gameOverImage = new GameOverImage(Constant.LOSS_SCREEN_X_POSITION, Constant.LOSS_SCREEN_Y_POSITION);
		this.shieldImage = new ShieldImage(Constant.SHIELD_X_POSITION, Constant.SHIELD_Y_POSITION);
		addImagesToRoot();
    }
	
	private void addImagesToRoot() {
        root.getChildren().addAll(shieldImage);
    }

	public void showShield() {
        shieldImage.showShield();
    }

    public void hideShield() {
        shieldImage.hideShield();
    }

	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}
	
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}
	
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

}
