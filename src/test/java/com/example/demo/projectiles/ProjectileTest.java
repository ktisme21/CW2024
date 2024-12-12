package com.example.demo.projectiles;

import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.testutils.JavaFXTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileTest {

    private static final String IMAGE_NAME = "test_projectile.png";
    private static final int IMAGE_HEIGHT = 10;
    private static final double INITIAL_X_POS = 100.0;
    private static final double INITIAL_Y_POS = 200.0;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    /**
     * Mock subclass for testing the abstract `Projectile` class.
     */
    static class MockProjectile extends Projectile {
        public MockProjectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
            super(imageName, imageHeight, initialXPos, initialYPos);
        }

        @Override
        public void updatePosition() {
            moveHorizontally(5.0); // Example horizontal movement for testing
        }

        @Override
        public void updateActor() {
            updatePosition(); // Updates the position
        }
    }

    @Test
    void testInitialization() {
        MockProjectile projectile = new MockProjectile(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POS, INITIAL_Y_POS);

        // Extract the file name for comparison
        String actualImageName = projectile.getImage().getUrl().substring(projectile.getImage().getUrl().lastIndexOf('/') + 1);
        assertEquals(IMAGE_NAME, actualImageName, "Projectile image should match.");
        assertEquals(IMAGE_HEIGHT, projectile.getFitHeight(), "Projectile height should match.");
        assertEquals(INITIAL_X_POS, projectile.getLayoutX(), "Initial X position should match.");
        assertEquals(INITIAL_Y_POS, projectile.getLayoutY(), "Initial Y position should match.");
    }

    @Test
    void testUpdatePosition() {
        MockProjectile projectile = new MockProjectile(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POS, INITIAL_Y_POS);
        projectile.updatePosition();

        double expectedXPosition = INITIAL_X_POS + 5.0; // Based on the mock implementation
        assertEquals(expectedXPosition, projectile.getLayoutX() + projectile.getTranslateX(), "Projectile X position should update correctly.");
    }

    @Test
    void testTakeDamage() {
        MockProjectile projectile = new MockProjectile(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POS, INITIAL_Y_POS);
        assertFalse(projectile.isDestroyed(), "Projectile should not be destroyed initially.");
        projectile.takeDamage();
        assertTrue(projectile.isDestroyed(), "Projectile should be destroyed after taking damage.");
    }
}
