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
    void testGetCollisionBounds() {
        Bounds originalBounds = enemyPlane.getBoundsInParent();
        Bounds collisionBounds = enemyPlane.getCollisionBounds();

        double expectedWidth = originalBounds.getWidth() * (1 - Constant.ENEMY_COLLISION_SHRINK_FACTOR);
        double expectedHeight = originalBounds.getHeight() * (1 - Constant.ENEMY_COLLISION_SHRINK_FACTOR);

        assertEquals(expectedWidth, collisionBounds.getWidth(), 0.1, "Collision bounds width should be shrunk by ENEMY_COLLISION_SHRINK_FACTOR.");
        assertEquals(expectedHeight, collisionBounds.getHeight(), 0.1, "Collision bounds height should be shrunk by ENEMY_COLLISION_SHRINK_FACTOR.");
    }
}
