package com.example.demo.view;

import com.example.demo.manager.MusicPlayer;
import com.example.demo.utilities.Constant;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PauseScreen {

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
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parentStage);
        stage.initStyle(StageStyle.UNDECORATED);

        VBox contentBox = createContentBox();
        Scene pauseScene = new Scene(contentBox, Constant.PAUSE_SCENE_WIDTH, Constant.PAUSE_SCENE_HEIGHT);
        stage.setScene(pauseScene);
        stage.setResizable(false);

        return stage;
    }

    private VBox createContentBox() {
        VBox contentBox = new VBox(Constant.PAUSE_SPACING);
        contentBox.setAlignment(Pos.CENTER);

        BackgroundUtil.setBackgroundImage(contentBox);

        contentBox.getChildren().addAll(
            createPauseLabel(),
            createVolumeControl(),
            createButtonRow()
        );

        return contentBox;
    }

    private Label createPauseLabel() {
        Label pauseLabel = new Label("Game Paused");
        pauseLabel.setStyle(Constant.TITLE_STYLE);
        return pauseLabel;
    }

    private VBox createVolumeControl() {
        Label volumeLabel = new Label("Volume");
        volumeLabel.setStyle(Constant.PAUSE_BUTTON_STYLE);

        Slider volumeSlider = new Slider(0, 100, Constant.PAUSE_DEFAULT_VOLUME);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setPrefWidth(300);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!isMuted) {
                MusicPlayer.setVolume(newVal.doubleValue() / 100.0);
            }
        });

        Button decrementButton = createVolumeButton("-", volumeSlider, -5);
        Button incrementButton = createVolumeButton("+", volumeSlider, 5);

        HBox sliderBox = new HBox(Constant.PAUSE_SPACING, decrementButton, volumeSlider, incrementButton);
        sliderBox.setAlignment(Pos.CENTER);

        Button muteButton = createMuteButton(volumeSlider);

        VBox volumeBox = new VBox(Constant.PAUSE_SPACING, volumeLabel, sliderBox, muteButton);
        volumeBox.setAlignment(Pos.CENTER);

        return volumeBox;
    }

    private Button createVolumeButton(String text, Slider volumeSlider, int adjustment) {
        Button button = new Button(text);
        button.setStyle(Constant.PAUSE_BUTTON_STYLE);
        button.setOnAction(event -> {
            double newValue = volumeSlider.getValue() + adjustment;
            volumeSlider.setValue(Math.min(100, Math.max(0, newValue)));
        });
        return button;
    }

    private Button createMuteButton(Slider volumeSlider) {
        Button muteButton = new Button("Mute");
        muteButton.setStyle(Constant.PAUSE_BUTTON_STYLE);
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
        HBox buttonRow = new HBox(Constant.PAUSE_SPACING);
        buttonRow.setAlignment(Pos.CENTER);

        buttonRow.getChildren().addAll(
            createResumeButton(),
            createQuitButton()
        );

        return buttonRow;
    }

    private Button createResumeButton() {
        Button resumeButton = new Button("Resume");
        resumeButton.setStyle(Constant.PAUSE_BUTTON_STYLE);
        resumeButton.setOnAction(e -> {
            onResumeAction.run();
            pauseStage.close();
        });
        return resumeButton;
    }

    private Button createQuitButton() {
        Button quitButton = new Button("Main Menu");
        quitButton.setStyle(Constant.PAUSE_BUTTON_STYLE);
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
