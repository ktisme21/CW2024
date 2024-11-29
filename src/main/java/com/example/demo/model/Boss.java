package com.example.demo.model;

import java.util.*;

import com.example.demo.projectiles.BossProjectile;
import com.example.demo.utilities.Constant;

public class Boss extends FighterPlane {

	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	public Boss() {
		super(Constant.BOSS_PLANE_IMAGE, Constant.BOSS_IMAGE_HEIGHT, Constant.BOSS_INITIAL_X_POSITION,
              Constant.BOSS_INITIAL_Y_POSITION, Constant.BOSS_HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Constant.BOSS_Y_POSITION_UPPER_BOUND || currentPosition > Constant.BOSS_Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}
	
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	private void initializeMovePattern() {
        for (int i = 0; i < Constant.BOSS_MOVE_FREQUENCY; i++) {
            movePattern.add(Constant.BOSS_VERTICAL_VELOCITY);
            movePattern.add(-Constant.BOSS_VERTICAL_VELOCITY);
            movePattern.add(Constant.ZERO);
        }
        Collections.shuffle(movePattern);
    }

	private void updateShield() {
	    if (isShielded) {
            framesWithShieldActivated++;
            if (shieldExhausted()) {
                deactivateShield();
            }
        } else if (shieldShouldBeActivated()) {
            activateShield();
        }
    }

	private int getNextMove() {
        int currentMove = movePattern.get(indexOfCurrentMove);
        consecutiveMovesInSameDirection++;
        if (consecutiveMovesInSameDirection == Constant.BOSS_MAX_FRAMES_WITH_SAME_MOVE) {
            Collections.shuffle(movePattern);
            consecutiveMovesInSameDirection = 0;
            indexOfCurrentMove++;
        }
        if (indexOfCurrentMove == movePattern.size()) {
            indexOfCurrentMove = 0;
        }
        return currentMove;
    }

	private boolean bossFiresInCurrentFrame() {
        return Math.random() < Constant.BOSS_FIRE_RATE;
    }

    private double getProjectileInitialPosition() {
        return getLayoutY() + getTranslateY() + Constant.BOSS_PROJECTILE_Y_OFFSET;
    }

	public boolean isShielded() {
        return isShielded;
    }

	private boolean shieldShouldBeActivated() {
        return Math.random() < Constant.BOSS_SHIELD_PROBABILITY;
    }

    private boolean shieldExhausted() {
        return framesWithShieldActivated == Constant.BOSS_MAX_FRAMES_WITH_SHIELD;
    }

	private void activateShield() {
		isShielded = true;
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

}
