package com.example.demo.manager;

import javafx.util.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalGameTimerTest {

    private GlobalGameTimer gameTimer;

    @BeforeEach
    void setUp() {
        gameTimer = GlobalGameTimer.getInstance();
        gameTimer.reset(); // Reset the timer before each test
        gameTimer.stop(); // Ensure the timer is stopped before each test
    }

    @Test
    void testSingletonInstance() {
        // Verify the same instance is returned every time
        GlobalGameTimer timer1 = GlobalGameTimer.getInstance();
        GlobalGameTimer timer2 = GlobalGameTimer.getInstance();
        assertSame(timer1, timer2, "The GlobalGameTimer instance should be a singleton.");
    }

    @Test
    void testStartAndElapsedTime() throws InterruptedException {
        // Start the timer
        gameTimer.start();
        // Wait for 100 milliseconds
        Thread.sleep(100);
        gameTimer.stop();

        // Get elapsed time and ensure it's around 100ms
        Duration elapsedTime = gameTimer.getElapsedTime();
        assertTrue(elapsedTime.toMillis() >= 100 && elapsedTime.toMillis() < 150,
                "Elapsed time should be close to 100 milliseconds.");
    }

    @Test
    void testStop() throws InterruptedException {
        // Start the timer
        gameTimer.start();
        // Wait for 100 milliseconds
        Thread.sleep(100);
        // Stop the timer
        gameTimer.stop();

        // Store elapsed time
        Duration elapsedTime = gameTimer.getElapsedTime();

        // Wait for another 100 milliseconds
        Thread.sleep(100);

        // Ensure the elapsed time hasn't changed after stopping
        assertEquals(elapsedTime.toMillis(), gameTimer.getElapsedTime().toMillis(),
                "Elapsed time should not change after the timer is stopped.");
    }

    @Test
    void testReset() {
        // Start the timer
        gameTimer.start();
        // Stop the timer
        gameTimer.stop();
        // Reset the timer
        gameTimer.reset();

        // Ensure elapsed time is zero
        assertEquals(0, gameTimer.getElapsedTime().toMillis(),
                "Elapsed time should be zero after reset.");
    }

    @Test
    void testFormatElapsedTime() throws InterruptedException {
        // Start the timer
        gameTimer.start();
        // Wait for 1.5 seconds
        Thread.sleep(1500);
        gameTimer.stop();

        // Format the elapsed time
        String formattedTime = gameTimer.formatElapsedTime();

        // Verify the formatted string
        assertTrue(formattedTime.startsWith("00:01:"),
                "Formatted time should represent 1 second.");
    }
}
