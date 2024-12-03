package com.example.demo.display;

import com.example.demo.utilities.Constant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@code ShieldImage} class represents a shield image that can be shown or hidden during gameplay.
 */
public class ShieldImage extends ImageView {

    /**
     * Constructs a new {@code ShieldImage} instance.
     *
     * @param xPosition The X-coordinate for the shield's position.
     * @param yPosition The Y-coordinate for the shield's position.
     */
    public ShieldImage(double xPosition, double yPosition) {
        super(new Image(ShieldImage.class.getResource(Constant.SHIELD_IMAGE).toExternalForm()));
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        this.setFitHeight(Constant.SHIELD_SIZE);
        this.setFitWidth(Constant.SHIELD_SIZE);
    }

    /**
     * Displays the shield image.
     */
    public void showShield() {
        this.setVisible(true);
    }

    /**
     * Hides the shield image.
     */
    public void hideShield() {
        this.setVisible(false);
    }
}
