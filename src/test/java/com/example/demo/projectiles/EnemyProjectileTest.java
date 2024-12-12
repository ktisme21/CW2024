package com.example.demo.projectiles;

import com.example.demo.utilities.Constant;
import com.example.demo.testutils.JavaFXTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyProjectileTest {

    private static final double INITIAL_X_POS = 100.0;
    private static final double INITIAL_Y_POS = 200.0;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @Test
    void testInitialization() {
        EnemyProjectile enemyProjectile = new EnemyProjectile(INITIAL_X_POS, INITIAL_Y_POS);

        // Extract file name for comparison
        String expectedImageName = Constant.ENEMY_PROJECTILE_IMAGE.substring(Constant.ENEMY_PROJECTILE_IMAGE.lastIndexOf('/') + 1);
        String actualImageName = enemyProjectile.getImage().getUrl().substring(enemyProjectile.getImage().getUrl().lastIndexOf('/') + 1);
        assertEquals(expectedImageName, actualImageName, "Projectile image should match.");

        assertEquals(Constant.ENEMY_PROJECTILE_IMAGE_HEIGHT, enemyProjectile.getFitHeight(), "Projectile height should match.");
        assertEquals(INITIAL_X_POS, enemyProjectile.getLayoutX(), "Initial X position should match.");
        assertEquals(INITIAL_Y_POS, enemyProjectile.getLayoutY(), "Initial Y position should match.");
    }

    @Test
    void testUpdatePosition() {
        EnemyProjectile enemyProjectile = new EnemyProjectile(INITIAL_X_POS, INITIAL_Y_POS);
        enemyProjectile.updatePosition();

        double expectedXPosition = INITIAL_X_POS + Constant.ENEMY_PROJECTILE_HORIZONTAL_VELOCITY;
        assertEquals(expectedXPosition, enemyProjectile.getLayoutX() + enemyProjectile.getTranslateX(), "Projectile X position should update correctly.");
    }

    @Test
    void testUpdateActor() {
        EnemyProjectile enemyProjectile = new EnemyProjectile(INITIAL_X_POS, INITIAL_Y_POS);
        enemyProjectile.updateActor();

        double expectedXPosition = INITIAL_X_POS + Constant.ENEMY_PROJECTILE_HORIZONTAL_VELOCITY;
        assertEquals(expectedXPosition, enemyProjectile.getLayoutX() + enemyProjectile.getTranslateX(), "Projectile X position should update correctly via updateActor.");
    }
}
