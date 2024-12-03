package com.example.demo.manager;

import com.example.demo.utilities.Constant;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UIManager {

    private final Label timerLabel;
    private final Rectangle timerBackground;
    private final Button pauseButton;

    public UIManager(Group root, double screenWidth, Runnable onPauseAction) {
        // Create and add UI components
        timerLabel = createTimerLabel(screenWidth);
        timerBackground = createTimerBackground(screenWidth);
        pauseButton = createPauseButton(screenWidth, onPauseAction);

        root.getChildren().addAll(timerBackground, timerLabel, pauseButton);
    }

    private Label createTimerLabel(double screenWidth) {
        Label label = new Label("Timer: 00:00:00");
        label.setStyle(Constant.TIMER_LABEL_STYLE);
        label.setLayoutX(screenWidth - Constant.TIMER_LABEL_X_POSITION);
        label.setLayoutY(Constant.TIMER_LABEL_Y_POSITION);
        return label;
    }

    private Rectangle createTimerBackground(double screenWidth) {
        Rectangle background = new Rectangle();
        background.setWidth(Constant.TIMER_BACKGROUND_WIDTH);
        background.setHeight(Constant.TIMER_BACKGROUND_HEIGHT);
        background.setFill(Color.web(Constant.TIMER_BACKGROUND_COLOR)); // Semi-transparent
        background.setArcWidth(Constant.TIMER_BACKGROUND_CORNER_RADIUS);
        background.setArcHeight(Constant.TIMER_BACKGROUND_CORNER_RADIUS);
        background.setLayoutX(screenWidth - Constant.TIMER_LABEL_X_POSITION - 10); // Adjust padding
        background.setLayoutY(Constant.TIMER_LABEL_Y_POSITION - 5); // Adjust padding
        return background;
    }

    private Button createPauseButton(double screenWidth, Runnable onPauseAction) {
        Image settingsImage = new Image(getClass().getResource("/com/example/demo/buttons/settings.png").toExternalForm());
        ImageView settingsImageView = new ImageView(settingsImage);
        settingsImageView.setFitWidth(Constant.PAUSE_BUTTON_WIDTH);
        settingsImageView.setFitHeight(Constant.PAUSE_BUTTON_HEIGHT);

        Button button = new Button();
        button.setGraphic(settingsImageView);
        button.setStyle("-fx-background-color: transparent;"); // No default styling
        button.setLayoutX(screenWidth - Constant.PAUSE_BUTTON_X_POSITION);
        button.setLayoutY(Constant.PAUSE_BUTTON_Y_POSITION);
        button.setOnAction(event -> onPauseAction.run());

        return button;
    }

    public void updateTimer(String elapsedTime) {
        timerLabel.setText("Timer: " + elapsedTime);
    }
}
