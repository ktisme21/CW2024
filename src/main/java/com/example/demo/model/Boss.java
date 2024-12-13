package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.demo.projectiles.BossProjectile;
import com.example.demo.utilities.Constant;

/**
 * Represents a boss plane in the game.
 * Bosses have unique behaviors such as shields, health, and move patterns.
 */
public class Boss extends FighterPlane {

    private final List<Integer> movePattern;
    private boolean isShielded;
    private int consecutiveMovesInSameDirection;
    private int indexOfCurrentMove;
    private int framesWithShieldActivated;
    private int health;
    private double shieldProbability;
    private boolean shielded;

    /**
     * Constructs a {@code Boss} instance with default settings.
     */
    public Boss() {
        super(Constant.BOSS_PLANE_IMAGE, Constant.BOSS_IMAGE_HEIGHT, Constant.BOSS_INITIAL_X_POSITION,
              Constant.BOSS_INITIAL_Y_POSITION, Constant.BOSS_HEALTH);

        this.health = Constant.BOSS_HEALTH;
        this.shieldProbability = Constant.BOSS_SHIELD_PROBABILITY;

        this.movePattern = new ArrayList<>();
        this.consecutiveMovesInSameDirection = 0;
        this.indexOfCurrentMove = 0;
        this.framesWithShieldActivated = 0;
        this.isShielded = false;

        initializeMovePattern();
    }

    /**
     * Sets the health of the boss.
     *
     * @param health The health value.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Returns the health of the boss.
     *
     * @return The current health.
     */
    public int getHealth() {
        return health;
    }

    public void setShieldProbability(double shieldProbability) {
        this.shieldProbability = shieldProbability;
    }

    public double getShieldProbability() {
        return shieldProbability;
    }

    @Override
    public void updatePosition() {
        double initialTranslateY = getTranslateY();
        moveVertically(getNextMove());
        ensureWithinBounds(initialTranslateY);
    }

    @Override
    public void updateActor() {
        updatePosition();
        manageShieldState();
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        return shouldFireProjectile() ? new BossProjectile(getProjectileInitialPosition()) : null;
    }

    @Override
    public void takeDamage() {
        if (!isShielded()) {
            health--; // Decrease health only when not shielded
            if (health <= 0) {
                destroy();
            }
        }
    }


    // Initialization Helpers
    private void initializeMovePattern() {
        for (int i = 0; i < Constant.BOSS_MOVE_FREQUENCY; i++) {
            movePattern.add(Constant.BOSS_VERTICAL_VELOCITY);
            movePattern.add(-Constant.BOSS_VERTICAL_VELOCITY);
            movePattern.add(Constant.ZERO);
        }
        Collections.shuffle(movePattern);
    }

    private void ensureWithinBounds(double initialTranslateY) {
        double currentPosition = getLayoutY() + getTranslateY();
        if (currentPosition < Constant.BOSS_Y_POSITION_UPPER_BOUND || currentPosition > Constant.BOSS_Y_POSITION_LOWER_BOUND) {
            setTranslateY(initialTranslateY);
        }
    }

    // Shield Management
    private void manageShieldState() {
        if (isShielded) {
            framesWithShieldActivated++;
            if (isShieldExhausted()) {
                deactivateShield();
            }
        } else if (shouldActivateShield()) {
            activateShield();
        }
    }

    /**
     * Activates or deactivates the shield.
     */
    public boolean isShielded() {
        return isShielded;
    }

    private boolean shouldActivateShield() {
        return Math.random() < shieldProbability;
    }

    private boolean isShieldExhausted() {
        return framesWithShieldActivated >= Constant.BOSS_MAX_FRAMES_WITH_SHIELD;
    }

    private void activateShield() {
        isShielded = true;
    }

    private void deactivateShield() {
        isShielded = false;
        framesWithShieldActivated = 0;
    }

    // Movement Logic
    private int getNextMove() {
        int currentMove = movePattern.get(indexOfCurrentMove);
        consecutiveMovesInSameDirection++;

        if (consecutiveMovesInSameDirection >= Constant.BOSS_MAX_FRAMES_WITH_SAME_MOVE) {
            resetMovePattern();
        }

        if (indexOfCurrentMove >= movePattern.size()) {
            indexOfCurrentMove = 0;
        }

        return currentMove;
    }

    private void resetMovePattern() {
        Collections.shuffle(movePattern);
        consecutiveMovesInSameDirection = 0;
        indexOfCurrentMove++;
    }

    // Projectile Logic
    private boolean shouldFireProjectile() {
        return Math.random() < Constant.BOSS_FIRE_RATE;
    }

    private double getProjectileInitialPosition() {
        return getLayoutY() + getTranslateY() + Constant.BOSS_PROJECTILE_Y_OFFSET;
    }
}