package com.example.demo.display;

import com.example.demo.utilities.Constant;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Represents a "Game Over" display component, including an image and an instruction label.
 * This class is used to visually indicate the end of the game and guide the user to return
 * to the leaderboard.
 */
public class GameOverImage extends Pane {

    /** The "Game Over" image displayed on the screen. */
    private final ImageView gameOverImage;
    /** The instruction label displayed below the "Game Over" image. */
    private final Label instructionLabel;

    /**
     * Constructs a new {@code GameOverImage} with a specified position.
     *
     * @param xPosition the X-coordinate of the top-left corner of the "Game Over" image.
     * @param yPosition the Y-coordinate of the top-left corner of the "Game Over" image.
     */
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

    /**
     * Displays the "Game Over" image and instruction label.
     */
    public void showGameOverImage() {
        gameOverImage.setVisible(true);
        instructionLabel.setVisible(true);
    }

    /**
     * Hides the "Game Over" image and instruction label.
     */
    public void hideGameOverImage() {
        gameOverImage.setVisible(false);
        instructionLabel.setVisible(false);
    }
}
