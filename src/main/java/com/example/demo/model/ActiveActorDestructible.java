package com.example.demo.model;

import com.example.demo.mechanics.Destructible;

/**
 * Abstract class representing an active actor in the game that can be destroyed.
 * Combines functionality from {@link ActiveActor} and {@link Destructible}.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	/**
     * Initializes a destructible active actor with its image and position.
     *
     * @param imageName   the name of the image file for the actor.
     * @param imageHeight the height to scale the actor's image.
     * @param initialXPos the initial x-coordinate of the actor.
     * @param initialYPos the initial y-coordinate of the actor.
     */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
     * Updates the position of the actor. Subclasses must provide implementation.
     */
	@Override
	public abstract void updatePosition();

	/**
     * Updates the state of the actor. Subclasses must provide implementation.
     */
	public abstract void updateActor();

	/**
     * Handles damage taken by the actor. Subclasses must provide implementation.
     */
	@Override
	public abstract void takeDamage();

	/**
     * Marks the actor as destroyed.
     */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
     * Sets the destroyed state of the actor.
     *
     * @param isDestroyed true if the actor is destroyed, false otherwise.
     */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
     * Checks if the actor is destroyed.
     *
     * @return true if the actor is destroyed, false otherwise.
     */
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
}
