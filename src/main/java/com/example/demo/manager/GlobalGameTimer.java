package com.example.demo.manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GlobalGameTimer {
    private static GlobalGameTimer instance = null; // Singleton instance
    private Duration totalTimeElapsed = Duration.ZERO;
    private final Timeline timer;

    private GlobalGameTimer() {
        // Initialize the timer
        timer = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            totalTimeElapsed = totalTimeElapsed.add(Duration.millis(10));
        }));
        timer.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
    }

    // Singleton instance getter
    public static GlobalGameTimer getInstance() {
        if (instance == null) {
            instance = new GlobalGameTimer();
        }
        return instance;
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
