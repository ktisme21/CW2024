package com.example.demo.manager;

import com.example.demo.testutils.JavaFXTestUtils;
import org.junit.jupiter.api.*;
import javafx.scene.media.MediaPlayer;

import static org.junit.jupiter.api.Assertions.*;

class MusicPlayerTest {

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @BeforeEach
    void resetMusicPlayer() {
        MusicPlayer.stopMusic(); // Ensure no music is playing before each test
    }

    @Test
    void testStartMusic() {
        assertDoesNotThrow(MusicPlayer::startMusic, "Starting music should not throw any exceptions.");
        assertNotNull(getBackgroundMediaPlayer(), "Background MediaPlayer should be initialized after starting music.");
        assertTrue(getBackgroundMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING, "Music should be playing after startMusic.");
    }

    @Test
    void testPauseAndResumeMusic() {
        MusicPlayer.startMusic();
        MusicPlayer.pauseMusic();
        assertTrue(getBackgroundMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED, "Music should be paused after calling pauseMusic.");

        MusicPlayer.resumeMusic();
        assertTrue(getBackgroundMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING, "Music should resume playing after calling resumeMusic.");
    }

    @Test
    void testStopMusic() {
        MusicPlayer.startMusic();
        MusicPlayer.stopMusic();
        assertNull(getBackgroundMediaPlayer(), "Background MediaPlayer should be null after stopping music.");
    }

    @Test
    void testSetAndGetVolume() {
        double testVolume = 0.8;
        MusicPlayer.setVolume(testVolume);
        assertEquals(testVolume, MusicPlayer.getVolume(), "Volume should match the value set using setVolume.");
        MusicPlayer.startMusic();
        assertEquals(testVolume, getBackgroundMediaPlayer().getVolume(), "MediaPlayer volume should reflect the set value.");
    }

    @Test
    void testSetAndGetSoundEffectVolume() {
        double testEffectVolume = 0.7;
        MusicPlayer.setSoundEffectVolume(testEffectVolume);
        assertEquals(testEffectVolume, MusicPlayer.getSoundEffectVolume(), "Sound effect volume should match the value set using setSoundEffectVolume.");
    }

    @Test
    void testPlayShootingSound() {
        assertDoesNotThrow(MusicPlayer::playShootingSound, "Playing shooting sound effect should not throw any exceptions.");
    }

    // Utility method to access private MediaPlayer for testing
    private MediaPlayer getBackgroundMediaPlayer() {
        try {
            var field = MusicPlayer.class.getDeclaredField("backgroundMediaPlayer");
            field.setAccessible(true);
            return (MediaPlayer) field.get(null);
        } catch (Exception e) {
            fail("Failed to access backgroundMediaPlayer for testing.");
            return null;
        }
    }
}