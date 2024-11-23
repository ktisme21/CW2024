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

    public ScorePage(Stage ownerStage, EventHandler<MouseEvent> onBackToMain) {
        // Initialize the popup stage
        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Make it a modal dialog
        popupStage.initOwner(ownerStage); // Set the owner stage
        popupStage.setTitle("Score Page");

        // Create a VBox to hold the page content
        VBox contentBox = new VBox(20); // 20px spacing between elements
        contentBox.setAlignment(Pos.CENTER);

        // Set the background image on the VBox
        BackgroundUtil.setBackgroundImage(contentBox);

        // Title text
        Text title = new Text("Rankings");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Placeholder score text
        Text scoreText = new Text("Your Score:");
        scoreText.setStyle("-fx-font-size: 18px;");

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-size: 18px;");
        backButton.setOnMouseClicked(event -> {
            popupStage.close(); // Close the popup
            onBackToMain.handle(event); // Handle the back-to-main-menu action
        });

        // Add components to the VBox
        contentBox.getChildren().addAll(title, scoreText, backButton);

        // Create the popup scene
        Scene popupScene = new Scene(contentBox, 400, 300); // Set a smaller size for the popup
        popupStage.setScene(popupScene);
    }

    // Expose the `show()` method of the popup stage
    public void show() {
        popupStage.showAndWait(); // Display the popup
    }
}
