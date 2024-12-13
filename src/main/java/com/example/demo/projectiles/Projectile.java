package com.example.demo.projectiles;

import com.example.demo.model.ActiveActorDestructible;

/**
 * Abstract class representing a generic projectile in the game.
 * Extends {@link ActiveActorDestructible} and provides basic functionality for projectiles.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
     * Constructs a projectile with the specified image, dimensions, and initial position.
     *
     * @param imageName   the name of the image file for the projectile.
     * @param imageHeight the height to scale the projectile's image.
     * @param initialXPos the initial horizontal position of the projectile.
     * @param initialYPos the initial vertical position of the projectile.
     */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
     * Handles damage taken by the projectile.
     * The default behavior is to mark the projectile as destroyed.
     */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
     * Updates the position of the projectile.
     * Must be implemented by subclasses to define specific movement behavior.
     */
	@Override
	public abstract void updatePosition();

}
