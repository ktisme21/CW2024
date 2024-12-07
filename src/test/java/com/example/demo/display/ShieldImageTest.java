package com.example.demo.display;

import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.Constant;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShieldImageTest {

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX();
    }

    @Test
    void testShieldImageInitialization() {
        double xPosition = 100.0;
        double yPosition = 200.0;

        ShieldImage shieldImage = new ShieldImage(xPosition, yPosition);

        // Check position
        assertEquals(xPosition, shieldImage.getLayoutX(), "X position should match");
        assertEquals(yPosition, shieldImage.getLayoutY(), "Y position should match");

        // Check size
        assertEquals(Constant.SHIELD_SIZE, shieldImage.getFitWidth(), "Width should match the shield size");
        assertEquals(Constant.SHIELD_SIZE, shieldImage.getFitHeight(), "Height should match the shield size");

        // Check visibility (default is visible in this case)
        assertTrue(shieldImage.isVisible(), "Shield should be visible by default");
    }

    @Test
    void testShowShield() {
        ShieldImage shieldImage = new ShieldImage(100.0, 200.0);

        // Hide shield first
        shieldImage.setVisible(false);
        assertFalse(shieldImage.isVisible(), "Shield should be hidden");

        // Show shield
        shieldImage.showShield();
        assertTrue(shieldImage.isVisible(), "Shield should be visible");
    }

    @Test
    void testHideShield() {
        ShieldImage shieldImage = new ShieldImage(100.0, 200.0);

        // Ensure shield is visible first
        shieldImage.setVisible(true);
        assertTrue(shieldImage.isVisible(), "Shield should be visible");

        // Hide shield
        shieldImage.hideShield();
        assertFalse(shieldImage.isVisible(), "Shield should be hidden");
    }
}
