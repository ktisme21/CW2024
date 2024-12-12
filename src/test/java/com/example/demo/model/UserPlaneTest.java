package com.example.demo.model;

import com.example.demo.projectiles.UserProjectile;
import com.example.demo.utilities.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserPlaneTest {

    private UserPlane userPlane;

    @BeforeEach
    void setUp() {
        // Create a UserPlane with some initial health
        userPlane = new UserPlane(100);
    }

    @Test
    void testInitialConditions() {
        assertEquals(Constant.USER_PLANE_INITIAL_X, userPlane.getLayoutX(), "Initial X should match constant.");
        assertEquals(Constant.USER_PLANE_INITIAL_Y, userPlane.getLayoutY(), "Initial Y should match constant.");
        assertFalse(userPlane.isDestroyed(), "UserPlane should not be destroyed initially.");
        assertTrue(userPlane.isVisible(), "UserPlane should be visible initially.");
        assertEquals(0, userPlane.getNumberOfKills(), "Initial kill count should be 0.");
    }

    @Test
    void testMovementUpDown() {
        // Move up and verify
        userPlane.moveUp();
        userPlane.updatePosition();
        // After moving up, translateY should be negative if allowed
        // We can't easily assert exact positions without mocking, but we can check if isMovingVertically is true
        assertTrue(userPlane.isMovingVertically(), "UserPlane should be moving vertically after moveUp.");

        // Stop vertical movement
        userPlane.stopVertical();
        userPlane.updatePosition();
        assertFalse(userPlane.isMovingVertically(), "UserPlane should not be moving vertically after stopVertical.");

        // Move down
        userPlane.moveDown();
        assertTrue(userPlane.isMovingVertically(), "UserPlane should be moving vertically after moveDown.");
    }

    @Test
    void testMovementLeftRight() {
        // Move right
        userPlane.moveRight();
        assertTrue(userPlane.isMovingHorizontally(), "UserPlane should be moving horizontally after moveRight.");
        userPlane.stopHorizontal();
        userPlane.updatePosition();
        assertFalse(userPlane.isMovingHorizontally(), "UserPlane should not be moving horizontally after stopHorizontal.");

        // Move left
        userPlane.moveLeft();
        assertTrue(userPlane.isMovingHorizontally(), "UserPlane should be moving horizontally after moveLeft.");
    }

    @Test
    void testSetAndGetDestroyed() {
        userPlane.setDestroyed(true);
        assertTrue(userPlane.isDestroyed(), "UserPlane should be destroyed after setDestroyed(true).");
        userPlane.setDestroyed(false);
        assertFalse(userPlane.isDestroyed(), "UserPlane should not be destroyed after setDestroyed(false).");
    }

    @Test
    void testFireProjectileWhenVisible() {
        // UserPlane is visible by default
        ActiveActorDestructible projectile = userPlane.fireProjectile();
        assertNotNull(projectile, "Should fire a projectile when visible.");
        assertTrue(projectile instanceof UserProjectile, "Fired projectile should be a UserProjectile.");

        UserProjectile userProjectile = (UserProjectile) projectile;
        double expectedX = Constant.USER_PLANE_INITIAL_X + Constant.USER_PROJECTILE_X_POSITION;
        double expectedY = Constant.USER_PLANE_INITIAL_Y + Constant.USER_PROJECTILE_Y_POSITION_OFFSET;
        assertEquals(expectedX, userProjectile.getLayoutX(), 0.001, "Projectile X position should be as expected.");
        assertEquals(expectedY, userProjectile.getLayoutY(), 0.001, "Projectile Y position should be as expected.");
    }

    @Test
    void testFireProjectileWhenInvisible() {
        // Make the UserPlane invisible
        userPlane.setVisible(false);
        ActiveActorDestructible projectile = userPlane.fireProjectile();
        assertNull(projectile, "Should return null when trying to fire projectile while invisible.");
    }

    @Test
    void testIncrementKillCount() {
        assertEquals(0, userPlane.getNumberOfKills(), "Kill count should start at 0.");
        userPlane.incrementKillCount();
        assertEquals(1, userPlane.getNumberOfKills(), "Kill count should increment by 1.");
        userPlane.setNumberOfKills(5);
        assertEquals(5, userPlane.getNumberOfKills(), "Kill count should be updated to 5.");
    }
}
