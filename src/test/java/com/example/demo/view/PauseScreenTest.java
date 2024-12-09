package com.example.demo.view;

import com.example.demo.manager.MusicPlayer;
import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.Constant;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PauseScreenTest {

    private Stage testStage;
    private PauseScreen pauseScreen;

    @BeforeAll
    public static void initJavaFX() {
        JavaFXTestUtils.initializeJavaFX();
    }

    @BeforeEach
    void setUp() {
        testStage = new Stage();
        pauseScreen = new PauseScreen(
            testStage,
            () -> System.out.println("Resume action executed"),
            () -> System.out.println("Quit action executed")
        );
    }

    @Test
    void testPauseScreenInitialization() {
        Platform.runLater(() -> {
            // Ensure the pause screen is created without issues
            pauseScreen.show();
            assertTrue(testStage.isShowing(), "Pause screen should be displayed.");
        });
    }

    @Test
    void testVolumeAdjustment() {
        Platform.runLater(() -> {
            Slider volumeSlider = (Slider) pauseScreen.pauseStage.getScene().lookup(".slider");
            assertNotNull(volumeSlider, "Volume slider should exist in the pause screen.");

            // Simulate volume adjustment
            volumeSlider.setValue(50);
            assertEquals(50, volumeSlider.getValue(), "Volume slider value should be set to 50.");
            MusicPlayer.setVolume(volumeSlider.getValue() / 100.0);
            assertEquals(0.5, MusicPlayer.getVolume(), "MusicPlayer volume should match slider value.");
        });
    }

    @Test
    void testMuteToggle() {
        Platform.runLater(() -> {
            Slider volumeSlider = (Slider) pauseScreen.pauseStage.getScene().lookup(".slider");
            assertNotNull(volumeSlider, "Volume slider should exist in the pause screen.");

            pauseScreen.show();

            // Simulate mute toggle
            volumeSlider.setValue(50); // Set initial volume
            pauseScreen.toggleMute(volumeSlider, null); // Mute the sound
            assertTrue(pauseScreen.isMuted(), "Sound should be muted.");
            assertEquals(0.0, MusicPlayer.getVolume(), "MusicPlayer volume should be 0 when muted.");
            assertTrue(volumeSlider.isDisabled(), "Volume slider should be disabled when muted.");

            // Unmute and verify
            pauseScreen.toggleMute(volumeSlider, null);
            assertFalse(pauseScreen.isMuted(), "Sound should be unmuted.");
            assertEquals(0.5, MusicPlayer.getVolume(), "MusicPlayer volume should restore to slider value.");
            assertFalse(volumeSlider.isDisabled(), "Volume slider should be enabled when unmuted.");
        });
    }



    @Test
    void testResumeButton() {
        Platform.runLater(() -> {
            pauseScreen.show();

            // Simulate resume button click
            Scene scene = pauseScreen.pauseStage.getScene();
            scene.lookup(".resume-button").fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 0, false, false, false, false, false, false, false, false, false, false, null));

            // Ensure the pause screen is closed
            assertFalse(testStage.isShowing(), "Pause screen should be closed after resume.");
        });
    }

    @Test
    void testQuitButton() {
        Platform.runLater(() -> {
            pauseScreen.show();

            // Simulate quit button click
            Scene scene = pauseScreen.pauseStage.getScene();
            scene.lookup(".quit-button").fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 0, false, false, false, false, false, false, false, false, false, false, null));

            // Ensure the pause screen is closed
            assertFalse(testStage.isShowing(), "Pause screen should be closed after quitting.");
        });
    }
}
