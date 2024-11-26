package com.example.demo.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private static final String SCORE_STYLE = "-fx-font-size: 18px;";
    private static final String BUTTON_STYLE = "-fx-font-size: 18px;";
    private static final int SPACING = 20; // Spacing between VBox elements

    public LeaderBoard(Stage primaryStage, String timeUsed, EventHandler<MouseEvent> onBackToMain, EventHandler<MouseEvent> onRestart) {
        // Create a VBox to hold the page content
        VBox contentBox = new VBox(SPACING); // Spacing between elements
        contentBox.setAlignment(Pos.CENTER);

        // Set the background image on the VBox
        BackgroundUtil.setBackgroundImage(contentBox);

        // Title text
        Text title = new Text("Leaderboard");
        title.setStyle(TITLE_STYLE);

        // Display the elapsed time
        Text timeUsedText = new Text("Time Used: " + timeUsed);
        timeUsedText.setStyle(SCORE_STYLE);

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle(BUTTON_STYLE);
        backButton.setOnMouseClicked(onBackToMain);

        // Restart button
        Button restartButton = new Button("Restart");
        restartButton.setStyle(BUTTON_STYLE);
        restartButton.setOnMouseClicked(event -> {
            primaryStage.setScene(null); // Clear the current scene
            onRestart.handle(event); // Invoke the restart action
        });

        // Add components to the VBox
        contentBox.getChildren().addAll(title, timeUsedText, restartButton, backButton);

        // Create the popup scene
        Scene leaderboardScene = new Scene(contentBox, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(leaderboardScene);
    }
    
}
