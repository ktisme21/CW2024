package com.example.demo.mechanics;

/**
 * Interface representing an entity that can take damage and be destroyed.
 */
public interface Destructible {

    /**
     * Method to be called when the object takes damage.
     */
    void takeDamage();

    /**
     * Method to destroy the object, marking it as no longer active.
     */
    void destroy();
}
