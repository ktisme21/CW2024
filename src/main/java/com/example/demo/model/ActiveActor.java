package com.example.demo.model;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class ActiveActor extends ImageView {

    private static final String IMAGE_LOCATION = "/com/example/demo/images/";
    private static final double SHRINK_FACTOR = 0.5; // 80% of the original size

    public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        initializeImage(imageName, imageHeight, initialXPos, initialYPos);
    }

    private void initializeImage(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
        this.setLayoutX(initialXPos);
        this.setLayoutY(initialYPos);
        this.setFitHeight(imageHeight);
        this.setPreserveRatio(true);
    }

    public void addToParent(Group parent) {
        parent.getChildren().add(this); // Add the actor to the parent
    }

    public abstract void updatePosition();

    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(getTranslateX() + horizontalMove);
    }

    protected void moveVertically(double verticalMove) {
        this.setTranslateY(getTranslateY() + verticalMove);
    }

    public Bounds getCollisionBounds() {
        Bounds originalBounds = getBoundsInParent();
        double shrinkWidth = originalBounds.getWidth() * SHRINK_FACTOR;
        double shrinkHeight = originalBounds.getHeight() * SHRINK_FACTOR;

        return new BoundingBox(
            originalBounds.getMinX() + (originalBounds.getWidth() - shrinkWidth) / 2,
            originalBounds.getMinY() + (originalBounds.getHeight() - shrinkHeight) / 2,
            shrinkWidth,
            shrinkHeight
        );
    }
}
