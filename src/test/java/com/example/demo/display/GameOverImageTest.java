package com.example.demo.display;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameOverImageTest{

    private GameOverImage gameOverImage;

    @BeforeAll
    public static void initJavaFX() {
        try {
            Platform.startup(() -> {
                // No-op, just start the JavaFX platform
            });
        } catch (IllegalStateException e) {
            // If JavaFX is already initialized, ignore this exception
        }
    }

    @BeforeEach
    void setUp() {
        // Initialize the GameOverImage object with some position
        gameOverImage = new GameOverImage(100, 100);
    }

    @Test
    void testShowGameOverImage() {
//        // Initially, the elements should not be visible
//        assertFalse(gameOverImage.lookupAll(".image-view").iterator().next().isVisible());
//        assertFalse(gameOverImage.lookupAll(".label").iterator().next().isVisible());

        // Call the method to make elements visible
        gameOverImage.showGameOverImage();

        // Assert that the elements are now visible
        assertTrue(gameOverImage.lookupAll(".image-view").iterator().next().isVisible());
        assertTrue(gameOverImage.lookupAll(".label").iterator().next().isVisible());
    }
}
