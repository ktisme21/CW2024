package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.view.GameStartScreen;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private Controller myController;

	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);

		// Set up the GameStartScreen with an event to start the game
        GameStartScreen startScreen = new GameStartScreen(event -> {
            myController = new Controller(stage);
            try {
                myController.launchGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
		},
			event -> showSettings(),
			event -> showHowToPlay()
		);

		
		// Show the start screen initially
        Scene scene = new Scene(startScreen, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.show();
	}

	private void showSettings() {
        // Display a placeholder alert for settings (replace with actual settings page logic)
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Settings");
        alert.setHeaderText(null);
        alert.setContentText("Settings page is under construction.");
        alert.showAndWait();
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