package com.example.demo.display;

import com.example.demo.utilities.Constant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HeartDisplay {

    private HBox container;
    private double containerXPosition;
    private double containerYPosition;
    private int numberOfHeartsToDisplay;

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

    public void removeHeart() {
        if (!container.getChildren().isEmpty())
            container.getChildren().remove(Constant.FIRST_ITEM_INDEX);
    }

    public HBox getContainer() {
        return container;
    }
}
