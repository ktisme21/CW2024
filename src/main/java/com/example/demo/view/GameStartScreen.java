package com.example.demo.view;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameStartScreen extends StackPane {

    private static final double BUTTON_SPACING = 20;
    private static final String TITLE_STYLE = "-fx-font-size: 24px; -fx-font-weight: bold;";
    private static final String BUTTON_STYLE = "-fx-font-size: 18px;";

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
        VBox contentBox = new VBox(BUTTON_SPACING);
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
        Text title = new Text("Welcome to Sky Battle!");
        title.setStyle(TITLE_STYLE);
        return title;
    }

    private Button createButton(String text, EventHandler<MouseEvent> eventHandler) {
        Button button = new Button(text);
        button.setStyle(BUTTON_STYLE);
        button.setOnMouseClicked(eventHandler);
        return button;
    }
}
