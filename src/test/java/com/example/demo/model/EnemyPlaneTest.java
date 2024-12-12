package com.example.demo.model;

import com.example.demo.projectiles.EnemyProjectile;
import com.example.demo.utilities.Constant;
import com.example.demo.testutils.JavaFXTestUtils;

import javafx.geometry.Bounds;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyPlaneTest {

    private EnemyPlane enemyPlane;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @BeforeEach
    void init() {
        enemyPlane = new EnemyPlane(100, 100); // Initial position at (100, 100)
    }

    @Test
    void testUpdatePosition() {
        double initialX = enemyPlane.getTranslateX();
        enemyPlane.updatePosition();
        assertEquals(initialX + Constant.ENEMY_HORIZONTAL_VELOCITY, enemyPlane.getTranslateX(),
                "EnemyPlane should move horizontally by ENEMY_HORIZONTAL_VELOCITY.");
    }

    @Test
    void testFireProjectile() {
        boolean projectileFired = false;

        for (int i = 0; i < 100; i++) { // Simulate multiple attempts to account for random chance
            ActiveActorDestructible projectile = enemyPlane.fireProjectile();
            if (projectile instanceof EnemyProjectile) {
                projectileFired = true;
                break;
            }
        }

        assertTrue(projectileFired, "EnemyPlane should fire a projectile at least once in 100 attempts.");
    }

    @Test
    void testHasExitedScreen() {
        enemyPlane.setLayoutX(-200); // Simulate off-screen to the left
        assertTrue(enemyPlane.hasExitedScreen(800), "EnemyPlane should be considered off-screen when it exits the left edge.");

        enemyPlane.setLayoutX(900); // Simulate off-screen to the right
        assertTrue(enemyPlane.hasExitedScreen(800), "EnemyPlane should be considered off-screen when it exits the right edge.");
    }

    @Test
    void testGetCollisionBounds() {
        Bounds originalBounds = enemyPlane.getBoundsInParent();
        Bounds collisionBounds = enemyPlane.getCollisionBounds();

        double expectedWidth = originalBounds.getWidth() * (1 - Constant.ENEMY_COLLISION_SHRINK_FACTOR);
        double expectedHeight = originalBounds.getHeight() * (1 - Constant.ENEMY_COLLISION_SHRINK_FACTOR);

        assertEquals(expectedWidth, collisionBounds.getWidth(), 0.1, "Collision bounds width should be shrunk by ENEMY_COLLISION_SHRINK_FACTOR.");
        assertEquals(expectedHeight, collisionBounds.getHeight(), 0.1, "Collision bounds height should be shrunk by ENEMY_COLLISION_SHRINK_FACTOR.");
    }
}
