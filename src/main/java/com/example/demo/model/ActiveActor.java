package com.example.demo.model;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract class representing an active actor in the game. 
 * This class provides common functionality for positioning and movement.
 */
public abstract class ActiveActor extends ImageView {

    private static final String IMAGE_LOCATION = "/com/example/demo/images/";
    private static final double SHRINK_FACTOR = 0.5; // 80% of the original size

    /**
     * Initializes the active actor with its image and position.
     *
     * @param imageName   the name of the image file for the actor.
     * @param imageHeight the height to scale the actor's image.
     * @param initialXPos the initial x-coordinate of the actor.
     * @param initialYPos the initial y-coordinate of the actor.
     */
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

    /**
     * Adds the actor to a parent {@link Group}.
     *
     * @param parent the parent group to which the actor is added.
     */
    public void addToParent(Group parent) {
        parent.getChildren().add(this); // Add the actor to the parent
    }

    /**
     * Updates the position of the actor. This method must be implemented by subclasses.
     */
    public abstract void updatePosition();

    /**
     * Moves the actor horizontally by a specified amount.
     *
     * @param horizontalMove the amount to move horizontally.
     */
    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(getTranslateX() + horizontalMove);
    }

    /**
     * Moves the actor vertically by a specified amount.
     *
     * @param verticalMove the amount to move vertically.
     */
    protected void moveVertically(double verticalMove) {
        this.setTranslateY(getTranslateY() + verticalMove);
    }

    /**
     * Gets the collision bounds of the actor, with a reduced size for better collision detection.
     *
     * @return the collision bounds as a {@link BoundingBox}.
     */
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
