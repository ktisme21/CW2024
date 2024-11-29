package com.example.demo.display;

import com.example.demo.utilities.Constant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class WinImage extends Pane {

    private final ImageView winImage;
    private final Label instructionLabel;

    public WinImage(double xPosition, double yPosition) {
        // Initialize the win image
        winImage = new ImageView(new Image(getClass().getResource(Constant.WIN_IMAGE).toExternalForm()));
        winImage.setFitHeight(Constant.WIN_IMAGE_HEIGHT);
        winImage.setFitWidth(Constant.WIN_IMAGE_WIDTH);
        winImage.setLayoutX(xPosition);
        winImage.setLayoutY(yPosition);
        winImage.setVisible(false);

        // Initialize the instruction label
        instructionLabel = new Label("Press 'Q' to return to the leaderboard");
        instructionLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 5px;");
        instructionLabel.setVisible(false);

        // Position the label below the win image
        instructionLabel.setLayoutX(xPosition + (Constant.WIN_IMAGE_WIDTH - instructionLabel.prefWidth(-1)) / 2);
        instructionLabel.setLayoutY(yPosition + Constant.WIN_IMAGE_HEIGHT + 10);

        // Add both elements to the pane
        this.getChildren().addAll(winImage, instructionLabel);
    }

    public void showWinImage() {
        winImage.setVisible(true);
        instructionLabel.setVisible(true);
    }

    public void hideWinImage() {
        winImage.setVisible(false);
        instructionLabel.setVisible(false);
    }
}
