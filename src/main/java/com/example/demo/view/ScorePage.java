package com.example.demo.view;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ScorePage extends StackPane {

    // Declare reusable style names
    private static final String TITLE_STYLE = "-fx-font-size: 24px; -fx-font-weight: bold;";
    private static final String TEXT_STYLE = "-fx-font-size: 18px;";
    private static final String BUTTON_STYLE = "-fx-font-size: 18px;";

    public ScorePage(Stage stage, EventHandler<MouseEvent> onBackToMain) {
        // Set the background image
        BackgroundUtil.setBackgroundImage(this);

        // Set the preferred size to match the stage dimensions
        this.setPrefSize(stage.getWidth(), stage.getHeight());

        // Create a VBox to hold the page content
        VBox contentBox = new VBox(20); // 20px spacing between elements
        contentBox.setAlignment(Pos.CENTER);

        // Title text
        Text title = new Text("Rankings");
        title.setStyle(TITLE_STYLE);

        // Placeholder score text
        Text scoreText = new Text("Your Score:");
        scoreText.setStyle(TEXT_STYLE);

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle(BUTTON_STYLE);
        backButton.setOnMouseClicked(onBackToMain); // Back button action

        // Add components to the VBox
        contentBox.getChildren().addAll(title, scoreText, backButton);

        // Add the VBox to the StackPane
        this.getChildren().add(backButton);
    }
}
