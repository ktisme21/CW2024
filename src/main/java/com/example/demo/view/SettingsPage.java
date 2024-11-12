package com.example.demo.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SettingsPage extends VBox {

    public SettingsPage(EventHandler<MouseEvent> onBackToMain) {
        // Title text for settings page
        Text title = new Text("Settings");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-size: 18px;");
        backButton.setOnMouseClicked(onBackToMain);  // Set the action for going back to main screen

        // Layout setup
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(title, backButton);
    }
}
