package com.example.demo.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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

        // Add padding and center alignment for overall layout
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

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
        volumeSlider.setPrefWidth(300); // Adjusted for better centering

        // Decrement button
        Button decrementButton = new Button("-");
        decrementButton.setStyle("-fx-font-size: 18px;");
        decrementButton.setOnMouseClicked(event -> volumeSlider.setValue(volumeSlider.getValue() - 5));

        // Increment button
        Button incrementButton = new Button("+");
        incrementButton.setStyle("-fx-font-size: 18px;");
        incrementButton.setOnMouseClicked(event -> volumeSlider.setValue(volumeSlider.getValue() + 5));

        // Horizontal box to hold decrement button, slider, and increment button
        HBox sliderBox = new HBox(10, decrementButton, volumeSlider, incrementButton);
        sliderBox.setAlignment(Pos.CENTER);

        // Mute button
        Button muteButton = new Button("Mute");
        muteButton.setStyle("-fx-font-size: 18px;");
        muteButton.setOnMouseClicked(event -> toggleMute(volumeSlider, muteButton));

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-size: 18px;");
        backButton.setOnMouseClicked(onBackToMain);

        // Add all elements and center them
        VBox volumeSection = new VBox(10, volumeLabel, sliderBox, muteButton);
        volumeSection.setAlignment(Pos.CENTER);

        this.setSpacing(20);
        this.getChildren().addAll(title, volumeSection, backButton);
    }

    private void toggleMute(Slider volumeSlider, Button muteButton) {
        if (isMuted) {
            volumeSlider.setDisable(false);
            muteButton.setText("Mute");
        } else {
            volumeSlider.setValue(0);
            volumeSlider.setDisable(true);
            muteButton.setText("Unmute");
        }
        isMuted = !isMuted;
    }
}
