package com.example.demo.view;

import com.example.demo.manager.LeaderboardManager;
import com.example.demo.testutils.JavaFXTestUtils;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaderBoardTest {

    private LeaderboardManager leaderboardManager;
    private Stage primaryStage;

    @BeforeAll
    public static void initJavaFX() {
        JavaFXTestUtils.initializeJavaFX(); // Ensure JavaFX is initialized
    }

    @BeforeEach
    void setUp() {
        // Initialize the leaderboard manager and mock data
        leaderboardManager = new LeaderboardManager();
        leaderboardManager.addEntry("Player1", 5000);
        leaderboardManager.addEntry("Player2", 3000);
        leaderboardManager.addEntry("Player3", 10000);

        // Create a new stage for testing
        primaryStage = new Stage();
    }

    @Test
    void testLeaderboardConstruction() {
        LeaderBoard leaderBoard = new LeaderBoard(
                primaryStage,
                leaderboardManager,
                "PlayerTest",
                7000,
                event -> {}, // Mock back-to-main handler
                event -> {}  // Mock restart handler
        );

        // Assert that the leaderboard scene is set
        Scene scene = primaryStage.getScene();
        assertNotNull(scene, "Leaderboard scene should be set.");

        // Assert that the scene root contains the VBox
        assertTrue(scene.getRoot() instanceof VBox, "Scene root should be a VBox.");

        VBox root = (VBox) scene.getRoot();

        // Assert the leaderboard contains the "Your Score" label
        Label currentScoreLabel = (Label) root.getChildren().get(0);
        assertEquals("Your Score:", currentScoreLabel.getText(), "Current score title should be 'Your Score:'.");

        // Assert the leaderboard box is created and populated
        StackPane leaderboardBox = (StackPane) root.getChildren().get(2);
        assertNotNull(leaderboardBox, "Leaderboard box should not be null.");
    }

    @Test
    void testLeaderboardContentPopulation() {
        LeaderBoard leaderBoard = new LeaderBoard(
                primaryStage,
                leaderboardManager,
                "PlayerTest",
                7000,
                event -> {}, // Mock back-to-main handler
                event -> {}  // Mock restart handler
        );

        StackPane leaderboardBox = (StackPane) primaryStage.getScene().getRoot().lookup(".stack-pane");

        assertNotNull(leaderboardBox, "Leaderboard box should exist.");

        VBox leaderboardContent = (VBox) leaderboardBox.getChildren().get(1);
        List<?> entries = leaderboardContent.getChildren();

        // Validate the leaderboard content
        assertEquals(4, entries.size(), "Leaderboard should display the correct number of entries including title.");

        // Validate individual entries
        Label entryLabel = (Label) entries.get(1);
        assertTrue(entryLabel.getText().contains("Player1"), "Leaderboard should display Player1's name.");
    }

    @Test
    void testButtonsFunctionality() {
        // Track button click actions using a flag
        final boolean[] backToMainClicked = {false};
        final boolean[] restartClicked = {false};

        // Simulate button clicks
        StackPane restartButton = (StackPane) primaryStage.getScene().getRoot().lookup("#restartButton");
        StackPane backButton = (StackPane) primaryStage.getScene().getRoot().lookup("#backButton");

        assertNotNull(restartButton, "Restart button should exist.");
        assertNotNull(backButton, "Back-to-main button should exist.");

        // Simulate clicks
        Platform.runLater(() -> restartButton.fireEvent(new javafx.scene.input.MouseEvent(
                javafx.scene.input.MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY,
                1, true, true, true, true,
                true, true, true, true, true, true, null
        )));
        Platform.runLater(() -> backButton.fireEvent(new javafx.scene.input.MouseEvent(
                javafx.scene.input.MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY,
                1, true, true, true, true,
                true, true, true, true, true, true, null
        )));

        // Validate button clicks
        assertTrue(backToMainClicked[0], "Back-to-main button click should trigger the event.");
        assertTrue(restartClicked[0], "Restart button click should trigger the event.");
    }
}
