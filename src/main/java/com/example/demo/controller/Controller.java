package com.example.demo.controller;

import com.example.demo.LevelChangeListener;
import com.example.demo.manager.LevelManager;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;

/**
 * The {@code Controller} class is responsible for managing the flow between different levels of the game.
 * It implements {@link LevelChangeListener} to handle transitions between levels.
 */
public class Controller implements LevelChangeListener {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";
	private final Stage stage;

	/**
	 * Constructs a new {@code Controller} instance.
	 *
	 * @param stage The primary {@link Stage} for the game.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game and initializes the first level.
	 *
	 * @throws Exception If an error occurs during the level initialization.
	 */
	public void launchGame() throws Exception {
		stage.show();
		goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	/**
	 * Navigates to the specified level by dynamically loading the level class.
	 *
	 * @param className The fully qualified class name of the level to navigate to.
	 * @throws Exception If an error occurs during the level loading or initialization.
	 */
	private void goToLevel(String className) throws Exception {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelManager myLevel = (LevelManager) constructor.newInstance(stage.getHeight(), stage.getWidth());
		myLevel.setLevelChangeListener(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}

	/**
	 * Handles level changes and navigates to the specified level.
	 *
	 * @param levelName The name of the level to navigate to.
	 */
	@Override
	public void onLevelChange(String levelName) {
		try {
			goToLevel(levelName);
		} catch (Exception e) {
			showAlert(e);
		}
	}

	/**
	 * Displays an alert dialog to show error messages.
	 *
	 * @param e The exception that occurred.
	 */
	private void showAlert(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Error: " + e.getMessage());
		alert.show();
	}
}
