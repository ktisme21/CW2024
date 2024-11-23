package com.example.demo.view;

import com.example.demo.services.MusicPlayer;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PauseScreen {

    private static final String TITLE_STYLE = "-fx-font-size: 24px; -fx-font-weight: bold;";
    private static final String BUTTON_STYLE = "-fx-font-size: 16px;";
    private static final double SPACING = 20;
    private static final double SCENE_WIDTH = 500;
    private static final double SCENE_HEIGHT = 400;
    private static final double DEFAULT_VOLUME = 50.0;

    private final Stage pauseStage;
    private final Runnable onResumeAction;
    private final Runnable onQuitAction;
    private boolean isMuted = false;

    public PauseScreen(Stage parentStage, Runnable onResumeAction, Runnable onQuitAction) {
        this.onResumeAction = onResumeAction;
        this.onQuitAction = onQuitAction;
        this.pauseStage = createPauseStage(parentStage);
    }

    private Stage createPauseStage(Stage parentStage) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with the parent window
        stage.initOwner(parentStage);
        stage.setTitle("Game Paused");

        // Create content and set the scene
        VBox contentBox = createContentBox();
        Scene pauseScene = new Scene(contentBox, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setScene(pauseScene);
        stage.setResizable(false);

        return stage;
    }

    private VBox createContentBox() {
        VBox contentBox = new VBox(SPACING);
        contentBox.setAlignment(Pos.CENTER);

        // Set background image
        BackgroundUtil.setBackgroundImage(contentBox);

        // Add components
        contentBox.getChildren().addAll(
            createPauseLabel(),
            createVolumeControl(), // Volume control with slider and mute button
            createButtonRow()
        );

        return contentBox;
    }

    private Label createPauseLabel() {
        Label pauseLabel = new Label("Game Paused");
        pauseLabel.setStyle(TITLE_STYLE);
        return pauseLabel;
    }

    private VBox createVolumeControl() {
        // Volume label
        Label volumeLabel = new Label("Volume");
        volumeLabel.setStyle(BUTTON_STYLE);

        // Volume slider
        Slider volumeSlider = new Slider(0, 100, DEFAULT_VOLUME);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setPrefWidth(300);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!isMuted) {
                MusicPlayer.setVolume(newVal.doubleValue() / 100.0);
            }
        });

        // Decrement button
        Button decrementButton = new Button("-");
        decrementButton.setStyle(BUTTON_STYLE);
        decrementButton.setOnAction(event -> {
            volumeSlider.setValue(Math.max(0, volumeSlider.getValue() - 5));
        });

        // Increment button
        Button incrementButton = new Button("+");
        incrementButton.setStyle(BUTTON_STYLE);
        incrementButton.setOnAction(event -> {
            volumeSlider.setValue(Math.min(100, volumeSlider.getValue() + 5));
        });

        // Horizontal box for slider controls
        HBox sliderBox = new HBox(SPACING, decrementButton, volumeSlider, incrementButton);
        sliderBox.setAlignment(Pos.CENTER);

        // Mute/Unmute button
        Button muteButton = createMuteButton(volumeSlider);

        // Vertical box for label, slider, and mute button
        VBox volumeBox = new VBox(SPACING, volumeLabel, sliderBox, muteButton);
        volumeBox.setAlignment(Pos.CENTER);

        return volumeBox;
    }

    private Button createMuteButton(Slider volumeSlider) {
        Button muteButton = new Button("Mute");
        muteButton.setStyle(BUTTON_STYLE);
        muteButton.setOnAction(event -> toggleMute(volumeSlider, muteButton));
        return muteButton;
    }

    private void toggleMute(Slider volumeSlider, Button muteButton) {
        isMuted = !isMuted;
        if (isMuted) {
            MusicPlayer.setVolume(0.0);
            volumeSlider.setDisable(true);
            muteButton.setText("Unmute");
        } else {
            volumeSlider.setDisable(false);
            MusicPlayer.setVolume(volumeSlider.getValue() / 100.0);
            muteButton.setText("Mute");
        }
    }

    private HBox createButtonRow() {
        HBox buttonRow = new HBox(SPACING);
        buttonRow.setAlignment(Pos.CENTER);

        // Add Resume and Quit buttons to the row
        buttonRow.getChildren().addAll(
            createResumeButton(),
            createQuitButton()
        );

        return buttonRow;
    }

    private Button createResumeButton() {
        Button resumeButton = new Button("Resume");
        resumeButton.setStyle(BUTTON_STYLE);
        resumeButton.setOnAction(e -> {
            onResumeAction.run();
            pauseStage.close();
        });
        return resumeButton;
    }

    private Button createQuitButton() {
        Button quitButton = new Button("Main Menu");
        quitButton.setStyle(BUTTON_STYLE);
        quitButton.setOnAction(e -> {
            onQuitAction.run();
            pauseStage.close();
        });
        return quitButton;
    }

    public void show() {
        pauseStage.show();
    }
}
