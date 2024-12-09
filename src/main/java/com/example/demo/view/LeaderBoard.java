package com.example.demo.view;

import com.example.demo.manager.LeaderboardManager;
import com.example.demo.utilities.BackgroundUtil;
import com.example.demo.utilities.Constant;
import javafx.scene.Scene;
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
import java.util.List;

/**
 * Displays the leaderboard screen, showing the player's score and top scores.
 */
public class LeaderBoard {

    /**
     * Constructs a {@code LeaderBoard} view.
     * 
     * @param primaryStage The main {@link Stage} to display the leaderboard.
     * @param manager       The {@link LeaderboardManager} to fetch top scores.
     * @param playerName    The name of the player.
     * @param playerScore   The score of the player.
     * @param onBackToMain  Event handler for returning to the main menu.
     * @param onRestart     Event handler for restarting the game.
     */
    public LeaderBoard(Stage primaryStage, LeaderboardManager manager, String playerName, int playerScore, EventHandler<MouseEvent> onBackToMain, EventHandler<MouseEvent> onRestart) {
        VBox contentBox = new VBox(Constant.LEADERBOARD_SPACING);
        contentBox.setAlignment(Pos.CENTER);

        BackgroundUtil.setBackgroundImage(contentBox);

        Text currentScoreTitle = new Text("Your Score:");
        currentScoreTitle.setStyle(Constant.TITLE_STYLE);

        Text currentScoreText = new Text(playerName + " - " + formatTime(playerScore));
        currentScoreText.setStyle(Constant.LEADERBOARD_SCORE_STYLE);

        StackPane leaderboardBox = createLeaderboardBox(manager);

        // Create buttons using the same design
        StackPane backButton = createImageButton("Main Menu", onBackToMain);
        StackPane restartButton = createImageButton("Restart", onRestart);

        HBox buttonBox = new HBox(Constant.LEADERBOARD_SPACING);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(restartButton, backButton);

        contentBox.getChildren().addAll(currentScoreTitle, currentScoreText, leaderboardBox, buttonBox);

        Scene leaderboardScene = new Scene(contentBox, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(leaderboardScene);
    }

    /**
     * Formats a time score into a readable format (MM:SS:MS).
     * 
     * @param score The time score in milliseconds.
     * @return The formatted time string.
     */
    private String formatTime(int score) {
        int minutes = score / 60000;
        int seconds = (score / 1000) % 60;
        int millis = score % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }

    private StackPane createLeaderboardBox(LeaderboardManager manager) {
        VBox leaderboardEntries = new VBox(10);
        leaderboardEntries.setAlignment(Pos.CENTER);

        Text leaderboardTitle = new Text("Leaderboard");
        leaderboardTitle.setStyle(Constant.TITLE_STYLE);

        List<LeaderboardManager.LeaderboardEntry> topEntries = manager.getTopEntries();
        for (LeaderboardManager.LeaderboardEntry entry : topEntries) {
            Text entryText = new Text(entry.getPlayerName() + " - " + formatTime(entry.getScore()));
            entryText.setStyle(Constant.LEADERBOARD_SCORE_STYLE);
            leaderboardEntries.getChildren().add(entryText);
        }

        VBox leaderboardContent = new VBox(10);
        leaderboardContent.setAlignment(Pos.CENTER);
        leaderboardContent.getChildren().addAll(leaderboardTitle, leaderboardEntries);

        ImageView background = new ImageView(new Image(getClass().getResource(Constant.LEADERBOARD_STACKPANE_IMAGE_PATH).toExternalForm()));
        background.setFitWidth(Constant.LEADERBOARD_STACKPANE_WIDTH);
        background.setFitHeight(Constant.LEADERBOARD_STACKPANE_HEIGHT);
        background.setPreserveRatio(true);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(background, leaderboardContent);
        stackPane.setAlignment(Pos.CENTER);

        return stackPane;
    }

    private StackPane createImageButton(String text, EventHandler<MouseEvent> eventHandler) {
        // Create a StackPane to hold the image and text
        StackPane buttonPane = new StackPane();
        buttonPane.setAlignment(Pos.CENTER);

        // Set background image for the button
        Image buttonImage = new Image(getClass().getResource(Constant.TEXTBAR2_IMAGE_PATH).toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(Constant.RETURN_BUTTON_WIDTH); // Use updated button dimensions
        imageView.setFitHeight(Constant.RETURN_BUTTON_HEIGHT);

        // Create the text to be displayed
        Text buttonText = new Text(text);
        buttonText.setStyle(Constant.GAME_START_BUTTON_STYLE); // Reuse the same button text style
        buttonText.setTranslateY(-5); // Adjust text position if needed

        // Add the image and text to the StackPane
        buttonPane.getChildren().addAll(imageView, buttonText);

        // Add event handler for button click
        buttonPane.setOnMouseClicked(eventHandler);

        return buttonPane;
    }
}
