package com.example.demo.controller;

import com.example.demo.view.GameStartScreen;
import com.example.demo.view.ScorePage;
import com.example.demo.view.SettingsPage;
import com.example.demo.MusicPlayer;
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
        stage.setFullScreenExitHint(""); // Removes the exit hint message
        stage.setFullScreenExitKeyCombination(null); // Disables the exit key combination (e.g., ESC)

        // Set fixed size
        stage.setWidth(SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);
        
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

    public void showMainMenu(Stage stage) {
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

    private void showSettings(Stage stage) {
        SettingsPage settingsPage = new SettingsPage(stage, event -> showMainMenu(stage));
        Scene settingsScene = new Scene(settingsPage, stage.getWidth(), stage.getHeight());
        stage.setScene(settingsScene);
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
        // Create ScorePage and provide the back-to-main-menu action
        ScorePage scorePage = new ScorePage(stage, event -> showMainMenu(stage));

        // Set the scene to the ScorePage
        Scene scoreScene = new Scene(scorePage, stage.getWidth(), stage.getHeight());
        stage.setScene(scoreScene);
    }


    @Override
    public void stop() {
        // Stop music when the application closes
        MusicPlayer.stopMusic();
    }

    public static void main(String[] args) {
        launch();
    }
}
