package com.example.demo.view;

import com.example.demo.manager.MusicPlayer;
import com.example.demo.utilities.Constant;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents the settings page of the application.
 * Provides options to adjust volume and mute/unmute the background music.
 */
public class SettingsPage extends StackPane {

    private boolean isMuted = false;

    /**
     * Constructs a new `SettingsPage` for full-screen display.
     *
     * @param stage The stage to display the settings page.
     * @param onBackToMain Event handler to return to the main menu.
     */
    public SettingsPage(Stage stage, EventHandler<MouseEvent> onBackToMain) {
        // Set background image with proper scaling to fill screen
        Image backgroundImage = new Image(getClass().getResource(Constant.BACKGROUND_IMAGE_6_PATH).toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(stage.getWidth()); // Match the stage width
        backgroundView.setFitHeight(stage.getHeight()); // Match the stage height
        backgroundView.setPreserveRatio(false); // Ensure it fills the screen entirely

        // Add background image to the root
        this.getChildren().add(backgroundView);

        // Ensure root StackPane fills the entire stage
        this.setPrefSize(stage.getWidth(), stage.getHeight());

        // Create content container for the settings UI
        VBox contentBox = new VBox(Constant.SETTINGS_SPACING);
        contentBox.setAlignment(Pos.CENTER);

        // Add UI components to the content box
        contentBox.getChildren().addAll(
            createTitle(),
            createVolumeSlider(),
            createSoundEffectSlider(),
            createMuteButton(),
            createBackButton(onBackToMain) // Back to main menu button
        );

        // Center the content box
        this.getChildren().add(contentBox);
        StackPane.setAlignment(contentBox, Pos.CENTER);
    }

    private Label createTitle() {
        Label title = new Label("Settings");
        title.setStyle(Constant.TITLE_STYLE);
        return title;
    }

    private VBox createVolumeSlider() {
        Label volumeLabel = new Label("Volume");
        volumeLabel.setStyle(Constant.SETTINGS_BUTTON_STYLE);

        Slider volumeSlider = new Slider(0, 100, Constant.SETTINGS_DEFAULT_VOLUME);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setPrefWidth(Constant.SETTINGS_SLIDER_WIDTH);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!isMuted) {
                MusicPlayer.setVolume(newVal.doubleValue() / 100.0);
            }
        });

        Button decrementButton = new Button("-");
        decrementButton.setStyle(Constant.SETTINGS_BUTTON_STYLE);
        decrementButton.setOnMouseClicked(event -> {
            volumeSlider.setValue(Math.max(0, volumeSlider.getValue() - 5));
        });

        Button incrementButton = new Button("+");
        incrementButton.setStyle(Constant.SETTINGS_BUTTON_STYLE);
        incrementButton.setOnMouseClicked(event -> {
            volumeSlider.setValue(Math.min(100, volumeSlider.getValue() + 5));
        });

        HBox sliderBox = new HBox(Constant.SETTINGS_SPACING, decrementButton, volumeSlider, incrementButton);
        sliderBox.setAlignment(Pos.CENTER);

        VBox volumeBox = new VBox(Constant.SETTINGS_SPACING, volumeLabel, sliderBox);
        volumeBox.setAlignment(Pos.CENTER);

        return volumeBox;
    }

    private VBox createSoundEffectSlider() {
        Label effectVolumeLabel = new Label("Sound Effects Volume");
        effectVolumeLabel.setStyle(Constant.SETTINGS_BUTTON_STYLE);
    
        Slider effectVolumeSlider = new Slider(0, 100, 100); // Default volume is 100
        effectVolumeSlider.setShowTickLabels(true);
        effectVolumeSlider.setShowTickMarks(true);
        effectVolumeSlider.setPrefWidth(Constant.SETTINGS_SLIDER_WIDTH);
        effectVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            MusicPlayer.setSoundEffectVolume(newVal.doubleValue() / 100.0); // Scale to 0.0-1.0
        });
    
        Button decrementButton = new Button("-");
        decrementButton.setStyle(Constant.SETTINGS_BUTTON_STYLE);
        decrementButton.setOnMouseClicked(event -> {
            effectVolumeSlider.setValue(Math.max(0, effectVolumeSlider.getValue() - 5));
        });
    
        Button incrementButton = new Button("+");
        incrementButton.setStyle(Constant.SETTINGS_BUTTON_STYLE);
        incrementButton.setOnMouseClicked(event -> {
            effectVolumeSlider.setValue(Math.min(100, effectVolumeSlider.getValue() + 5));
        });
    
        HBox sliderBox = new HBox(Constant.SETTINGS_SPACING, decrementButton, effectVolumeSlider, incrementButton);
        sliderBox.setAlignment(Pos.CENTER);
    
        VBox effectVolumeBox = new VBox(Constant.SETTINGS_SPACING, effectVolumeLabel, sliderBox);
        effectVolumeBox.setAlignment(Pos.CENTER);
    
        return effectVolumeBox;
    }
    
    private StackPane createMuteButton() {
        StackPane muteButtonPane = new StackPane();
        muteButtonPane.setAlignment(Pos.CENTER);

        Image buttonImage = new Image(getClass().getResource(Constant.TEXTBAR2_IMAGE_PATH).toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(Constant.RETURN_BUTTON_WIDTH);
        imageView.setFitHeight(Constant.RETURN_BUTTON_HEIGHT);

        Label muteText = new Label("Mute");
        muteText.setStyle(Constant.SETTINGS_BUTTON_STYLE);
        muteText.setTranslateY(-5);

        muteButtonPane.getChildren().addAll(imageView, muteText);
        muteButtonPane.setOnMouseClicked(event -> toggleMute(muteText));

        return muteButtonPane;
    }

    private void toggleMute(Label muteText) {
        isMuted = !isMuted;
        if (isMuted) {
            MusicPlayer.setVolume(0.0);
            muteText.setText("Unmute");
        } else {
            MusicPlayer.setVolume(Constant.SETTINGS_DEFAULT_VOLUME / 100.0);
            muteText.setText("Mute");
        }
    }

    private StackPane createBackButton(EventHandler<MouseEvent> onBackToMain) {
        StackPane backButtonPane = new StackPane();
        backButtonPane.setAlignment(Pos.CENTER);

        Image buttonImage = new Image(getClass().getResource(Constant.TEXTBAR2_IMAGE_PATH).toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(Constant.RETURN_BUTTON_WIDTH);
        imageView.setFitHeight(Constant.RETURN_BUTTON_HEIGHT);

        Label backText = new Label("Close");
        backText.setStyle(Constant.SETTINGS_BUTTON_STYLE);
        backText.setTranslateY(-5);

        backButtonPane.getChildren().addAll(imageView, backText);
        backButtonPane.setOnMouseClicked(onBackToMain);

        return backButtonPane;
    }

}
