package com.example.demo.model;

import com.example.demo.testutils.JavaFXTestUtils;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ActiveActorDestructibleTest {

    private static final String TEST_IMAGE = "test_image.png";
    private static final int TEST_HEIGHT = 100;
    private static final double INITIAL_X = 50.0;
    private static final double INITIAL_Y = 75.0;

    private TestActiveActorDestructible testActor;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @BeforeEach
    void init() {
        testActor = new TestActiveActorDestructible(TEST_IMAGE, TEST_HEIGHT, INITIAL_X, INITIAL_Y);
    }

    @Test
    void testInitialization() {
        assertFalse(testActor.isDestroyed(), "The actor should not be destroyed initially.");
        assertEquals(INITIAL_X, testActor.getLayoutX(), "The actor's X position should match the initial X.");
        assertEquals(INITIAL_Y, testActor.getLayoutY(), "The actor's Y position should match the initial Y.");
    }

    @Test
    void testDestroy() {
        assertFalse(testActor.isDestroyed(), "The actor should not be destroyed initially.");
        testActor.destroy();
        assertTrue(testActor.isDestroyed(), "The actor should be marked as destroyed after calling destroy.");
    }

    @Test
    void testSetDestroyed() {
        testActor.setDestroyed(true);
        assertTrue(testActor.isDestroyed(), "The actor should be marked as destroyed when set explicitly.");
        testActor.setDestroyed(false);
        assertFalse(testActor.isDestroyed(), "The actor should not be marked as destroyed when reset.");
    }

    /**
     * A concrete subclass of ActiveActorDestructible to enable testing.
     */
    private static class TestActiveActorDestructible extends ActiveActorDestructible {

        public TestActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
            super(imageName, imageHeight, initialXPos, initialYPos);
        }

        @Override
        public void updatePosition() {
            moveHorizontally(10); // Simulate moving 10 units to the right
        }

        @Override
        public void updateActor() {
            updatePosition(); // Call updatePosition for demonstration
        }

        @Override
        public void takeDamage() {
            destroy(); // Simulate destruction upon taking damage
        }

    }
}
