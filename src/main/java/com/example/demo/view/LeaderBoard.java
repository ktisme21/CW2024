package com.example.demo.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import com.example.demo.manager.LeaderboardManager;
import java.util.List;

public class LeaderBoard {
    // Define styles as constants
    private static final String TITLE_STYLE = "-fx-font-size: 24px; -fx-font-weight: bold;";
    private static final String SCORE_STYLE = "-fx-font-size: 18px; -fx-text-fill: white;";
    private static final String BUTTON_STYLE = "-fx-font-size: 18px;";
    private static final int SPACING = 20; // Spacing between VBox elements

    public LeaderBoard(Stage primaryStage, LeaderboardManager manager, String playerName, int playerScore, EventHandler<MouseEvent> onBackToMain, EventHandler<MouseEvent> onRestart) {
        // Create a VBox to hold the page content
        VBox contentBox = new VBox(SPACING); // Spacing between elements
        contentBox.setAlignment(Pos.CENTER);

        // Set the background image on the VBox
        BackgroundUtil.setBackgroundImage(contentBox);

        // Title text
        Text title = new Text("Leaderboard");
        title.setStyle(TITLE_STYLE);

        // Current Score Section
        Text currentScoreTitle = new Text("Your Score:");
        currentScoreTitle.setStyle(TITLE_STYLE);

        Text currentScoreText = new Text(playerName + " - " + formatTime(playerScore));
        currentScoreText.setStyle(SCORE_STYLE);

        // Add the player's current score to the leaderboard
        manager.addEntry(playerName, playerScore);

        // Display top leaderboard entries inside a StackPane for a custom background
        StackPane leaderboardBox = createLeaderboardBox(manager);

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle(BUTTON_STYLE);
        backButton.setOnMouseClicked(onBackToMain);

        // Restart button
        Button restartButton = new Button("Restart");
        restartButton.setStyle(BUTTON_STYLE);
        restartButton.setOnMouseClicked(onRestart);

        // Create an HBox for the buttons
        HBox buttonBox = new HBox(SPACING);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(restartButton, backButton);

        // Add components to the VBox
        contentBox.getChildren().addAll(title, currentScoreTitle, currentScoreText, leaderboardBox, buttonBox);

        // Create the leaderboard scene
        Scene leaderboardScene = new Scene(contentBox, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(leaderboardScene);
    }

    // Helper method to format time (milliseconds to MM:SS:SSS)
    private String formatTime(int score) {
        int minutes = score / 60000;
        int seconds = (score / 1000) % 60;
        int millis = score % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }

    // Helper method to create the leaderboard box with a background
    private StackPane createLeaderboardBox(LeaderboardManager manager) {
        // Create the VBox for leaderboard entries
        VBox leaderboardEntries = new VBox(10);
        leaderboardEntries.setAlignment(Pos.CENTER);

        List<LeaderboardManager.LeaderboardEntry> topEntries = manager.getTopEntries();
        for (LeaderboardManager.LeaderboardEntry entry : topEntries) {
            Text entryText = new Text(entry.getPlayerName() + " - " + formatTime(entry.getScore()));
            entryText.setStyle(SCORE_STYLE);
            leaderboardEntries.getChildren().add(entryText);
        }

        // Create the background image
        ImageView background = new ImageView(new Image(getClass().getResource("com/example/demo/images/background1.jpg").toExternalForm()));
        background.setFitWidth(400); // Set appropriate width
        background.setFitHeight(300); // Set appropriate height
        background.setPreserveRatio(true);

        // Add the background and leaderboard entries to the StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(background, leaderboardEntries);
        stackPane.setAlignment(Pos.CENTER);

        return stackPane;
    }
}
