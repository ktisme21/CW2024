package com.example.demo.controller;

import com.example.demo.level.EndlessMode;
import com.example.demo.manager.MusicPlayer;
import com.example.demo.utilities.Constant;
import com.example.demo.view.MainMenu;
import com.example.demo.view.SettingsPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main application class for the demo project.
 * Handles the initialization and navigation between different screens.
 */
public class Main extends Application {

    private Controller myController;

    /**
     * Starts the JavaFX application.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        setupStage(stage);
        showMainMenu(stage);
        MusicPlayer.startMusic();
    }

    /**
     * Configures the main stage settings.
     *
     * @param stage The primary stage.
     */
    private void setupStage(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED); // Remove the top panel
        stage.setTitle(Constant.TITLE);
        stage.setResizable(false);
        stage.setWidth(1300); // Fixed width
        stage.setHeight(750); // Fixed height
    }

    /**
     * Displays the main menu screen.
     *
     * @param stage The primary stage.
     */
    public void showMainMenu(Stage stage) {
        MainMenu startScreen = new MainMenu(
            stage,
            event -> launchGame(stage),
            event -> endlessMode(stage),
            event -> showSettings(stage),
            event -> stage.close()
        );

        Scene scene = new Scene(startScreen, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the game by initializing the game controller.
     *
     * @param stage The primary stage.
     */
    private void launchGame(Stage stage) {
        myController = new Controller(stage);
        try {
            myController.launchGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the endless mode of the game.
     *
     * @param stage The primary stage.
     */
    private void endlessMode(Stage stage) {
        EndlessMode endlessMode = new EndlessMode(Constant.SCREEN_HEIGHT, Constant.SCREEN_WIDTH);
        Scene endlessModeScene = endlessMode.initializeScene();

        // Set the new scene on the stage
        stage.setScene(endlessModeScene);

        // Start the endless mode gameplay
        endlessMode.startGame();
        stage.show();
    }

    /**
     * Displays the settings page as a full-screen replacement.
     *
     * @param stage The primary stage.
     */
    private void showSettings(Stage stage) {
        // Create the SettingsPage and set it as the new scene
        SettingsPage settingsPage = new SettingsPage(stage, () -> showMainMenu(stage));

        // Display the settings page by setting it as the scene
        settingsPage.display();
    }

    // For FXML
    // private void showSettings(Stage stage) {
    //     try {
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/com/example/demo/fxml/SettingsPage.fxml"));
    //         Scene settingsScene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());

    //         SettingsController controller = loader.getController();
    //         controller.setOnBackAction(() -> showMainMenu(stage));

    //         stage.setScene(settingsScene);
    //         stage.show();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * Main entry point for the Java application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}
