package com.example.demo.model;

/**
 * Abstract class representing a fighter plane in the game.
 * Fighter planes have health, movement, and projectile capabilities.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

    private int health;

    /**
     * Constructs a {@code FighterPlane} instance with the specified parameters.
     *
     * @param imageName    The name of the image representing the fighter plane.
     * @param imageHeight  The height of the image.
     * @param initialXPos  The initial X-coordinate position of the plane.
     * @param initialYPos  The initial Y-coordinate position of the plane.
     * @param health       The initial health of the plane.
     */
    public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.health = health;
    }

    /**
     * Abstract method to fire a projectile. 
     * Each subclass must implement how the projectile is fired.
     *
     * @return An {@link ActiveActorDestructible} representing the fired projectile.
     */
    public abstract ActiveActorDestructible fireProjectile();

    /**
     * Reduces the health of the plane by 1. 
     * If health reaches zero, the plane is destroyed.
     */
    @Override
    public void takeDamage() {
        health--;
        if (healthAtZero()) {
            this.destroy();
        }
    }

    /**
     * Calculates the X-coordinate for the projectile's initial position based on the plane's position.
     *
     * @param xPositionOffset The offset from the plane's X-coordinate.
     * @return The X-coordinate for the projectile.
     */
    protected double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }

    /**
     * Calculates the Y-coordinate for the projectile's initial position based on the plane's position.
     *
     * @param yPositionOffset The offset from the plane's Y-coordinate.
     * @return The Y-coordinate for the projectile.
     */
    protected double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
    }

    /**
     * Checks if the plane's health has reached zero.
     *
     * @return {@code true} if health is zero, {@code false} otherwise.
     */
    private boolean healthAtZero() {
        return health == 0;
    }

    /**
     * Retrieves the current health of the plane.
     *
     * @return The current health of the plane.
     */
    public int getHealth() {
        return health;
    }
}
