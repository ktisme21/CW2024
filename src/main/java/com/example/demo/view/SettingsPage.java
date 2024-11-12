package com.example.demo.view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SettingsPage extends VBox {

    private static final int SCREEN_WIDTH = 1300; 
    private static final int SCREEN_HEIGHT = 750; 
    private boolean isMuted = false;

    public SettingsPage(EventHandler<MouseEvent> onBackToMain) {
        // Set the preferred width and height to match Main.java
        this.setPrefWidth(SCREEN_WIDTH);
        this.setPrefHeight(SCREEN_HEIGHT);
        
        // Title text for settings page
        Text title = new Text("Settings");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Volume Slider Label
        Text volumeLabel = new Text("Volume");
        volumeLabel.setStyle("-fx-font-size: 18px;");

        // Volume Slider setup
        Slider volumeSlider = new Slider(0, 100, 50); // Min: 0, Max: 100, Initial: 50
        volumeSlider.setMinorTickCount(4);
        volumeSlider.setBlockIncrement(10);
        volumeSlider.setPrefWidth(SCREEN_WIDTH); 

        // // Mute button
        Button muteButton = new Button("Mute");
        muteButton.setStyle("-fx-font-size: 18px;");
        muteButton.setOnMouseClicked(event -> toggleMute(volumeSlider, muteButton)); // Toggle mute state on click

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-size: 18px;");
        backButton.setOnMouseClicked(onBackToMain);  // Set the action for going back to main screen

        // Layout setup
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        this.getChildren().addAll(title, volumeLabel, volumeSlider, muteButton, backButton);
    }

    private void toggleMute(Slider volumeSlider, Button muteButton) {
        if (isMuted) {
            // Unmute: Restore slider to previous value and change button text
            volumeSlider.setDisable(false);
            muteButton.setText("Mute");
        } else {
            // Mute: Set slider to zero, disable it, and change button text
            volumeSlider.setValue(0);
            volumeSlider.setDisable(true);
            muteButton.setText("Unmute");
        }
        isMuted = !isMuted; // Toggle the mute state
    }
}
