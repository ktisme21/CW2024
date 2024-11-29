package com.example.demo.display;

import com.example.demo.utilities.Constant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {

    public GameOverImage(double xPosition, double yPosition) {
        // Load the image directly
        setImage(new Image(getClass().getResource(Constant.GAME_OVER_IMAGE).toExternalForm()));

        // Set layout positions
        setLayoutX(xPosition);
        setLayoutY(yPosition);

        // Set default dimensions (optional)
        setFitWidth(Constant.GAME_OVER_IMAGE_WIDTH);
        setFitHeight(Constant.GAME_OVER_IMAGE_HEIGHT);
        setPreserveRatio(true); // Preserve aspect ratio
    }
}
