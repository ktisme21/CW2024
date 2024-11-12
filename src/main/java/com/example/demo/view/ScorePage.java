package com.example.demo.view;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import com.example.demo.controller.Main;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.text.Text;

public class ScorePage extends VBox {

    public void GameStartScreen(EventHandler<MouseEvent> onStartGame, EventHandler<MouseEvent> onBackToMain) {

        // Title text
        Text title = new Text("Ranking");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Start Game button
        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 18px;");
        startButton.setOnMouseClicked(onStartGame);  // Set the action for starting the game

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-size: 18px;");
        backButton.setOnMouseClicked(onBackToMain);  // Set the action for going back to main screen

        // Layout setup
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        this.getChildren().addAll(title, backButton);
        // Layout setup
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        
        this.getChildren().addAll(title, startButton);
    }
}
