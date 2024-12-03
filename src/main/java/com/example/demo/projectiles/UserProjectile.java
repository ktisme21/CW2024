package com.example.demo.projectiles;

import com.example.demo.utilities.Constant;

/**
 * Represents a projectile fired by the user in the game.
 * Extends the {@link Projectile} class and defines behavior specific to user projectiles.
 */
public class UserProjectile extends Projectile {
    /**
     * Constructs a `UserProjectile` with the specified initial position.
     *
     * @param initialXPos the initial horizontal position of the projectile.
     * @param initialYPos the initial vertical position of the projectile.
     */
    public UserProjectile(double initialXPos, double initialYPos) {
        super(Constant.USER_PROJECTILE_IMAGE,
              Constant.USER_PROJECTILE_IMAGE_HEIGHT,
              initialXPos,
              initialYPos);
    }

    /**
     * Updates the position of the user projectile.
     * Moves the projectile horizontally based on the defined velocity and destroys it
     * if it moves out of bounds.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(Constant.USER_PROJECTILE_HORIZONTAL_VELOCITY);

        // Check if the projectile is out of bounds
        if (isOutOfBounds()) {
            this.destroy();
        }
    }

    /**
     * Updates the state of the user projectile, including position and destruction logic.
     */
    @Override
    public void updateActor() {
        updatePosition();
        if (isOutOfBounds()) {
            this.destroy(); // Mark the projectile as destroyed
        }
    }

    /**
     * Checks if the projectile is out of the game bounds.
     *
     * @return true if the projectile is out of bounds, false otherwise.
     */
    private boolean isOutOfBounds() {
        double xPosition = getLayoutX() + getTranslateX();
        return xPosition > Constant.SCREEN_WIDTH || xPosition < 0;
    }
}
