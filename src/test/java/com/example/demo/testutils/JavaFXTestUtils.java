package com.example.demo.testutils;

import javafx.application.Platform;

public class JavaFXTestUtils {

    private static boolean javaFXInitialized = false;

    /**
     * Initializes the JavaFX platform if it hasn't been initialized already.
     * This method is safe to call multiple times.
     */
    public static void initializeJavaFX() {
        if (!javaFXInitialized) {
            try {
                Platform.startup(() -> {
                    // No-op, just start the JavaFX platform
                });
                javaFXInitialized = true;
            } catch (IllegalStateException e) {
                // JavaFX is already initialized, so no action is needed
            }
        }
    }
}
