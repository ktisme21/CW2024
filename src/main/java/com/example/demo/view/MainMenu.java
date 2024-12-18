package com.example.demo.view;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.example.demo.utilities.BackgroundUtil;
import com.example.demo.utilities.Constant;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Represents the main menu screen of the application.
 * Provides options to start the game, open settings, or quit.
 */
public class MainMenu extends StackPane {

    /**
     * Constructs a new `MainMenu`.
     *
     * @param stage The primary stage of the application.
     * @param onStartGame Event handler for starting the game.
     * @param onEndlessMode Event handler for starting the endless mode.
     * @param onSettings Event handler for opening the settings.
     * @param onQuit Event handler for quitting the application.
     */
    public MainMenu(Stage stage, EventHandler<MouseEvent> onStartGame, EventHandler<MouseEvent> onEndlessMode, EventHandler<MouseEvent> onSettings, EventHandler<MouseEvent> onQuit) {
        // Bind the preferred size to the stage's current dimensions
        this.prefWidthProperty().bind(stage.widthProperty());
        this.prefHeightProperty().bind(stage.heightProperty());
    
        // Apply background image
        BackgroundUtil.setBackgroundImage(this);
    
        // Create layout for content
        VBox contentBox = createContentBox(onStartGame, onEndlessMode, onSettings, onQuit);
    
        // Add VBox to center of StackPane
        this.getChildren().add(contentBox);
        StackPane.setAlignment(contentBox, Pos.CENTER);
    
        // Listen for stage resize events to ensure proper background adjustments
        stage.widthProperty().addListener((observable, oldValue, newValue) -> BackgroundUtil.setBackgroundImage(this));
        stage.heightProperty().addListener((observable, oldValue, newValue) -> BackgroundUtil.setBackgroundImage(this));
    }

    private VBox createContentBox(EventHandler<MouseEvent> onStartGame, EventHandler<MouseEvent> onEndlessMode, EventHandler<MouseEvent> onSettings, EventHandler<MouseEvent> onQuit) {
        VBox contentBox = new VBox(Constant.GAME_START_BUTTON_SPACING);
        contentBox.setAlignment(Pos.CENTER);

        contentBox.setPrefWidth(1300);
        contentBox.setPrefHeight(750);

        // Add title and buttons to the layout
        contentBox.getChildren().addAll(
            createTitle(),
            createButton("Start Game", onStartGame),
            createButton("Endless Mode", onEndlessMode), // New Endless Mode button
            createButton("Settings", onSettings),
            createButton("Quit", onQuit)
        );

        return contentBox;
    }

    private Text createTitle() {
        Text title = new Text("Sky Battle");
        title.setStyle(Constant.TITLE_STYLE);
        return title;
    }

    private StackPane createButton(String text, EventHandler<MouseEvent> eventHandler) {
        // Create a StackPane to hold the image and the text
        StackPane buttonPane = new StackPane();
        buttonPane.setAlignment(Pos.CENTER);

        // Set background image for the button
        Image buttonImage = new Image(getClass().getResource(Constant.TEXTBOX_IMAGE_PATH).toExternalForm());
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(Constant.BUTTON_IMAGE_WIDTH); // Adjust width
        imageView.setFitHeight(Constant.BUTTON_IMAGE_HEIGHT); // Adjust height
        imageView.setPreserveRatio(true);

        // Create the text to be displayed
        Text buttonText = new Text(text);
        buttonText.setStyle(Constant.BUTTON_TEXT_STYLE); // Style from constants
        buttonText.setTranslateY(Constant.BUTTON_TEXT_TRANSLATE_Y); // Move text higher

        // Add the image and text to the StackPane
        buttonPane.getChildren().addAll(imageView, buttonText);

        // Add event handler for button click
        buttonPane.setOnMouseClicked(eventHandler);

        return buttonPane;
    }
}
