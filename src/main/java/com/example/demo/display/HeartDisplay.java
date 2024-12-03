package com.example.demo.display;

import com.example.demo.utilities.Constant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The {@code HeartDisplay} class manages the display of heart icons representing the player's health.
 */
public class HeartDisplay {

    private HBox container;
    private double containerXPosition;
    private double containerYPosition;
    private int numberOfHeartsToDisplay;

    /**
     * Constructs a new {@code HeartDisplay} instance.
     *
     * @param xPosition The X-coordinate for the container's position.
     * @param yPosition The Y-coordinate for the container's position.
     * @param heartsToDisplay The initial number of hearts to display.
     */
    public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        this.numberOfHeartsToDisplay = heartsToDisplay;
        initializeContainer();
        initializeHearts();
    }

    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);
    }

    private void initializeHearts() {
        for (int i = 0; i < numberOfHeartsToDisplay; i++) {
            ImageView heart = new ImageView(new Image(getClass().getResource(Constant.HEART_IMAGE).toExternalForm()));

            heart.setFitHeight(Constant.HEART_HEIGHT);
            heart.setPreserveRatio(true);
            container.getChildren().add(heart);
        }
    }

    /**
     * Removes a heart from the display.
     */
    public void removeHeart() {
        if (!container.getChildren().isEmpty())
            container.getChildren().remove(Constant.FIRST_ITEM_INDEX);
    }

    /**
     * Retrieves the container holding the heart images.
     *
     * @return The container {@link HBox}.
     */
    public HBox getContainer() {
        return container;
    }
}
