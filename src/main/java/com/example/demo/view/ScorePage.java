package com.example.demo.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ScorePage {

    private Stage popupStage;

    // Define styles as constants
    private static final String TITLE_STYLE = "-fx-font-size: 24px; -fx-font-weight: bold;";
    private static final String SCORE_STYLE = "-fx-font-size: 18px;";
    private static final String BUTTON_STYLE = "-fx-font-size: 18px;";
    private static final int SPACING = 20; // Spacing between VBox elements
    private static final double POPUP_WIDTH = 400; // Popup width
    private static final double POPUP_HEIGHT = 300; // Popup height

    public ScorePage(Stage ownerStage, EventHandler<MouseEvent> onBackToMain, EventHandler<MouseEvent> onRestart) {
        // Initialize the popup stage
        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Make it a modal dialog
        popupStage.initOwner(ownerStage); // Set the owner stage
        popupStage.setTitle("Score Page");

        // Create a VBox to hold the page content
        VBox contentBox = new VBox(SPACING); // Spacing between elements
        contentBox.setAlignment(Pos.CENTER);

        // Set the background image on the VBox
        BackgroundUtil.setBackgroundImage(contentBox);

        // Title text
        Text title = new Text("Time Used:");
        title.setStyle(TITLE_STYLE);

        // Placeholder score text
        Text scoreText = new Text("00:00");
        scoreText.setStyle(SCORE_STYLE);

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle(BUTTON_STYLE);
        backButton.setOnMouseClicked(event -> {
            popupStage.close(); // Close the popup
            onBackToMain.handle(event); // Handle the back-to-main-menu action
        });

        // Restart button
        Button restartButton = new Button("Restart");
        restartButton.setStyle(BUTTON_STYLE);
        restartButton.setOnMouseClicked(event -> {
            popupStage.close(); // Close the popup
            onRestart.handle(event); // Handle the restart action
        });

        // Add components to the VBox
        contentBox.getChildren().addAll(title, scoreText, restartButton, backButton);

        // Create the popup scene
        Scene popupScene = new Scene(contentBox, POPUP_WIDTH, POPUP_HEIGHT); // Set a smaller size for the popup
        popupStage.setScene(popupScene);
    }

    // Expose the `show()` method of the popup stage
    public void show() {
        popupStage.showAndWait(); // Display the popup
    }
}
