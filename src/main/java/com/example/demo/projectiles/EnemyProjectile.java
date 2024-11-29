package com.example.demo.projectiles;

import com.example.demo.utilities.Constant;

public class EnemyProjectile extends Projectile {

    public EnemyProjectile(double initialXPos, double initialYPos) {
        super(Constant.ENEMY_PROJECTILE_IMAGE, 
              Constant.ENEMY_PROJECTILE_IMAGE_HEIGHT, 
              initialXPos, 
              initialYPos);
    }

	@Override
	public void updatePosition() {
		moveHorizontally(Constant.ENEMY_PROJECTILE_HORIZONTAL_VELOCITY);
	}

	@Override
	public void updateActor() {
		updatePosition();
	}


}
