package com.example.demo.manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Manages a global game timer, tracking elapsed time during gameplay.
 * This singleton class provides functionality to start, stop, reset, and
 * retrieve the elapsed time in the game.
 */
public class GlobalGameTimer {

    /** Singleton instance of the GlobalGameTimer. */
    private static GlobalGameTimer INSTANCE = new GlobalGameTimer();

    /** The total time elapsed since the timer started. */
    private Duration totalTimeElapsed = Duration.ZERO;

    /** The Timeline object used to update the timer. */
    private final Timeline timer;

    /**
     * Private constructor to enforce singleton pattern.
     * Initializes the timer to update every 10 milliseconds.
     */
    private GlobalGameTimer() {
        timer = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            totalTimeElapsed = totalTimeElapsed.add(Duration.millis(10));
        }));
        timer.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
    }

    /**
     * Returns the singleton instance of the GlobalGameTimer.
     *
     * @return the singleton instance.
     */
    public static GlobalGameTimer getInstance() {
        return INSTANCE;
    }

    /**
     * Starts the timer if it is not already running.
     */
    public void start() {
        if (timer.getStatus() != Timeline.Status.RUNNING) {
            timer.play();
        }
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Resets the timer to zero.
     */
    public void reset() {
        totalTimeElapsed = Duration.ZERO;
    }

    /**
     * Retrieves the total elapsed time since the timer started.
     *
     * @return the total elapsed time as a {@link Duration}.
     */
    public Duration getElapsedTime() {
        return totalTimeElapsed;
    }

    /**
     * Formats the elapsed time into a string in the format "MM:SS:MS".
     *
     * @return a formatted string representing the elapsed time.
     */
    public String formatElapsedTime() {
        Duration elapsedTime = getElapsedTime();
        int minutes = (int) elapsedTime.toMinutes();
        int seconds = (int) elapsedTime.toSeconds() % 60;
        int millis = (int) (elapsedTime.toMillis() % 1000);
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }
}
