package com.example.demo.controller;

import com.example.demo.view.GameStartScreen;
import com.example.demo.view.ScorePage;
import com.example.demo.view.SettingsPage;
import com.example.demo.services.MusicPlayer;
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
	public void start(Stage stage) throws Exception {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setFullScreenExitHint(""); // Removes the exit hint message
        stage.setFullScreenExitKeyCombination(null); // Disables the exit key combination (e.g., ESC)

		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);

		// Alternatively, set min and max to the same value to enforce fixed size
        stage.setMinWidth(SCREEN_WIDTH);
        stage.setMaxWidth(SCREEN_WIDTH);
        stage.setMinHeight(SCREEN_HEIGHT);
        stage.setMaxHeight(SCREEN_HEIGHT);

		// Show the main menu
        showMainMenu(stage);
        
        // Start playing background music
        MusicPlayer.startMusic();
    }

	public void showMainMenu(Stage stage){
		GameStartScreen startScreen = new GameStartScreen(stage,
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
        Scene scene = new Scene(startScreen, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
	}

	private void showSettings(Stage ownerStage) {
        // Create a new Stage for the pop-up
        Stage settingsStage = new Stage();
        settingsStage.setTitle("Settings");
        
        // Set the modality to block interaction with other windows
        settingsStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        settingsStage.initOwner(ownerStage); // Set the owner to the main stage
    
        // Create the SettingsPage content
        SettingsPage settingsPage = new SettingsPage(settingsStage, event -> settingsStage.close());
    
        // Set the scene for the pop-up stage
        Scene settingsScene = new Scene(settingsPage, 500, 400); // Adjust the size as needed
        settingsStage.setScene(settingsScene);
    
        // Show the pop-up window
        settingsStage.showAndWait();
    }
    
	

	private void showHowToPlay() {
        // Display a placeholder alert for settings (replace with actual settings page logic)
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("How To Play");
        alert.setHeaderText(null);
        alert.setContentText("How To Play page is under construction.");
        alert.showAndWait();
    }

	public void showScorePage(Stage stage) {
        // Create a new ScorePage instance as a popup
        ScorePage scorePage = new ScorePage(stage, event -> showMainMenu(stage));
        // Open the ScorePage popup
        scorePage.show();
    }
    
	public static void main(String[] args) {
		launch();
	}
}