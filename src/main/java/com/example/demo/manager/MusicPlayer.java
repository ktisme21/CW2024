package com.example.demo.manager;

import javafx.scene.media.MediaPlayer;

/**
 * Utility class for managing background music and sound effects in the game.
 */
public class MusicPlayer {
    private static final String MUSIC_FILE_PATH = "/com/example/demo/music/backgroundmusic.mp3";
    private static MediaPlayer mediaPlayer;

    /**
     * Starts playing the background music in an infinite loop.
     */
    public static void startMusic() {
        // Initialize and play background music
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
     * Plays a shooting sound effect once.
     */
    public static void playShootingSound(){
        String shootingSoundPath = "/com/example/demo/music/shoot.mp3";
        MediaPlayer shootingMediaPlayer = new MediaPlayer(new javafx.scene.media.Media(
            MusicPlayer.class.getResource(shootingSoundPath).toExternalForm()
        ));
        shootingMediaPlayer.play();

        // Stop and release resources after the sound is played
        shootingMediaPlayer.setOnEndOfMedia(shootingMediaPlayer::dispose);
    }
}
