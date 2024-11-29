package com.example.demo.view;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.example.demo.utilities.Constant;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameStartScreen extends StackPane {
    public GameStartScreen(Stage stage, EventHandler<MouseEvent> onStartGame, EventHandler<MouseEvent> onSettings, EventHandler<MouseEvent> onHowToPlay) {
        // Set preferred size to match the stage dimensions
        this.setPrefSize(stage.getWidth(), stage.getHeight());

        // Apply background image
        BackgroundUtil.setBackgroundImage(this);

        // Create layout for content
        VBox contentBox = createContentBox(onStartGame, onSettings, onHowToPlay);

        // Add VBox to center of StackPane
        this.getChildren().add(contentBox);
    }

    private VBox createContentBox(EventHandler<MouseEvent> onStartGame, EventHandler<MouseEvent> onSettings, EventHandler<MouseEvent> onHowToPlay) {
        VBox contentBox = new VBox(Constant.GAME_START_BUTTON_SPACING);
        contentBox.setAlignment(Pos.CENTER);

        // Add title and buttons to the layout
        contentBox.getChildren().addAll(
            createTitle(),
            createButton("Start Game", onStartGame),
            createButton("Settings", onSettings),
            createButton("How To Play", onHowToPlay)
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
