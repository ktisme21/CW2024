package com.example.demo.controller;

import com.example.demo.manager.MusicPlayer;
import com.example.demo.utilities.Constant;
import com.example.demo.view.MainMenu;
import com.example.demo.view.SettingsPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private Controller myController;

    @Override
    public void start(Stage stage) {
        setupStage(stage);
        showMainMenu(stage);
        MusicPlayer.startMusic();
    }

    private void setupStage(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED); // Remove the top panel
        stage.setTitle(Constant.TITLE);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setWidth(Constant.SCREEN_WIDTH);
        stage.setHeight(Constant.SCREEN_HEIGHT);
    }



    public void showMainMenu(Stage stage) {
        MainMenu startScreen = new MainMenu(
            stage,
            event -> launchGame(stage),
            event -> showSettings(stage),
            event -> stage.close()
        );

        Scene scene = new Scene(startScreen, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void launchGame(Stage stage) {
        myController = new Controller(stage);
        try {
            myController.launchGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSettings(Stage ownerStage) {
        // Create a new stage for the settings popup
        Stage settingsStage = new Stage();
        settingsStage.initModality(Modality.APPLICATION_MODAL); // Make it a modal popup
        settingsStage.initOwner(ownerStage); // Set the owner of the popup
        settingsStage.initStyle(StageStyle.UNDECORATED); // Remove the top panel (title bar)

        // Create the SettingsPage and set it as the root of the scene
        SettingsPage settingsPage = new SettingsPage(settingsStage, event -> settingsStage.close());
        Scene settingsScene = new Scene(settingsPage, ownerStage.getWidth() * 0.5, ownerStage.getHeight() * 0.5);

        // Set the scene and show the stage
        settingsStage.setScene(settingsScene);
        settingsStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
