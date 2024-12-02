package com.example.demo.display;

import com.example.demo.utilities.Constant;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GameOverImage extends Pane {

    private final ImageView gameOverImage;
    private final Label instructionLabel;

    public GameOverImage(double xPosition, double yPosition) {
        // Initialize the game over image
        gameOverImage = new ImageView(new Image(getClass().getResource(Constant.GAME_OVER_IMAGE).toExternalForm()));
        gameOverImage.setFitWidth(Constant.GAME_OVER_IMAGE_WIDTH);
        gameOverImage.setFitHeight(Constant.GAME_OVER_IMAGE_HEIGHT);
        gameOverImage.setPreserveRatio(true);
        gameOverImage.setLayoutX(xPosition + 50);
        gameOverImage.setLayoutY(yPosition);

        // Initialize the instruction label
        double labelOffsetX = -350;
        double labelOffsetY = -100;
        instructionLabel = new Label("Press 'Q' to return to leaderboard");
        instructionLabel.setStyle(Constant.WIN_IMAGE_LABEL_STYLE);

        // Position the label below the game over image
        instructionLabel.setLayoutX(xPosition + (Constant.GAME_OVER_IMAGE_WIDTH - instructionLabel.prefWidth(-1)) / 2 + labelOffsetX);
        instructionLabel.setLayoutY(yPosition + Constant.GAME_OVER_IMAGE_HEIGHT + Constant.WIN_IMAGE_LABEL_PADDING + labelOffsetY);

        // Add both elements to the pane
        this.getChildren().addAll(gameOverImage, instructionLabel);
    }

    public void showGameOverImage() {
        gameOverImage.setVisible(true);
        instructionLabel.setVisible(true);
    }

    public void hideGameOverImage() {
        gameOverImage.setVisible(false);
        instructionLabel.setVisible(false);
    }
}
