package com.example.demo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class HealthBarDisplay {

    private static final double BAR_WIDTH = 200;
    private static final double BAR_HEIGHT = 20;
    private static final double TEXT_OFFSET = 25;

    private final Rectangle backgroundBar;
    private final Rectangle healthBar;
    private final Text healthText;
    private final Group container;

    public HealthBarDisplay(double x, double y, double initialHealth, double maxHealth) {
        backgroundBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        backgroundBar.setFill(Color.DARKGRAY);
        backgroundBar.setStroke(Color.BLACK);
        backgroundBar.setLayoutX(x);
        backgroundBar.setLayoutY(y);

        healthBar = new Rectangle(BAR_WIDTH * (initialHealth / maxHealth), BAR_HEIGHT);
        healthBar.setFill(Color.RED);
        healthBar.setLayoutX(x);
        healthBar.setLayoutY(y);

        healthText = new Text(String.format("Health: %.0f/%.0f", initialHealth, maxHealth));
        healthText.setLayoutX(x);
        healthText.setLayoutY(y - TEXT_OFFSET);
        healthText.setFill(Color.WHITE);

        container = new Group(backgroundBar, healthBar, healthText);
    }

    public Group getContainer() {
        return container;
    }

    public void updateHealth(double currentHealth, double maxHealth) {
        double healthPercentage = currentHealth / maxHealth;
        healthBar.setWidth(BAR_WIDTH * healthPercentage);
        healthText.setText(String.format("Health: %.0f/%.0f", currentHealth, maxHealth));
    }

    public void updateKillsProgress(int currentKills, int killsToAdvance) {
        double progressPercentage = (double) currentKills / killsToAdvance;
        healthBar.setWidth(BAR_WIDTH * progressPercentage);
        healthText.setText(String.format("Kills: %d/%d", currentKills, killsToAdvance));
    }
}
