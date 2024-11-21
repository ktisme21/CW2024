package com.example.demo.controller;

import com.example.demo.LevelChangeListener;
import com.example.demo.level.LevelParent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Controller implements LevelChangeListener {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";
	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws Exception {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	private void goToLevel(String className) throws Exception {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
			myLevel.setLevelChangeListener(this);
			Scene scene = myLevel.initializeScene();
			stage.setScene(scene);
			myLevel.startGame();
	}

	@Override
    public void onLevelChange(String levelName) {
        try {
            goToLevel(levelName);
        } catch (Exception e) {
            showAlert(e);
        }
    }

    private void showAlert(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Error: " + e.getMessage());
        alert.show();
    }

}
