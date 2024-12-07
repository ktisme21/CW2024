package com.example.demo.display;

import com.example.demo.utilities.Constant;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.example.demo.testutils.JavaFXTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class WinImageTest {

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX();
    }

    @Test
    void testWinImageInitialization() {
        double xPosition = 100.0;
        double yPosition = 200.0;

        WinImage winImage = new WinImage(xPosition, yPosition);

        // Verify the win image properties
        ImageView imageView = (ImageView) winImage.getChildren().get(0);
        assertNotNull(imageView, "Win image should not be null");
        assertEquals(xPosition, imageView.getLayoutX(), "X position of win image should match");
        assertEquals(yPosition, imageView.getLayoutY(), "Y position of win image should match");
        assertEquals(Constant.WIN_IMAGE_HEIGHT, imageView.getFitHeight(), "Win image height should match");
        assertEquals(Constant.WIN_IMAGE_WIDTH, imageView.getFitWidth(), "Win image width should match");
        assertFalse(imageView.isVisible(), "Win image should be hidden by default");

        // Verify the instruction label properties
        Label label = (Label) winImage.getChildren().get(1);
        assertNotNull(label, "Instruction label should not be null");
        assertTrue(label.getText().contains("Press 'Q'"), "Instruction label text should match");
        assertFalse(label.isVisible(), "Instruction label should be hidden by default");
    }

    @Test
    void testShowWinImage() {
        WinImage winImage = new WinImage(100.0, 200.0);

        // Ensure the win image and label are hidden initially
        assertFalse(((ImageView) winImage.getChildren().get(0)).isVisible(), "Win image should be hidden initially");
        assertFalse(((Label) winImage.getChildren().get(1)).isVisible(), "Instruction label should be hidden initially");

        // Show the win image
        winImage.showWinImage();

        // Verify visibility
        assertTrue(((ImageView) winImage.getChildren().get(0)).isVisible(), "Win image should be visible after calling showWinImage");
        assertTrue(((Label) winImage.getChildren().get(1)).isVisible(), "Instruction label should be visible after calling showWinImage");
    }

    @Test
    void testHideWinImage() {
        WinImage winImage = new WinImage(100.0, 200.0);

        // Show the win image and label first
        winImage.showWinImage();
        assertTrue(((ImageView) winImage.getChildren().get(0)).isVisible(), "Win image should be visible initially");
        assertTrue(((Label) winImage.getChildren().get(1)).isVisible(), "Instruction label should be visible initially");

        // Hide the win image
        winImage.hideWinImage();

        // Verify visibility
        assertFalse(((ImageView) winImage.getChildren().get(0)).isVisible(), "Win image should be hidden after calling hideWinImage");
        assertFalse(((Label) winImage.getChildren().get(1)).isVisible(), "Instruction label should be hidden after calling hideWinImage");
    }
}
