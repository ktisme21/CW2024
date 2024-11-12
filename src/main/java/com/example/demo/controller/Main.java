package com.example.demo.controller;

import com.example.demo.view.GameStartScreen;
import com.example.demo.view.SettingsPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private Controller myController;

	@Override
	public void start(Stage stage) {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);

		// Set up the GameStartScreen with an event to start the game
        GameStartScreen startScreen = new GameStartScreen(
            event -> {
            myController = new Controller(stage);
            try {
                myController.launchGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
		},
			event -> showSettings(stage),
			event -> showHowToPlay()
		);

		
		// Show the start screen initially
        Scene scene = new Scene(startScreen, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.show();
	}

	private void showSettings(Stage stage) {
        // Create SettingsPage with an event to go back to the main menu
        SettingsPage settingsPage = new SettingsPage(event -> showMainMenu(stage));

        // Set the scene to the settings page
        Scene settingsScene = new Scene(settingsPage, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(settingsScene);
    }

    private void showMainMenu(Stage stage) {
        // Recreate the main menu screen
        GameStartScreen startScreen = new GameStartScreen(
            event -> {
                myController = new Controller(stage);
                try {
                    myController.launchGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },
            event -> showSettings(stage),
            event -> showHowToPlay()
        );

        // Set the main menu scene
        Scene scene = new Scene(startScreen, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
    }

	private void showHowToPlay() {
        // Display a placeholder alert for settings (replace with actual settings page logic)
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("How To Play");
        alert.setHeaderText(null);
        alert.setContentText("How To Play page is under construction.");
        alert.showAndWait();
    }

	public static void main(String[] args) {
		launch();
	}
}