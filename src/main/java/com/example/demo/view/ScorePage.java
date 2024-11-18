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
        this.getStylesheets().add(getClass().getResource("/com/example/demo/style/style.css").toExternalForm());

        // Create a VBox for the content
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);

        // Title text
        Text title = new Text("Rankings");
        title.getStyleClass().add("title-text");

        // Display score (placeholder)
        Text scoreText = new Text("Your Score:"); // Replace with actual score data
        scoreText.getStyleClass().add("score-text");

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.getStyleClass().add("button");
        backButton.setOnMouseClicked(onBackToMain); // Set the action for going back to main screen

        // Add components to VBox
        contentBox.getChildren().addAll(title, scoreText, backButton);

        // Add VBox to StackPane
        this.getChildren().add(contentBox);
    }
}
