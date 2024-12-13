package com.example.demo.display;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Displays the user's kill count in the game.
 * Tracks kills based on the reduction of enemy health to zero.
 */
public class HealthDisplay {

    private final StackPane container;
    private final Label killCountLabel;
    private int killCount;
    private int totalEnemies;

    /**
     * Constructs a HealthDisplay with initial settings.
     *
     * @param xPosition   The x-coordinate for the display.
     * @param yPosition   The y-coordinate for the display.
     * @param totalEnemies The total number of enemies in the level.
     */
    public HealthDisplay(double xPosition, double yPosition, int totalEnemies) {
        this.container = new StackPane();
        this.killCountLabel = new Label();
        this.killCount = totalEnemies;
        this.totalEnemies = totalEnemies;

        initializeLabel();
        positionDisplay(xPosition, yPosition);
    }

    /**
     * Initializes the visual properties of the kill count label.
     */
    private void initializeLabel() {
        killCountLabel.setText("Enemy Left: " + killCount);
        killCountLabel.setTextFill(Color.WHITE);
        killCountLabel.setFont(new Font("Papyrus", 25));
        container.getChildren().add(killCountLabel);
    }

    /**
     * Sets the position of the health display on the screen.
     *
     * @param xPosition The x-coordinate for the display.
     * @param yPosition The y-coordinate for the display.
     */
    private void positionDisplay(double xPosition, double yPosition) {
        container.setTranslateX(xPosition);
        container.setTranslateY(yPosition);
    }

    /**
     * Decreases the count of enemies left when one is defeated.
     */
    public void decrementKillCount() {
        if (killCount > 0) {
            killCount--;
            killCountLabel.setText("Enemy Left: " + killCount);
        }
    }

    /**
     * Resets the kill count, typically when a new level starts.
     */
    public void resetKillCount(int totalEnemies) {
        this.killCount = totalEnemies;
        this.totalEnemies = totalEnemies;
        killCountLabel.setText("Enemy Left: " + killCount);
    }

    /**
     * Returns the container for adding to the root node.
     *
     * @return The StackPane containing the kill count display.
     */
    public StackPane getContainer() {
        return container;
    }
}