package com.example.demo.view;

import com.example.demo.manager.MusicPlayer;
import com.example.demo.utilities.Constant;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Represents the pause screen during gameplay.
 * Allows players to adjust volume, mute/unmute, or resume/quit the game.
 */
public class PauseScreen {

    private final Stage pauseStage;
    private final Runnable onResumeAction;
    private final Runnable onQuitAction;
    private boolean isMuted = false;

    /**
     * Constructs a new `PauseScreen`.
     *
     * @param parentStage The stage to attach the pause screen to.
     * @param onResumeAction Runnable action to resume the game.
     * @param onQuitAction Runnable action to quit the game.
     */
    public PauseScreen(Stage parentStage, Runnable onResumeAction, Runnable onQuitAction) {
        this.onResumeAction = onResumeAction;
        this.onQuitAction = onQuitAction;
        this.pauseStage = initializePauseStage(parentStage);
    }

    private Stage initializePauseStage(Stage parentStage) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parentStage);
        stage.initStyle(StageStyle.UNDECORATED);

        StackPane root = new StackPane();
        VBox contentBox = initializeContentBox();

        root.getChildren().addAll(createBackgroundImage(), contentBox);

        Scene pauseScene = new Scene(root, Constant.PAUSE_SCENE_WIDTH, Constant.PAUSE_SCENE_HEIGHT);
        stage.setScene(pauseScene);
        stage.setResizable(false);

        return stage;
    }

    private ImageView createBackgroundImage() {
        Image backgroundImage = new Image(getClass().getResource(Constant.BACKGROUND_IMAGE_6_PATH).toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(Constant.PAUSE_SCENE_WIDTH);
        backgroundView.setFitHeight(Constant.PAUSE_SCENE_HEIGHT);
        backgroundView.setPreserveRatio(false);
        return backgroundView;
    }

    private VBox initializeContentBox() {
        VBox contentBox = new VBox(Constant.PAUSE_SPACING);
        contentBox.setAlignment(Pos.CENTER);

        contentBox.getChildren().addAll(
            createPauseLabel(),
            initializeVolumeControl(),
            initializeButtonRow()
        );

        return contentBox;
    }

    private Label createPauseLabel() {
        Label pauseLabel = new Label("Game Paused");
        pauseLabel.setStyle(Constant.TITLE_STYLE);
        return pauseLabel;
    }

    private VBox initializeVolumeControl() {
        Label volumeLabel = new Label("Volume");
        volumeLabel.setStyle(Constant.PAUSE_BUTTON_STYLE);

        Slider volumeSlider = createVolumeSlider();

        Button decrementButton = createVolumeButton("-", volumeSlider, -5);
        Button incrementButton = createVolumeButton("+", volumeSlider, 5);

        HBox sliderBox = new HBox(Constant.PAUSE_SPACING, decrementButton, volumeSlider, incrementButton);
        sliderBox.setAlignment(Pos.CENTER);

        StackPane muteButton = createMuteButton(volumeSlider);

        VBox volumeBox = new VBox(Constant.PAUSE_SPACING, volumeLabel, sliderBox, muteButton);
        volumeBox.setAlignment(Pos.CENTER);

        return volumeBox;
    }

    private Slider createVolumeSlider() {
        Slider volumeSlider = new Slider(0, 100, Constant.PAUSE_DEFAULT_VOLUME);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setPrefWidth(300);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!isMuted) {
                MusicPlayer.setVolume(newVal.doubleValue() / 100.0);
            }
        });
        return volumeSlider;
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

    private StackPane createMuteButton(Slider volumeSlider) {
        StackPane muteButtonPane = new StackPane();
        muteButtonPane.setAlignment(Pos.CENTER);

        Image buttonImage = new Image(getClass().getResource(Constant.TEXTBAR2_IMAGE_PATH).toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(Constant.SMALL_BUTTON_WIDTH);
        imageView.setFitHeight(Constant.SMALL_BUTTON_HEIGHT);

        Label muteText = new Label("Mute");
        muteText.setStyle(Constant.PAUSE_BUTTON_STYLE);
        muteText.setTranslateY(-5);

        muteButtonPane.getChildren().addAll(imageView, muteText);
        muteButtonPane.setOnMouseClicked(event -> toggleMute(volumeSlider, muteText));

        return muteButtonPane;
    }

    private void toggleMute(Slider volumeSlider, Label muteText) {
        isMuted = !isMuted;
        if (isMuted) {
            MusicPlayer.setVolume(0.0);
            volumeSlider.setDisable(true);
            muteText.setText("Unmute");
        } else {
            volumeSlider.setDisable(false);
            MusicPlayer.setVolume(volumeSlider.getValue() / 100.0);
            muteText.setText("Mute");
        }
    }

    private HBox initializeButtonRow() {
        HBox buttonRow = new HBox(Constant.PAUSE_SPACING);
        buttonRow.setAlignment(Pos.CENTER);

        buttonRow.getChildren().addAll(
            createResumeButton(),
            createQuitButton()
        );

        return buttonRow;
    }

    private StackPane createResumeButton() {
        return createTextBarButton("Resume", () -> {
            onResumeAction.run();
            pauseStage.close();

            Stage parentStage = (Stage) pauseStage.getOwner();
            if (parentStage.getScene() != null) {
                parentStage.getScene().lookup("#background").requestFocus();
            }
        });
    }

    private StackPane createQuitButton() {
        return createTextBarButton("Main Menu", onQuitAction);
    }

    private StackPane createTextBarButton(String text, Runnable action) {
        StackPane buttonPane = new StackPane();
        buttonPane.setAlignment(Pos.CENTER);

        Image buttonImage = new Image(getClass().getResource(Constant.TEXTBAR2_IMAGE_PATH).toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(Constant.SMALL_BUTTON_WIDTH);
        imageView.setFitHeight(Constant.SMALL_BUTTON_HEIGHT);

        Label buttonText = new Label(text);
        buttonText.setStyle(Constant.PAUSE_BUTTON_STYLE);
        buttonText.setTranslateY(-5);

        buttonPane.getChildren().addAll(imageView, buttonText);
        buttonPane.setOnMouseClicked(event -> {
            action.run();
            pauseStage.close();
        });

        return buttonPane;
    }

    /**
     * Displays the pause screen.
     */
    public void show() {
        pauseStage.show();
    }
}
