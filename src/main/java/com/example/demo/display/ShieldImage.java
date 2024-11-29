package com.example.demo.display;

import com.example.demo.utilities.Constant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {

    public ShieldImage(double xPosition, double yPosition) {
        super(new Image(ShieldImage.class.getResource(Constant.SHIELD_IMAGE).toExternalForm()));
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        this.setFitHeight(Constant.SHIELD_SIZE);
        this.setFitWidth(Constant.SHIELD_SIZE);
    }

    public void showShield() {
        this.setVisible(true);
    }

    public void hideShield() {
        this.setVisible(false);
    }
}
