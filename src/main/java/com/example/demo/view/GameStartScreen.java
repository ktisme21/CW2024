package com.example.demo.view;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameStartScreen extends StackPane {
    public GameStartScreen(Stage stage, EventHandler<MouseEvent> onStartGame, EventHandler<MouseEvent> onSettings, EventHandler<MouseEvent> onHowToPlay) {
        // Use stage dimensions for preferred size
        this.setPrefSize(stage.getWidth(), stage.getHeight());
        this.getStylesheets().add(getClass().getResource("/com/example/demo/style/style.css").toExternalForm());

        // Set the background image using the utility
        BackgroundUtil.setBackgroundImage(this);

        // Create a VBox for the content
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);

        // Title text
        Text title = new Text("Welcome to Sky Battle!");
        title.getStyleClass().add("title-text"); 

        // Start Game button
        Button startButton = new Button("Start Game");
        startButton.getStyleClass().add("button"); 
        startButton.setOnMouseClicked(onStartGame);  // Set the action for starting the game

        // Settings button
        Button settingsButton = new Button("Settings");
        settingsButton.getStyleClass().add("button"); // Apply CSS class
        settingsButton.setOnMouseClicked(onSettings);  // Set the action for settings

        // How To Play button
        Button howToPlayButton = new Button("How To Play");
        howToPlayButton.getStyleClass().add("button"); // Apply CSS class
        howToPlayButton.setOnMouseClicked(onHowToPlay);  // Set the action for instruction

        // Add components to VBox
        contentBox.getChildren().addAll(title, startButton, settingsButton, howToPlayButton);

        // Add VBox to center of StackPane
        this.getChildren().add(contentBox);
    }
}
