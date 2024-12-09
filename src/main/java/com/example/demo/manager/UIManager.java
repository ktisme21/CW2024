package com.example.demo.manager;

import com.example.demo.utilities.Constant;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Manages the UI elements of the game, including the timer and pause button.
 * Responsible for creating, styling, and updating UI components.
 */
public class UIManager {

    private final Label timerLabel;
    private final Rectangle timerBackground;
    private final Button pauseButton;

    /**
     * Constructs a {@code UIManager} and initializes UI components.
     *
     * @param root The root node of the game scene.
     * @param screenWidth The width of the game screen.
     * @param onPauseAction The action to execute when the pause button is clicked.
     */
    public UIManager(Group root, double screenWidth, Runnable onPauseAction) {
        // Create and add UI components
        timerLabel = createTimerLabel(screenWidth);
        timerBackground = createTimerBackground(screenWidth);
        pauseButton = createPauseButton(screenWidth, onPauseAction);

        root.getChildren().addAll(timerBackground, timerLabel, pauseButton);
    }

    /**
     * Creates the timer label to display elapsed time.
     *
     * @param screenWidth The width of the game screen.
     * @return A styled {@code Label} for displaying the timer.
     */
    private Label createTimerLabel(double screenWidth) {
        Label label = new Label("Timer: 00:00:00");
        label.setStyle(Constant.TIMER_LABEL_STYLE);
        label.setLayoutX(screenWidth - Constant.TIMER_LABEL_X_POSITION);
        label.setLayoutY(Constant.TIMER_LABEL_Y_POSITION);
        return label;
    }

    /**
     * Creates the background for the timer.
     *
     * @param screenWidth The width of the game screen.
     * @return A {@code Rectangle} styled as the timer background.
     */
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

    /**
     * Creates the pause button with an icon.
     *
     * @param screenWidth The width of the game screen.
     * @param onPauseAction The action to execute when the button is clicked.
     * @return A styled {@code Button} for pausing the game.
     */
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

    /**
     * Updates the timer label with the elapsed time.
     *
     * @param elapsedTime The elapsed time as a formatted string.
     */
    public void updateTimer(String elapsedTime) {
        timerLabel.setText("Timer: " + elapsedTime);
    }
}