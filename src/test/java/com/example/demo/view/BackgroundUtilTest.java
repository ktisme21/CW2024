package com.example.demo.view;

import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.BackgroundUtil;
import com.example.demo.utilities.Constant;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Background;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BackgroundUtilTest {

    private Pane testPane;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Initialize JavaFX for testing
    }

    @BeforeEach
    void setUpInstance() {
        testPane = new Pane(); // Create a new Pane for each test
    }

    @Test
    void testSetBackgroundImage() {
        // Set the background image
        BackgroundUtil.setBackgroundImage(testPane);

        // Validate that the background is not null
        Background background = testPane.getBackground();
        assertNotNull(background, "Background should not be null.");

        // Validate the BackgroundImage object
        assertNotNull(background.getImages(), "Background should have images.");
        assertEquals(1, background.getImages().size(), "Background should have exactly one image.");

        BackgroundImage backgroundImage = background.getImages().get(0);

        // Validate background image path
        String expectedImagePath = BackgroundUtil.class.getResource(Constant.BACKGROUND_IMAGE_PATH).toExternalForm();
        assertEquals(expectedImagePath, backgroundImage.getImage().getUrl(), "Background image URL should match the expected URL.");

        // Validate background size
        assertEquals(1300, backgroundImage.getImage().getWidth(), "Background image width should be 1300.");
        assertEquals(750, backgroundImage.getImage().getHeight(), "Background image height should be 750.");

        // Validate background repeat
        assertEquals(BackgroundRepeat.NO_REPEAT, backgroundImage.getRepeatX(), "Background repeat X should be NO_REPEAT.");
        assertEquals(BackgroundRepeat.NO_REPEAT, backgroundImage.getRepeatY(), "Background repeat Y should be NO_REPEAT.");
    }
}
