package com.example.demo.view;

import com.example.demo.GameOverImage;
import com.example.demo.HeartDisplay;
import com.example.demo.WinImage;

import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LevelView {
    private static final double HEART_DISPLAY_X_POSITION = 5;
    private static final double HEART_DISPLAY_Y_POSITION = 25;
    private static final int WIN_IMAGE_X_POSITION = 355;
    private static final int WIN_IMAGE_Y_POSITION = 175;
    private static final int LOSS_SCREEN_X_POSITION = -160;
    private static final int LOSS_SCREEN_Y_POSITION = -375;
    private static final double TIMER_X_POSITION = 1200; // Adjust for top-right placement
    private static final double TIMER_Y_POSITION = 30;

    private final Group root;
    private final WinImage winImage;
    private final GameOverImage gameOverImage;
    private final HeartDisplay heartDisplay;
    private final Text timerText;

    public LevelView(Group root, int heartsToDisplay) {
        this.root = root;
        this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
        this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
        this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
        this.timerText = new Text(TIMER_X_POSITION, TIMER_Y_POSITION, "Time: 0");
        timerText.setFill(Color.WHITE);
        timerText.setFont(new Font("Arial", 20));
    }

    public void showHeartDisplay() {
        root.getChildren().add(heartDisplay.getContainer());
    }

    public void showTimer() {
        root.getChildren().add(timerText);
    }

    public void updateTimer(int elapsedTime) {
        timerText.setText("Time: " + elapsedTime);
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
