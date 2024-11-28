package com.example.demo.model;

import com.example.demo.manager.MusicPlayer;
import com.example.demo.projectiles.UserProjectile;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double X_LEFT_BOUND = -30; // Allow userplane move horizontally
	private static final double X_RIGHT_BOUND = 1110;
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 150;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	
	private int velocityMultiplierX;
	private int velocityMultiplierY;
	private int numberOfKills;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplierX = 0;
		velocityMultiplierY = 0;
	}
	
	@Override
	public void updatePosition() {
		// Update vertical movement
		if (isMovingVertically()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplierY);
			double newYPosition = getLayoutY() + getTranslateY();
			if (newYPosition < Y_UPPER_BOUND || newYPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}

		// Update horizontal movement
		if (isMovingHorizontally()) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(HORIZONTAL_VELOCITY * velocityMultiplierX);
			double newXPosition = getLayoutX() + getTranslateX();
			if (newXPosition < X_LEFT_BOUND || newXPosition > X_RIGHT_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}

	@Override
	public void updateActor() {
		updatePosition();
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		// if (!isVisible()) {
		// 	System.out.println("UserPlane is invisible. Projectiles cannot be fired.");
		// 	return null; // Prevent firing
		// }
		MusicPlayer.playShootingSound();
		double projectileXPosition = getLayoutX() + getTranslateX() + PROJECTILE_X_POSITION;
		double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
		return new UserProjectile(projectileXPosition, projectileYPosition);
	}

	// Vertical and horizontal movement
	public void moveUp() {
		velocityMultiplierY = -1;
	}

	public void moveDown() {
		velocityMultiplierY = 1;
	}

	public void moveLeft() {
		velocityMultiplierX = -1;
	}

	public void moveRight() {
		velocityMultiplierX = 1;
	}

	// Stop vertical and horizontal movement
	public void stopVertical() {
		velocityMultiplierY = 0;
	}

	public void stopHorizontal() {
		velocityMultiplierX = 0;
	}

	private boolean isMovingVertically() {
		return velocityMultiplierY != 0;
	}

	private boolean isMovingHorizontally() {
		return velocityMultiplierX != 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

}
