package com.example.demo.view;

import com.example.demo.utilities.Constant;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class BackgroundUtil {

    public static void setBackgroundImage(Pane pane) {
        Image backgroundImage = new Image(BackgroundUtil.class.getResource(Constant.BACKGROUND_IMAGE_PATH).toExternalForm());

        BackgroundPosition position = new BackgroundPosition(
            BackgroundPosition.DEFAULT.getHorizontalSide(), 0, false,
            BackgroundPosition.DEFAULT.getVerticalSide(), 0, false
        );

        BackgroundSize backgroundSize = new BackgroundSize(
            1300, 750,  // Fixed width and height for the background
            false, false, // Width and height not proportional
            false, false
        );

        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            position, // Apply the custom position
            backgroundSize
        );

        pane.setBackground(new Background(background));
    }
}
