package com.example.demo.manager;

import com.example.demo.utilities.Constant;
import com.example.demo.view.PauseScreen;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UIManager {

    private final Label timerLabel;
    private final Rectangle timerBackground;
    private final Button pauseButton;

    public UIManager(Group root, Stage stage, Runnable onPauseAction) {
        // Initialize timer label and background
        timerLabel = createTimerLabel();
        timerBackground = createTimerBackground();

        // Add timer components to the root
        root.getChildren().addAll(timerBackground, timerLabel);

        // Initialize and add the pause button
        pauseButton = createPauseButton(stage, onPauseAction);
        root.getChildren().add(pauseButton);
    }

    private Label createTimerLabel() {
        Label label = new Label("Timer: 00:00:00");
        label.setStyle(Constant.TIMER_LABEL_STYLE);
        label.setLayoutX(Constant.SCREEN_WIDTH - Constant.TIMER_LABEL_X_POSITION);
        label.setLayoutY(Constant.TIMER_LABEL_Y_POSITION);
        return label;
    }

    private Rectangle createTimerBackground() {
        Rectangle background = new Rectangle();
        background.setWidth(Constant.TIMER_BACKGROUND_WIDTH);
        background.setHeight(Constant.TIMER_BACKGROUND_HEIGHT);
        background.setFill(Color.web(Constant.TIMER_BACKGROUND_COLOR));
        background.setArcWidth(Constant.TIMER_BACKGROUND_CORNER_RADIUS);
        background.setArcHeight(Constant.TIMER_BACKGROUND_CORNER_RADIUS);
        background.setLayoutX(Constant.SCREEN_WIDTH - Constant.TIMER_LABEL_X_POSITION - 10);
        background.setLayoutY(Constant.TIMER_LABEL_Y_POSITION - 5);
        return background;
    }

    private Button createPauseButton(Stage stage, Runnable onPauseAction) {
        Image settingsImage = new Image(getClass().getResource("/com/example/demo/buttons/settings.png").toExternalForm());
        ImageView settingsImageView = new ImageView(settingsImage);
        settingsImageView.setFitWidth(Constant.PAUSE_BUTTON_WIDTH);
        settingsImageView.setFitHeight(Constant.PAUSE_BUTTON_HEIGHT);

        Button button = new Button();
        button.setGraphic(settingsImageView);
        button.setStyle("-fx-background-color: transparent;");
        button.setLayoutX(Constant.SCREEN_WIDTH - Constant.PAUSE_BUTTON_X_POSITION);
        button.setLayoutY(Constant.PAUSE_BUTTON_Y_POSITION);

        button.setOnAction(event -> onPauseAction.run());
        return button;
    }

    public void updateTimer(Duration elapsedTime) {
        int minutes = (int) elapsedTime.toMinutes();
        int seconds = (int) elapsedTime.toSeconds() % 60;
        int millis = (int) (elapsedTime.toMillis() % 1000);
        timerLabel.setText(String.format(Constant.TIMER_FORMAT, minutes, seconds, millis));
    }
}
