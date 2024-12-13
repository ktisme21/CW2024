package com.example.demo.model;

import com.example.demo.projectiles.EnemyProjectile;
import com.example.demo.utilities.Constant;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Represents an enemy plane in the game.
 * Enemy planes move horizontally and can fire projectiles.
 */
public class EnemyPlane extends FighterPlane {

	/**
     * Constructs an {@code EnemyPlane} at the specified position.
     *
     * @param initialXPos Initial X position.
     * @param initialYPos Initial Y position.
     */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(Constant.ENEMY_PLANE_IMAGE, Constant.ENEMY_PLANE_IMAGE_HEIGHT, initialXPos, initialYPos, Constant.ENEMY_INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(Constant.ENEMY_HORIZONTAL_VELOCITY);
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < Constant.ENEMY_FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(Constant.PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(Constant.PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	public boolean hasExitedScreen(double screenWidth) {
		double xPosition = getLayoutX() + getTranslateX();
		return xPosition + getBoundsInParent().getWidth() < 0 || xPosition > screenWidth;
	}


	@Override
	public Bounds getCollisionBounds() {
		// Shrink collision bounds by 60% for EnemyPlane
		double width = getBoundsInParent().getWidth() * (1 - Constant.ENEMY_COLLISION_SHRINK_FACTOR);
		double height = getBoundsInParent().getHeight() * (1 - Constant.ENEMY_COLLISION_SHRINK_FACTOR);
		double x = getBoundsInParent().getMinX() + (getBoundsInParent().getWidth() - width) / 2;
		double y = getBoundsInParent().getMinY() + (getBoundsInParent().getHeight() - height) / 2;

		return new BoundingBox(x, y, width, height);
	}

}
