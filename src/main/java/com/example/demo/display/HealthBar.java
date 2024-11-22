package com.example.demo.display;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthBar extends StackPane {

    private static final double BAR_WIDTH = 200; // Total width of the health bar
    private static final double BAR_HEIGHT = 20; // Height of the health bar
    private static final Color BACKGROUND_COLOR = Color.DARKRED; // Background (empty health)
    private static final Color FOREGROUND_COLOR = Color.LIMEGREEN; // Foreground (current health)

    private Rectangle backgroundBar;
    private Rectangle foregroundBar;

    public HealthBar(int maxHealth) {
        // Initialize the background bar
        backgroundBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        backgroundBar.setFill(BACKGROUND_COLOR);

        // Initialize the foreground bar (current health)
        foregroundBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        foregroundBar.setFill(FOREGROUND_COLOR);

        // Add both bars to the StackPane
        this.getChildren().addAll(backgroundBar, foregroundBar);
    }

    public void updateHealth(int currentHealth, int maxHealth) {
        double healthPercentage = (double) currentHealth / maxHealth;
        foregroundBar.setWidth(BAR_WIDTH * healthPercentage); // Adjust the width based on health
    }
}
