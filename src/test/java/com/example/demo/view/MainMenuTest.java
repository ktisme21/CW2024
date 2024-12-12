package com.example.demo.view;

import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.BackgroundUtil;
import com.example.demo.utilities.Constant;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest {

    private Stage stage;
    private MainMenu mainMenu;

    @BeforeAll
    public static void initJavaFX() throws InterruptedException {
        Platform.startup(() -> {});
        Thread.sleep(500); // Ensure JavaFX is initialized properly
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            stage = new Stage();
            mainMenu = new MainMenu(
                    stage,
                    event -> System.out.println("Start Game Clicked"),
                    event -> System.out.println("Endless Mode Clicked"),
                    event -> System.out.println("Settings Clicked"),
                    event -> System.out.println("Quit Clicked")
            );
        });
    }

    @Test
    void testMainMenuInitialization() throws Exception {
        Platform.runLater(() -> {
            Scene scene = new Scene(mainMenu, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
            stage.setScene(scene);

            StackPane root = (StackPane) scene.getRoot();
            assertNotNull(root, "Main menu root should not be null.");

            VBox contentBox = (VBox) root.getChildren().get(0);
            assertNotNull(contentBox, "Content box should not be null.");
            assertEquals(5, contentBox.getChildren().size(), "Content box should have 5 children.");
        });

        // Wait for the JavaFX thread to execute the code
        Thread.sleep(500);
    }


    @Test
    void testButtonEventHandlers() {
        Platform.runLater(() -> {
            VBox contentBox = (VBox) mainMenu.getChildren().get(0);

            // Check if all buttons exist and are clickable
            StackPane startGameButton = (StackPane) contentBox.getChildren().get(1);
            StackPane endlessModeButton = (StackPane) contentBox.getChildren().get(2);
            StackPane settingsButton = (StackPane) contentBox.getChildren().get(3);
            StackPane quitButton = (StackPane) contentBox.getChildren().get(4);

            // Verify button click functionality
            assertDoesNotThrow(() -> startGameButton.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 0, false, false, false, false, false, false, false, false, false, false, null)), "Start Game button should respond to clicks.");
            assertDoesNotThrow(() -> endlessModeButton.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 0, false, false, false, false, false, false, false, false, false, false, null)), "Endless Mode button should respond to clicks.");
            assertDoesNotThrow(() -> settingsButton.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 0, false, false, false, false, false, false, false, false, false, false, null)), "Settings button should respond to clicks.");
            assertDoesNotThrow(() -> quitButton.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 0, false, false, false, false, false, false, false, false, false, false, null)), "Quit button should respond to clicks.");
        });
    }

    @Test
    void testBackgroundImageSet() {
        Platform.runLater(() -> {
            // Check if the background image is set
            BackgroundUtil.setBackgroundImage(mainMenu);
            assertNotNull(mainMenu.getBackground(), "Background image should be set for the main menu.");
        });
    }
}
