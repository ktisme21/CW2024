package com.example.demo.view;

import com.example.demo.MusicPlayer;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingsPage extends StackPane {

    private static final double DEFAULT_VOLUME = 50; // Default volume
    private boolean isMuted = false;

    public SettingsPage(Stage stage, EventHandler<MouseEvent> onBackToMain) {
        // Set the background image using the utility
        BackgroundUtil.setBackgroundImage(this);

        // Use stage dimensions for preferred size
        this.setPrefSize(stage.getWidth(), stage.getHeight());
        this.getStylesheets().add(getClass().getResource("/com/example/demo/style/style.css").toExternalForm());

        // Create a VBox for the content
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(20));

        // Title text for settings page
        Text title = new Text("Settings");
        title.getStyleClass().add("title-text");

        // Volume Slider Label
        Text volumeLabel = new Text("Volume");
        volumeLabel.getStyleClass().add("volume-label");

        // Volume Slider setup
        Slider volumeSlider = new Slider(0, 100, DEFAULT_VOLUME); // Min, Max, Initial
        volumeSlider.setMinorTickCount(4);
        volumeSlider.setBlockIncrement(10);
        volumeSlider.getStyleClass().add("volume-slider");

        // Adjust background music volume in real-time
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!isMuted) { // Only adjust volume if not muted
                double volume = newVal.doubleValue() / 100.0; // Convert to 0.0 - 1.0 range
                MusicPlayer.setVolume(volume); // Adjust the background music volume
            }
        });

        // Decrement button
        Button decrementButton = new Button("-");
        decrementButton.getStyleClass().add("button");
        decrementButton.setOnMouseClicked(event -> {
            double newVolume = Math.max(0, volumeSlider.getValue() - 5);
            volumeSlider.setValue(newVolume);
        });

        // Increment button
        Button incrementButton = new Button("+");
        incrementButton.getStyleClass().add("button");
        incrementButton.setOnMouseClicked(event -> {
            double newVolume = Math.min(100, volumeSlider.getValue() + 5);
            volumeSlider.setValue(newVolume);
        });

        // Horizontal box to hold decrement button, slider, and increment button
        HBox sliderBox = new HBox(10, decrementButton, volumeSlider, incrementButton);
        sliderBox.setAlignment(Pos.CENTER);

        // Mute button
        Button muteButton = new Button("Mute");
        muteButton.getStyleClass().add("button");
        muteButton.setOnMouseClicked(event -> toggleMute(volumeSlider, muteButton));

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.getStyleClass().add("button");
        backButton.setOnMouseClicked(onBackToMain);

        contentBox.getChildren().addAll(title, volumeLabel, sliderBox, muteButton, backButton);

        // Add the content box to the center of the StackPane
        this.getChildren().add(contentBox);
    }

    private void toggleMute(Slider volumeSlider, Button muteButton) {
        if (isMuted) {
            // Restore to default value after unmuting
            volumeSlider.setDisable(false);
            volumeSlider.setValue(DEFAULT_VOLUME); // Reset slider to default volume
            MusicPlayer.setVolume(DEFAULT_VOLUME / 100.0); // Reset volume to default
            muteButton.setText("Mute");
        } else {
            volumeSlider.setDisable(true);
            MusicPlayer.setVolume(0.0); // Mute music
            muteButton.setText("Unmute");
        }
        isMuted = !isMuted;
    }
}
