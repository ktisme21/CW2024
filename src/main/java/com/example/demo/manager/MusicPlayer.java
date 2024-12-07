package com.example.demo.manager;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

/**
 * Utility class for managing background music and sound effects in the game.
 */
public class MusicPlayer {
    private static final String MUSIC_FILE_PATH = "/com/example/demo/music/backgroundmusic.mp3";
    private static final String SHOOTING_SOUND_PATH = "/com/example/demo/music/shoot.mp3";
    private static MediaPlayer mediaPlayer;
    private static double soundEffectVolume = 1.0; // Default volume for sound effects

    /**
     * Starts playing the background music in an infinite loop.
     */
    public static void startMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer(new javafx.scene.media.Media(
                MusicPlayer.class.getResource(MUSIC_FILE_PATH).toExternalForm()
            ));
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    /**
     * Adjusts the volume of the background music.
     *
     * @param volume the desired volume level (0.0 to 1.0).
     */
    public static void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * Stops the background music and releases resources.
     */
    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    /**
     * Sets the volume for sound effects.
     *
     * @param volume the desired volume level for sound effects (0.0 to 1.0).
     */
    public static void setSoundEffectVolume(double volume) {
        soundEffectVolume = Math.max(0.0, Math.min(volume, 1.0)); // Clamp between 0.0 and 1.0
    }

    /**
     * Plays a shooting sound effect once.
     */
    public static void playShootingSound() {
        MediaPlayer shootingMediaPlayer = new MediaPlayer(new Media(
            MusicPlayer.class.getResource(SHOOTING_SOUND_PATH).toExternalForm()
        ));
        shootingMediaPlayer.setVolume(soundEffectVolume); // Use the sound effect volume
        shootingMediaPlayer.play();

        // Stop and release resources after the sound is played
        shootingMediaPlayer.setOnEndOfMedia(shootingMediaPlayer::dispose);
    }
}
