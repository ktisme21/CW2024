package com.example.demo.manager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Utility class for managing background music and sound effects in the game.
 */
public class MusicPlayer {

    private static final String MUSIC_FILE_PATH = "/com/example/demo/music/backgroundmusic.mp3";
    private static final String SHOOTING_SOUND_PATH = "/com/example/demo/music/shoot.mp3";
    private static MediaPlayer backgroundMediaPlayer;
    private static double backgroundVolume = 0.5; // Default background music volume
    private static double soundEffectVolume = 1.0; // Default sound effect volume

    /**
     * Starts playing the background music in an infinite loop.
     */
    public static void startMusic() {
        if (backgroundMediaPlayer == null) {
            Media music = new Media(MusicPlayer.class.getResource(MUSIC_FILE_PATH).toExternalForm());
            backgroundMediaPlayer = new MediaPlayer(music);
            backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
            backgroundMediaPlayer.setVolume(backgroundVolume);
            backgroundMediaPlayer.play();
        } else {
            backgroundMediaPlayer.play(); // Resume if paused
        }
    }

    /**
     * Adjusts the volume of the background music.
     *
     * @param volume the desired volume level (0.0 to 1.0).
     */
    public static void setVolume(double volume) {
        backgroundVolume = Math.max(0.0, Math.min(volume, 1.0)); // Clamp between 0.0 and 1.0
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.setVolume(backgroundVolume);
        }
    }

    /**
     * Gets the current volume of the background music.
     *
     * @return the current volume level (0.0 to 1.0).
     */
    public static double getVolume() {
        return backgroundVolume;
    }

    /**
     * Stops the background music and releases resources.
     */
    public static void stopMusic() {
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.stop();
            backgroundMediaPlayer.dispose();
            backgroundMediaPlayer = null;
        }
    }

    /**
     * Pauses the background music.
     */
    public static void pauseMusic() {
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.pause();
        }
    }

    /**
     * Resumes the background music if paused.
     */
    public static void resumeMusic() {
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.play();
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
     * Gets the current volume for sound effects.
     *
     * @return the current sound effect volume (0.0 to 1.0).
     */
    public static double getSoundEffectVolume() {
        return soundEffectVolume;
    }

    /**
     * Plays a shooting sound effect once.
     */
    public static void playShootingSound() {
        Media shootingSound = new Media(MusicPlayer.class.getResource(SHOOTING_SOUND_PATH).toExternalForm());
        MediaPlayer shootingMediaPlayer = new MediaPlayer(shootingSound);
        shootingMediaPlayer.setVolume(soundEffectVolume); // Use the sound effect volume
        shootingMediaPlayer.play();

        // Dispose of resources after playback is finished
        shootingMediaPlayer.setOnEndOfMedia(shootingMediaPlayer::dispose);
    }
}
