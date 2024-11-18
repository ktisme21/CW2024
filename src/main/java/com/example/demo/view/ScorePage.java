package com.example.demo.view;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ScorePage extends StackPane {
    public ScorePage(Stage stage, EventHandler<MouseEvent> onBackToMain) {
        // Set the background image using the utility
        BackgroundUtil.setBackgroundImage(this);

        // Use stage dimensions for preferred size
        this.setPrefSize(stage.getWidth(), stage.getHeight());

        // Create a VBox for the content
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);

        // Title text
        Text title = new Text("Rankings");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Display score (placeholder)
        Text scoreText = new Text("Your Score: 1234"); // Replace with actual score data
        scoreText.setStyle("-fx-font-size: 18px;");

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-size: 18px;");
        backButton.setOnMouseClicked(onBackToMain);  // Set the action for going back to main screen

        // Add components to VBox
        contentBox.getChildren().addAll(title, scoreText, backButton);

        // Add VBox to StackPane
        this.getChildren().add(contentBox);
    }
}
