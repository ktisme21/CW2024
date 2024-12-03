package com.example.demo.manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GlobalGameTimer {
    private static GlobalGameTimer INSTANCE = new GlobalGameTimer(); // Singleton instance
    private Duration totalTimeElapsed = Duration.ZERO;
    private final Timeline timer;

    private GlobalGameTimer() {
        // Initialize the timer
        timer = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            totalTimeElapsed = totalTimeElapsed.add(Duration.millis(10));
        }));
        timer.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
    }

    public String formatElapsedTime() {
        Duration elapsedTime = getElapsedTime();
        int minutes = (int) elapsedTime.toMinutes();
        int seconds = (int) elapsedTime.toSeconds() % 60;
        int millis = (int) (elapsedTime.toMillis() % 1000);
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }
    

    public static GlobalGameTimer getInstance() {
        return INSTANCE;
    }

    public void start() {
        if (timer.getStatus() != Timeline.Status.RUNNING) {
            timer.play();
        }
    }

    public void stop() {
        timer.stop();
    }

    public void reset() {
        totalTimeElapsed = Duration.ZERO;
    }

    public Duration getElapsedTime() {
        return totalTimeElapsed;
    }
}
