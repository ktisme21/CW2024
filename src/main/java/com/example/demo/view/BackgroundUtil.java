package com.example.demo.view;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

public class BackgroundUtil {

    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/background3.jpg";

    public static void setBackgroundImage(Pane pane) {
        Image backgroundImage = new Image(BackgroundUtil.class.getResource(BACKGROUND_IMAGE_PATH).toExternalForm());
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        pane.setBackground(new Background(background));
    }
}
