package com.example.demo.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GameStartScreen extends VBox {

    private static final int SCREEN_WIDTH = 1300; 
    private static final int SCREEN_HEIGHT = 750; 

    public GameStartScreen(EventHandler<MouseEvent> onStartGame, EventHandler<MouseEvent> onSettings, EventHandler<MouseEvent> onHowToPlay) {
        // Set the preferred width and height to match Main.java
        this.setPrefWidth(SCREEN_WIDTH);
        this.setPrefHeight(SCREEN_HEIGHT);

        // Set the background image using the utility
        BackgroundUtil.setBackgroundImage(this);

        // Title text
        Text title = new Text("Welcome to Sky Battle!");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Start Game button
        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 18px;");
        startButton.setOnMouseClicked(onStartGame);  // Set the action for starting the game

        // Settings button
        Button settingsButton = new Button("Settings");
        settingsButton.setStyle("-fx-font-size: 18px;");
        settingsButton.setOnMouseClicked(onSettings);  // Set the action for settings

        // How To Play button
        Button howToPlayButton = new Button("How To Play");
        howToPlayButton.setStyle("-fx-font-size: 18px;");
        howToPlayButton.setOnMouseClicked(onHowToPlay);  // Set the action for instruction

        // Layout setup
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        
        this.getChildren().addAll(title, startButton, settingsButton, howToPlayButton);
    }
}
