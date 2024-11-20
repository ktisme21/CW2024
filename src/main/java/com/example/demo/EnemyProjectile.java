package com.example.demo;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -10;
	private static final double SHRINK_FACTOR = 0.5;

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	// public Bounds getCollisionBounds() {
	// 	// Create a smaller bounding rectangle for collision detection
	// 	Bounds originalBounds = super.getBoundsInParent();
	// 	double shrinkWidth = originalBounds.getWidth() * SHRINK_FACTOR;
	// 	double shrinkHeight = originalBounds.getHeight() * SHRINK_FACTOR;

	// 	// Center the smaller bounds within the original bounds
	// 	return new Rectangle(
	// 		originalBounds.getMinX() + (originalBounds.getWidth() - shrinkWidth) / 2,
	// 		originalBounds.getMinY() + (originalBounds.getHeight() - shrinkHeight) / 2,
	// 		shrinkWidth,
	// 		shrinkHeight
	// 	).getBoundsInParent();
	// }


}
