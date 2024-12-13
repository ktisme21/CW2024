package com.example.demo.display;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.testutils.JavaFXTestUtils;

class GameOverImageTest {

    private GameOverImage gameOverImage;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @BeforeEach
    void setUpInstance() {
        // Initialize the GameOverImage object with a mock position
        gameOverImage = new GameOverImage(100, 100);
    }

    @Test
    void testShowGameOverImage() {
        Platform.runLater(() -> {
            // Ensure the elements are visible after calling the method
            gameOverImage.showGameOverImage();

            // Assert visibility of elements
            ImageView imageView = (ImageView) gameOverImage.lookup(".image-view");
            assertNotNull(imageView, "ImageView should exist.");
            assertTrue(imageView.isVisible(), "ImageView should be visible after calling showGameOverImage.");

            Label label = (Label) gameOverImage.lookup(".label");
            assertNotNull(label, "Label should exist.");
            assertTrue(label.isVisible(), "Label should be visible after calling showGameOverImage.");
        });

        // Wait for the JavaFX thread to complete
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
