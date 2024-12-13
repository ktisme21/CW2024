package com.example.demo.view;

import com.example.demo.manager.MusicPlayer;
import com.example.demo.testutils.JavaFXTestUtils;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        Platform.runLater(() -> {
            testStage = new Stage();
            pauseScreen = new PauseScreen(
                    testStage,
                    () -> System.out.println("Resume action executed"),
                    () -> System.out.println("Quit action executed")
            );
        });

        // Wait for the JavaFX initialization to complete
        JavaFXTestUtils.awaitFXTasks();
    }

    @Test
    void testPauseScreenInitialization() {
        Platform.runLater(() -> {
            pauseScreen.show();
            assertTrue(testStage.isShowing(), "Pause screen should be displayed.");
        });
    }

    @Test
    void testVolumeAdjustment() {
        Platform.runLater(() -> {
            pauseScreen.show();
            Slider volumeSlider = (Slider) pauseScreen.pauseStage.getScene().lookup(".slider");
            assertNotNull(volumeSlider, "Volume slider should exist in the pause screen.");

            // Simulate volume adjustment
            volumeSlider.setValue(75);
            assertEquals(75, volumeSlider.getValue(), "Volume slider value should be set to 75.");
            MusicPlayer.setVolume(volumeSlider.getValue() / 100.0);
            assertEquals(0.75, MusicPlayer.getVolume(), "MusicPlayer volume should match slider value.");
        });
    }

    @Test
    void testMuteToggle() throws Exception {
        Platform.runLater(() -> {
            try {
                pauseScreen.show();
                Slider volumeSlider = (Slider) pauseScreen.pauseStage.getScene().lookup(".slider");
                assertNotNull(volumeSlider, "Volume slider should exist in the pause screen.");

                // Access and invoke the private toggleMuteAll method
                Method toggleMuteAllMethod = PauseScreen.class.getDeclaredMethod("toggleMuteAll", javafx.scene.control.Label.class);
                toggleMuteAllMethod.setAccessible(true);

                Label muteLabel = new Label("Mute");
                volumeSlider.setValue(50); // Set initial volume
                toggleMuteAllMethod.invoke(pauseScreen, muteLabel); // Mute the sound

                // Access the private isMuted field to verify its state
                Field isMutedField = PauseScreen.class.getDeclaredField("isMuted");
                isMutedField.setAccessible(true);
                boolean isMuted = (boolean) isMutedField.get(pauseScreen);

                assertTrue(isMuted, "Sound should be muted.");
                assertEquals(0.0, MusicPlayer.getVolume(), "MusicPlayer volume should be 0 when muted.");

                // Unmute and verify
                toggleMuteAllMethod.invoke(pauseScreen, muteLabel);
                isMuted = (boolean) isMutedField.get(pauseScreen);

                assertFalse(isMuted, "Sound should be unmuted.");
                assertEquals(0.5, MusicPlayer.getVolume(), "MusicPlayer volume should restore to slider value.");
            } catch (Exception e) {
                fail("Reflection failed: " + e.getMessage());
            }
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
