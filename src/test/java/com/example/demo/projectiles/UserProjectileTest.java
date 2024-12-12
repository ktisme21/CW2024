package com.example.demo.projectiles;

import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.Constant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserProjectileTest {

    private static final double INITIAL_X_POS = 50.0;
    private static final double INITIAL_Y_POS = 100.0;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @Test
    void testInitialization() {
        UserProjectile projectile = new UserProjectile(INITIAL_X_POS, INITIAL_Y_POS);

        // Extract the file name for comparison
        String actualImageName = projectile.getImage().getUrl().substring(projectile.getImage().getUrl().lastIndexOf('/') + 1);
        assertEquals(Constant.USER_PROJECTILE_IMAGE, actualImageName, "Projectile image should match.");
        assertEquals(Constant.USER_PROJECTILE_IMAGE_HEIGHT, projectile.getFitHeight(), "Projectile height should match.");
        assertEquals(INITIAL_X_POS, projectile.getLayoutX(), "Initial X position should match.");
        assertEquals(INITIAL_Y_POS, projectile.getLayoutY(), "Initial Y position should match.");
    }

    @Test
    void testUpdatePositionWithinBounds() {
        UserProjectile projectile = new UserProjectile(INITIAL_X_POS, INITIAL_Y_POS);
        projectile.updatePosition();

        double expectedXPosition = INITIAL_X_POS + Constant.USER_PROJECTILE_HORIZONTAL_VELOCITY;
        assertEquals(expectedXPosition, projectile.getLayoutX() + projectile.getTranslateX(), "Projectile X position should update correctly.");
        assertFalse(projectile.isDestroyed(), "Projectile should not be destroyed within bounds.");
    }

    @Test
    void testUpdatePositionOutOfBounds() {
        UserProjectile projectile = new UserProjectile(Constant.SCREEN_WIDTH + 10, INITIAL_Y_POS); // Start out of bounds
        projectile.updatePosition();

        assertTrue(projectile.isDestroyed(), "Projectile should be destroyed if out of bounds.");
    }

    @Test
    void testUpdateActorOutOfBounds() {
        UserProjectile projectile = new UserProjectile(Constant.SCREEN_WIDTH + 10, INITIAL_Y_POS); // Start out of bounds
        projectile.updateActor();

        assertTrue(projectile.isDestroyed(), "Projectile should be destroyed after calling updateActor if out of bounds.");
    }

    @Test
    void testUpdateActorWithinBounds() {
        UserProjectile projectile = new UserProjectile(INITIAL_X_POS, INITIAL_Y_POS);
        projectile.updateActor();

        double expectedXPosition = INITIAL_X_POS + Constant.USER_PROJECTILE_HORIZONTAL_VELOCITY;
        assertEquals(expectedXPosition, projectile.getLayoutX() + projectile.getTranslateX(), "Projectile X position should update correctly.");
        assertFalse(projectile.isDestroyed(), "Projectile should not be destroyed within bounds.");
    }
}
