package com.example.demo.model;

import com.example.demo.manager.MusicPlayer;
import com.example.demo.projectiles.UserProjectile;
import com.example.demo.utilities.Constant;

/**
 * Represents the user's plane in the game.
 * The user plane can move, fire projectiles, take damage, and track kills.
 */
public class UserPlane extends FighterPlane {

    private int velocityMultiplierX;
    private int velocityMultiplierY;
    private int numberOfKills;
    private boolean destroyed = false;

    /**
     * Constructs a {@code UserPlane} with the specified initial health.
     *
     * @param initialHealth The initial health of the user's plane.
     */
    public UserPlane(int initialHealth) {
        super(
            Constant.USER_PLANE_IMAGE_NAME,
            Constant.USER_PLANE_IMAGE_HEIGHT,
            Constant.USER_PLANE_INITIAL_X,
            Constant.USER_PLANE_INITIAL_Y,
            initialHealth
        );
        velocityMultiplierX = 0;
        velocityMultiplierY = 0;
    }

    /**
     * Sets the destruction state of the user's plane.
     *
     * @param destroyed {@code true} if the plane is destroyed, {@code false} otherwise.
     */
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    /**
     * Checks if the user's plane is destroyed.
     *
     * @return {@code true} if the plane is destroyed, {@code false} otherwise.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Updates the position of the user's plane based on its velocity multipliers.
     * Ensures the plane stays within the defined boundaries.
     */
    @Override
    public void updatePosition() {
        // Update vertical movement
        if (isMovingVertically()) {
            double initialTranslateY = getTranslateY();
            this.moveVertically(Constant.USER_PLANE_VERTICAL_VELOCITY * velocityMultiplierY);
            double newYPosition = getLayoutY() + getTranslateY();
            if (newYPosition < Constant.USER_PLANE_Y_UPPER_BOUND || newYPosition > Constant.USER_PLANE_Y_LOWER_BOUND) {
                this.setTranslateY(initialTranslateY);
            }
        }

        // Update horizontal movement
        if (isMovingHorizontally()) {
            double initialTranslateX = getTranslateX();
            this.moveHorizontally(Constant.USER_PLANE_HORIZONTAL_VELOCITY * velocityMultiplierX);
            double newXPosition = getLayoutX() + getTranslateX();
            if (newXPosition < Constant.USER_PLANE_X_LEFT_BOUND || newXPosition > Constant.USER_PLANE_X_RIGHT_BOUND) {
                this.setTranslateX(initialTranslateX);
            }
        }
    }

    /**
     * Updates the state of the user's plane.
     * Calls the method to update the position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Fires a projectile from the user's plane.
     * Plays a shooting sound and creates a new projectile at the correct position.
     *
     * @return An {@link ActiveActorDestructible} representing the fired projectile, or {@code null} if the plane is invisible.
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (!this.isVisible()) {
            return null; // Return null to prevent firing when invisible
        }
        MusicPlayer.playShootingSound();
        double projectileXPosition = getLayoutX() + getTranslateX() + Constant.USER_PROJECTILE_X_POSITION;
        double projectileYPosition = getLayoutY() + getTranslateY() + Constant.USER_PROJECTILE_Y_POSITION_OFFSET;
        return new UserProjectile(projectileXPosition, projectileYPosition);
    }

    /**
     * Sets the number of kills made by the user.
     *
     * @param numberOfKills The number of kills to set.
     */
    public void setNumberOfKills(int numberOfKills) {
        this.numberOfKills = numberOfKills;
    }

    /**
     * Increments the kill count for the user's plane.
     */
    public void incrementKillCount() {
        numberOfKills++;
    }

    /**
     * Retrieves the number of kills made by the user.
     *
     * @return The current kill count.
     */
    public int getNumberOfKills() {
        return numberOfKills;
    }

    // Movement methods

    /**
     * Initiates upward movement of the user's plane.
     */
    public void moveUp() {
        velocityMultiplierY = -1;
    }

    /**
     * Initiates downward movement of the user's plane.
     */
    public void moveDown() {
        velocityMultiplierY = 1;
    }

    /**
     * Initiates leftward movement of the user's plane.
     */
    public void moveLeft() {
        velocityMultiplierX = -1;
    }

    /**
     * Initiates rightward movement of the user's plane.
     */
    public void moveRight() {
        velocityMultiplierX = 1;
    }

    /**
     * Stops vertical movement of the user's plane.
     */
    public void stopVertical() {
        velocityMultiplierY = 0;
    }

    /**
     * Stops horizontal movement of the user's plane.
     */
    public void stopHorizontal() {
        velocityMultiplierX = 0;
    }

    // Helper methods

    /**
     * Checks if the user's plane is currently moving vertically.
     *
     * @return {@code true} if moving vertically, {@code false} otherwise.
     */
    protected boolean isMovingVertically() {
        return velocityMultiplierY != 0;
    }

    /**
     * Checks if the user's plane is currently moving horizontally.
     *
     * @return {@code true} if moving horizontally, {@code false} otherwise.
     */
    protected boolean isMovingHorizontally() {
        return velocityMultiplierX != 0;
    }
}
