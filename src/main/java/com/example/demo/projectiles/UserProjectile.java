package com.example.demo.projectiles;

import com.example.demo.utilities.Constant;

public class UserProjectile extends Projectile {
    public UserProjectile(double initialXPos, double initialYPos) {
        super(Constant.USER_PROJECTILE_IMAGE,
              Constant.USER_PROJECTILE_IMAGE_HEIGHT,
              initialXPos,
              initialYPos);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(Constant.USER_PROJECTILE_HORIZONTAL_VELOCITY);

        // Check if the projectile is out of bounds
        if (isOutOfBounds()) {
            this.destroy();
        }
    }

    @Override
    public void updateActor() {
        updatePosition();
        if (isOutOfBounds()) {
            this.destroy(); // Mark the projectile as destroyed
        }
    }

    private boolean isOutOfBounds() {
        double xPosition = getLayoutX() + getTranslateX();
        return xPosition > Constant.SCREEN_WIDTH || xPosition < 0;
    }
}
