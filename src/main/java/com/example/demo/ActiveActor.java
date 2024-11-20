package com.example.demo;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.BoundingBox;

public abstract class ActiveActor extends ImageView {

    private static final String IMAGE_LOCATION = "/com/example/demo/images/";
    private static final double SHRINK_FACTOR = 0.6;
    private final Rectangle redContainer;

    public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        initializeImage(imageName, imageHeight, initialXPos, initialYPos);
        redContainer = createRedContainer();
        updateRedContainerPosition(); // Set the initial position and size
    }

    private void initializeImage(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
        this.setLayoutX(initialXPos);
        this.setLayoutY(initialYPos);
        this.setFitHeight(imageHeight);
        this.setPreserveRatio(true);
    }

    private Rectangle createRedContainer() {
        Rectangle container = new Rectangle();
        container.setStroke(Color.RED); // Red border
        container.setFill(Color.TRANSPARENT); // Transparent fill
        container.setStrokeWidth(2);
        return container;
    }

    public void addToParent(Group parent) {
        parent.getChildren().addAll(this, redContainer); // Add the actor and its red container to the parent
    }

    private void updateRedContainerPosition() {
        Bounds originalBounds = getBoundsInParent();

        // Calculate shrunk dimensions
        double shrinkWidth = originalBounds.getWidth() * SHRINK_FACTOR;
        double shrinkHeight = originalBounds.getHeight() * SHRINK_FACTOR;

        // Update red container size and position
        redContainer.setWidth(shrinkWidth);
        redContainer.setHeight(shrinkHeight);
        redContainer.setX(originalBounds.getMinX() + (originalBounds.getWidth() - shrinkWidth) / 2);
        redContainer.setY(originalBounds.getMinY() + (originalBounds.getHeight() - shrinkHeight) / 2);
    }

    public void updateRedContainer() {
        updateRedContainerPosition();
    }

    public void removeRedContainer() {
        Group parentGroup = (Group) this.getParent();
        if (parentGroup != null) {
            parentGroup.getChildren().remove(redContainer);
        }
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

    public abstract void updatePosition();

    protected void moveHorizontally(double horizontalMove) {
        setTranslateX(getTranslateX() + horizontalMove);
        updateRedContainer(); // Update red container position after moving
    }

    protected void moveVertically(double verticalMove) {
        setTranslateY(getTranslateY() + verticalMove);
        updateRedContainer(); // Update red container position after moving
    }
}
