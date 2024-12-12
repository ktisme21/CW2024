package com.example.demo.utilities;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BackgroundUtilTest {

    @BeforeAll
    public static void initJavaFX() {
        // Initialize JavaFX runtime
        Platform.startup(() -> {});
    }

    @Test
    void testSetBackgroundImage() {
        // Create a Pane for testing
        Pane testPane = new Pane();

        // Call the method to set the background image
        BackgroundUtil.setBackgroundImage(testPane);

        // Get the background set on the pane
        Background background = testPane.getBackground();

        // Validate that the background is not null
        assertNotNull(background, "Background should not be null.");

        // Validate that the background contains an image
        assertFalse(background.getImages().isEmpty(), "Background should contain an image.");

        // Validate the properties of the background image
        BackgroundImage backgroundImage = background.getImages().get(0);
        assertNotNull(backgroundImage.getImage(), "Background image should not be null.");

        // Validate position and size
        assertEquals(BackgroundPosition.DEFAULT.getHorizontalSide(), backgroundImage.getPosition().getHorizontalSide(), "Horizontal side should match.");
        assertEquals(BackgroundPosition.DEFAULT.getVerticalSide(), backgroundImage.getPosition().getVerticalSide(), "Vertical side should match.");
        assertEquals(1300, backgroundImage.getSize().getWidth(), "Background width should be 1300.");
        assertEquals(750, backgroundImage.getSize().getHeight(), "Background height should be 750.");
    }
}
