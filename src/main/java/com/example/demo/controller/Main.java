package com.example.demo.controller;

import com.example.demo.manager.MusicPlayer;
import com.example.demo.utilities.Constant;
import com.example.demo.view.GameStartScreen;
import com.example.demo.view.SettingsPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller myController;

    @Override
    public void start(Stage stage) {
        setupStage(stage);
        showMainMenu(stage);
        MusicPlayer.startMusic();
    }

    private void setupStage(Stage stage) {
        stage.setTitle(Constant.TITLE);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setWidth(Constant.SCREEN_WIDTH);
        stage.setHeight(Constant.SCREEN_HEIGHT);
    }

    public void showMainMenu(Stage stage) {
        GameStartScreen startScreen = new GameStartScreen(
            stage,
            event -> launchGame(stage),
            event -> showSettings(stage),
            event -> showHowToPlay()
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
        Stage settingsStage = createPopupStage("Settings", ownerStage);
        SettingsPage settingsPage = new SettingsPage(settingsStage, event -> settingsStage.close());
        Scene settingsScene = new Scene(settingsPage, 500, 400);
        settingsStage.setScene(settingsScene);
        settingsStage.showAndWait();
    }

    private void showHowToPlay() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("How To Play");
        alert.setHeaderText(null);
        alert.setContentText("How To Play page is under construction.");
        alert.showAndWait();
    }

    private Stage createPopupStage(String title, Stage ownerStage) {
        Stage popupStage = new Stage();
        popupStage.setTitle(title);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(ownerStage);
        return popupStage;
    }

    public static void main(String[] args) {
        launch();
    }
}
