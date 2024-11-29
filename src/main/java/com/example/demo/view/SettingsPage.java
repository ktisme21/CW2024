package com.example.demo.view;

import com.example.demo.manager.MusicPlayer;
import com.example.demo.utilities.Constant;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

public class SettingsPage extends StackPane {

    private boolean isMuted = false;

    public SettingsPage(Stage stage, EventHandler<MouseEvent> onBackToMain) {
        // Set the background image
        BackgroundUtil.setBackgroundImage(this);

        // Set preferred size based on stage dimensions
        this.setPrefSize(stage.getWidth(), stage.getHeight());

        // Create content container
        VBox contentBox = new VBox(Constant.SETTINGS_SPACING);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(20));

        // Add components to the content box
        contentBox.getChildren().addAll(
            createTitle(),
            createVolumeSlider(),
            createMuteButton(),
            createBackButton(onBackToMain)
        );

        // Add content box to the root pane
        this.getChildren().add(contentBox);
        StackPane.setAlignment(contentBox, Pos.CENTER);
    }

    private Label createTitle() {
        Label title = new Label("Settings");
        title.setStyle(Constant.TITLE_STYLE);
        return title;
    }

    private VBox createVolumeSlider() {
        // Volume label
        Label volumeLabel = new Label("Volume");
        volumeLabel.setStyle(Constant.SETTINGS_BUTTON_STYLE);

        // Volume slider
        Slider volumeSlider = new Slider(0, 100, Constant.SETTINGS_DEFAULT_VOLUME);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setPrefWidth(Constant.SETTINGS_SLIDER_WIDTH);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!isMuted) {
                MusicPlayer.setVolume(newVal.doubleValue() / 100.0);
            }
        });

        // Decrement button
        Button decrementButton = new Button("-");
        decrementButton.setStyle(Constant.SETTINGS_BUTTON_STYLE);
        decrementButton.setOnMouseClicked(event -> {
            volumeSlider.setValue(Math.max(0, volumeSlider.getValue() - 5));
        });

        // Increment button
        Button incrementButton = new Button("+");
        incrementButton.setStyle(Constant.SETTINGS_BUTTON_STYLE);
        incrementButton.setOnMouseClicked(event -> {
            volumeSlider.setValue(Math.min(100, volumeSlider.getValue() + 5));
        });

        // Horizontal box for slider controls
        HBox sliderBox = new HBox(Constant.SETTINGS_SPACING, decrementButton, volumeSlider, incrementButton);
        sliderBox.setAlignment(Pos.CENTER);

        // Vertical box for label and slider
        VBox volumeBox = new VBox(Constant.SETTINGS_SPACING, volumeLabel, sliderBox);
        volumeBox.setAlignment(Pos.CENTER);

        return volumeBox;
    }

    private StackPane createMuteButton() {
        // Create a StackPane for the button
        StackPane muteButtonPane = new StackPane();
        muteButtonPane.setAlignment(Pos.CENTER);

        // Set the background image for the mute button
        Image buttonImage = new Image(getClass().getResource(Constant.TEXTBAR2_IMAGE_PATH).toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(Constant.RETURN_BUTTON_WIDTH);
        imageView.setFitHeight(Constant.RETURN_BUTTON_HEIGHT);

        // Create the text
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
        // Create a StackPane for the back button
        StackPane backButtonPane = new StackPane();
        backButtonPane.setAlignment(Pos.CENTER);

        // Set the background image for the back button
        Image buttonImage = new Image(getClass().getResource(Constant.TEXTBAR2_IMAGE_PATH).toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(Constant.RETURN_BUTTON_WIDTH);
        imageView.setFitHeight(Constant.RETURN_BUTTON_HEIGHT);

        // Create the text
        Label backText = new Label("Close");
        backText.setStyle(Constant.SETTINGS_BUTTON_STYLE);
        backText.setTranslateY(-5);

        backButtonPane.getChildren().addAll(imageView, backText);
        backButtonPane.setOnMouseClicked(onBackToMain);

        return backButtonPane;
    }
}
