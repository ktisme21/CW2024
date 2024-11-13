package com.example.demo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {

    private static final String MUSIC_FILE_PATH = "/com/example/demo/music/backgroundmusic.mp3";
    private static MediaPlayer mediaPlayer;

    public static void startMusic() {
        if (mediaPlayer == null) {
            // Load the music file
            Media music = new Media(MusicPlayer.class.getResource(MUSIC_FILE_PATH).toExternalForm());
            mediaPlayer = new MediaPlayer(music);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music indefinitely
        }
        mediaPlayer.play(); // Start playing the music
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop the music
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    public static void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause(); // Pause the music
        }
    }

    public static void resumeMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.play(); // Resume the music
        }
    }
}
