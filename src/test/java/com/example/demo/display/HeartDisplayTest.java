package com.example.demo.display;

import com.example.demo.testutils.JavaFXTestUtils;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeartDisplayTest {

    private HeartDisplay heartDisplay;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX();
    }

    @BeforeEach
    void initHeartDisplay() {
        // Initialize the HeartDisplay with a mock position and a given number of hearts
        heartDisplay = new HeartDisplay(100, 50, 5);
    }

    @Test
    void testInitializeHearts() {
        // Verify the container has the correct initial number of hearts
        HBox container = heartDisplay.getContainer();
        assertEquals(5, container.getChildren().size(), "Initial heart count should match the specified number.");
    }

    @Test
    void testRemoveHeart() {
        // Remove one heart and verify the container's size decreases
        heartDisplay.removeHeart();
        HBox container = heartDisplay.getContainer();
        assertEquals(4, container.getChildren().size(), "Heart count should decrease by one after removing a heart.");

        // Remove all hearts and ensure the container is empty
        for (int i = 0; i < 4; i++) {
            heartDisplay.removeHeart();
        }
        assertTrue(container.getChildren().isEmpty(), "Container should be empty after removing all hearts.");
    }

    @Test
    void testContainerPosition() {
        // Verify the container's position is set correctly
        HBox container = heartDisplay.getContainer();
        assertEquals(100, container.getLayoutX(), "Container X position should be initialized correctly.");
        assertEquals(50, container.getLayoutY(), "Container Y position should be initialized correctly.");
    }
}
