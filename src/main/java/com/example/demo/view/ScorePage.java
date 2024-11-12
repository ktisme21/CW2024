package com.example.demo.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ScorePage extends VBox {

    public ScorePage(EventHandler<MouseEvent> onBackToMain) {
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

        // Layout setup
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        // Add components to layout
        this.getChildren().addAll(title, scoreText, backButton);
    }
}
