package com.example.demo.projectiles;

import com.example.demo.utilities.Constant;

public class BossProjectile extends Projectile {

	public BossProjectile(double initialYPos) {
        super(Constant.BOSS_PROJECTILE_IMAGE, 
              Constant.BOSS_PROJECTILE_IMAGE_HEIGHT, 
              Constant.BOSS_PROJECTILE_INITIAL_X, 
              initialYPos);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(Constant.BOSS_PROJECTILE_HORIZONTAL_VELOCITY);
    }
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
