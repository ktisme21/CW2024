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
    }

	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
