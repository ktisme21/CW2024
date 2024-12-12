package com.example.demo.projectiles;

import com.example.demo.utilities.Constant;
import com.example.demo.testutils.JavaFXTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the {@link BossProjectile} class.
 */
public class BossProjectileTest {

    private static final double INITIAL_Y_POS = 100.0;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @Test
    void testInitialization() {
        BossProjectile bossProjectile = new BossProjectile(INITIAL_Y_POS);
        String expectedImageName = Constant.BOSS_PROJECTILE_IMAGE.substring(Constant.BOSS_PROJECTILE_IMAGE.lastIndexOf('/') + 1);
        String actualImageName = bossProjectile.getImage().getUrl().substring(bossProjectile.getImage().getUrl().lastIndexOf('/') + 1);
        assertEquals(expectedImageName, actualImageName, "Projectile image should match.");
        assertEquals(Constant.BOSS_PROJECTILE_IMAGE_HEIGHT, bossProjectile.getFitHeight(), "Projectile height should match.");
        assertEquals(Constant.BOSS_PROJECTILE_INITIAL_X, bossProjectile.getLayoutX(), "Initial X position should match.");
        assertEquals(INITIAL_Y_POS, bossProjectile.getLayoutY(), "Initial Y position should match.");
    }

    @Test
    void testUpdatePosition() {
        BossProjectile bossProjectile = new BossProjectile(INITIAL_Y_POS);
        double initialX = bossProjectile.getLayoutX() + bossProjectile.getTranslateX();

        bossProjectile.updatePosition();

        double newX = bossProjectile.getLayoutX() + bossProjectile.getTranslateX();
        assertEquals(initialX + Constant.BOSS_PROJECTILE_HORIZONTAL_VELOCITY, newX, "Projectile should move horizontally.");
    }

    @Test
    void testUpdateActor() {
        BossProjectile bossProjectile = new BossProjectile(INITIAL_Y_POS);
        double initialX = bossProjectile.getLayoutX() + bossProjectile.getTranslateX();

        bossProjectile.updateActor();

        double newX = bossProjectile.getLayoutX() + bossProjectile.getTranslateX();
        assertEquals(initialX + Constant.BOSS_PROJECTILE_HORIZONTAL_VELOCITY, newX, "updateActor should update the projectile's position.");
    }
}
