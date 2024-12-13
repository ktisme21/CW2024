package com.example.demo.projectiles;

import com.example.demo.utilities.Constant;

/**
 * Represents a projectile fired by the boss in the game.
 * Extends the {@link Projectile} class and defines the behavior specific to boss projectiles.
 */
public class BossProjectile extends Projectile {

    /**
     * Constructs a `BossProjectile` with the specified initial Y position.
     *
     * @param initialYPos the initial vertical position of the projectile.
     */
	public BossProjectile(double initialYPos) {
        super(Constant.BOSS_PROJECTILE_IMAGE, 
              Constant.BOSS_PROJECTILE_IMAGE_HEIGHT, 
              Constant.BOSS_PROJECTILE_INITIAL_X, 
              initialYPos);
    }

    /**
     * Updates the horizontal position of the boss projectile.
     * Moves the projectile based on the defined velocity.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(Constant.BOSS_PROJECTILE_HORIZONTAL_VELOCITY);
    }
	
    /**
     * Updates the state of the boss projectile.
     */
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
