package com.example.demo.view;

import com.example.demo.utilities.Constant;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class BackgroundUtil {

    public static void setBackgroundImage(Pane pane) {
        Image backgroundImage = new Image(BackgroundUtil.class.getResource(Constant.BACKGROUND_IMAGE_PATH).toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(
            BackgroundSize.AUTO, BackgroundSize.AUTO, 
            false, false, true, true
        );
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            backgroundSize
        );
        pane.setBackground(new Background(background));
    }

}
