package com.example.demo.model;

import com.example.demo.manager.MusicPlayer;
import com.example.demo.projectiles.UserProjectile;
import com.example.demo.utilities.Constant;

/**
 * Represents the user's plane in the game.
 * The user plane can move, fire projectiles, and take damage.
 */
public class UserPlane extends FighterPlane {

	private int velocityMultiplierX;
	private int velocityMultiplierY;
	private int numberOfKills;
	private boolean destroyed = false;

	public UserPlane(int initialHealth) {
        super(
            Constant.USER_PLANE_IMAGE_NAME,
            Constant.USER_PLANE_IMAGE_HEIGHT,
            Constant.USER_PLANE_INITIAL_X,
            Constant.USER_PLANE_INITIAL_Y,
            initialHealth
        );
        velocityMultiplierX = 0;
        velocityMultiplierY = 0;
    }

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	@Override
    public void updatePosition() {
        // Update vertical movement
        if (isMovingVertically()) {
            double initialTranslateY = getTranslateY();
            this.moveVertically(Constant.USER_PLANE_VERTICAL_VELOCITY * velocityMultiplierY);
            double newYPosition = getLayoutY() + getTranslateY();
            if (newYPosition < Constant.USER_PLANE_Y_UPPER_BOUND || newYPosition > Constant.USER_PLANE_Y_LOWER_BOUND) {
                this.setTranslateY(initialTranslateY);
            }
        }

        // Update horizontal movement
        if (isMovingHorizontally()) {
            double initialTranslateX = getTranslateX();
            this.moveHorizontally(Constant.USER_PLANE_HORIZONTAL_VELOCITY * velocityMultiplierX);
            double newXPosition = getLayoutX() + getTranslateX();
            if (newXPosition < Constant.USER_PLANE_X_LEFT_BOUND || newXPosition > Constant.USER_PLANE_X_RIGHT_BOUND) {
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
		if (!this.isVisible()) {
			return null; // Return null to prevent firing when invisible
		}
		MusicPlayer.playShootingSound();
		double projectileXPosition = getLayoutX() + getTranslateX() + Constant.USER_PROJECTILE_X_POSITION;
		double projectileYPosition = getLayoutY() + getTranslateY() + Constant.USER_PROJECTILE_Y_POSITION_OFFSET;
		return new UserProjectile(projectileXPosition, projectileYPosition);
	}

	public void setNumberOfKills(int numberOfKills) {
		this.numberOfKills = numberOfKills;
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

	// Update access modifiers for isMovingVertically and isMovingHorizontally
	protected boolean isMovingVertically() {
		return velocityMultiplierY != 0;
	}

	protected boolean isMovingHorizontally() {
		return velocityMultiplierX != 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

}
