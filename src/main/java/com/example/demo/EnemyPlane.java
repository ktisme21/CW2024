package com.example.demo;

import javafx.geometry.BoundingBox;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 150;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;
	private static final double SHRINK_FACTOR = 0.5;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateRedContainer();
	}

	public boolean hasExitedScreen(double screenWidth){
		// Check if the plane has flown past the left edge of the screen
        return getLayoutX() + getTranslateX() + getBoundsInParent().getWidth() < 0 || 
               getLayoutX() > screenWidth; // Check if it exited on the right
	}

	@Override
	public javafx.geometry.Bounds getCollisionBounds() {
		// Shrink collision bounds by 60% for EnemyPlane
		double width = getBoundsInParent().getWidth() * (1 - SHRINK_FACTOR);
		double height = getBoundsInParent().getHeight() * (1 - SHRINK_FACTOR);
		double x = getBoundsInParent().getMinX() + (getBoundsInParent().getWidth() - width) / 2;
		double y = getBoundsInParent().getMinY() + (getBoundsInParent().getHeight() - height) / 2;

		return new BoundingBox(x, y, width, height);
	}



}
