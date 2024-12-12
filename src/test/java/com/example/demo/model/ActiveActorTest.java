package com.example.demo.model;

import com.example.demo.testutils.JavaFXTestUtils;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the ActiveActor class.
 */
class ActiveActorTest {

    /**
     * Dummy implementation of ActiveActor for testing.
     */
    private static class TestActiveActor extends ActiveActor {

        public TestActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
            super(imageName, imageHeight, initialXPos, initialYPos);
        }

        @Override
        public void updatePosition() {
            // For testing purposes, just move diagonally.
            moveHorizontally(5);
            moveVertically(5);
        }
    }

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @Test
    void testInitializeImage() {
        ActiveActor actor = new TestActiveActor("testImage.jpg", 100, 50, 50);

        assertNotNull(actor.getImage(), "Image should be initialized.");
        assertEquals(50, actor.getLayoutX(), "Initial X position should be set correctly.");
        assertEquals(50, actor.getLayoutY(), "Initial Y position should be set correctly.");
        assertEquals(100, actor.getFitHeight(), "Image height should be set correctly.");
    }

    @Test
    void testAddToParent() {
        ActiveActor actor = new TestActiveActor("testImage.jpg", 100, 50, 50);
        Group parent = new Group();

        actor.addToParent(parent);
        assertTrue(parent.getChildren().contains(actor), "Actor should be added to the parent group.");
    }

    @Test
    void testMoveHorizontallyAndVertically() {
        ActiveActor actor = new TestActiveActor("testImage.jpg", 100, 50, 50);

        actor.moveHorizontally(10);
        actor.moveVertically(20);

        assertEquals(10, actor.getTranslateX(), "Horizontal translation should be updated correctly.");
        assertEquals(20, actor.getTranslateY(), "Vertical translation should be updated correctly.");
    }

    @Test
    void testUpdatePosition() {
        ActiveActor actor = new TestActiveActor("testImage.jpg", 100, 50, 50);

        actor.updatePosition();

        assertEquals(5, actor.getTranslateX(), "Horizontal position should be updated by updatePosition.");
        assertEquals(5, actor.getTranslateY(), "Vertical position should be updated by updatePosition.");
    }

    @Test
    void testGetCollisionBounds() {
        ActiveActor actor = new TestActiveActor("testImage.jpg", 100, 50, 50);

        Bounds collisionBounds = actor.getCollisionBounds();
        Bounds originalBounds = actor.getBoundsInParent();

        double expectedWidth = originalBounds.getWidth() * 0.5;
        double expectedHeight = originalBounds.getHeight() * 0.5;

        assertEquals(expectedWidth, collisionBounds.getWidth(), "Collision bounds width should be reduced correctly.");
        assertEquals(expectedHeight, collisionBounds.getHeight(), "Collision bounds height should be reduced correctly.");
    }
}
