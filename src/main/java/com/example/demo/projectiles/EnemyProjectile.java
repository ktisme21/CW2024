package com.example.demo.projectiles;

import com.example.demo.utilities.Constant;

/**
 * Represents a projectile fired by enemy planes in the game.
 * Extends the {@link Projectile} class and defines the behavior specific to enemy projectiles.
 */
public class EnemyProjectile extends Projectile {

	/**
     * Constructs an `EnemyProjectile` with the specified initial position.
     *
     * @param initialXPos the initial horizontal position of the projectile.
     * @param initialYPos the initial vertical position of the projectile.
     */
    public EnemyProjectile(double initialXPos, double initialYPos) {
        super(Constant.ENEMY_PROJECTILE_IMAGE, 
              Constant.ENEMY_PROJECTILE_IMAGE_HEIGHT, 
              initialXPos, 
              initialYPos);
    }

	/**
     * Updates the horizontal position of the enemy projectile.
     * Moves the projectile based on the defined velocity.
     */
	@Override
	public void updatePosition() {
		moveHorizontally(Constant.ENEMY_PROJECTILE_HORIZONTAL_VELOCITY);
	}

	/**
     * Updates the state of the enemy projectile.
     */
	@Override
	public void updateActor() {
		updatePosition();
	}

}
