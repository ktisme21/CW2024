package com.example.demo.display;

import com.example.demo.utilities.Constant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinImage extends ImageView {

    public WinImage(double xPosition, double yPosition) {
        this.setImage(new Image(getClass().getResource(Constant.WIN_IMAGE).toExternalForm()));
        this.setVisible(false);
        this.setFitHeight(Constant.WIN_IMAGE_HEIGHT);
        this.setFitWidth(Constant.WIN_IMAGE_WIDTH);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }

    public void showWinImage() {
        this.setVisible(true);
    }
}
