package com.example.demo.view;

import com.example.demo.display.GameOverImage;
import com.example.demo.display.HeartDisplay;
import com.example.demo.display.ShieldImage;
import com.example.demo.display.WinImage;
import com.example.demo.utilities.Constant;

import javafx.scene.Group;
import javafx.scene.control.Label;

/**
 * Handles the visual representation of a game level, including heart display, win/lose images, and shields.
 */
public class LevelView {
	
	private final Group root;
	private final ShieldImage shieldImage;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private Label healthDisplay;
	
	/**
     * Constructs a {@code LevelView} with specified number of hearts to display.
     * 
     * @param root The root {@link Group} of the level.
     * @param heartsToDisplay Number of hearts to display.
     */
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

	// public void showHealth(String infoText) {
    //     if (healthDisplay == null) {
    //         healthDisplay = new Label();
    //         healthDisplay.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
    //         healthDisplay.setLayoutX(15); // Adjust positioning as needed
    //         healthDisplay.setLayoutY(80); // Place below the heart display
    //         root.getChildren().add(healthDisplay);
    //     }
    //     healthDisplay.setText(infoText);
    // }

}
