// package com.example.demo.view;

// import com.example.demo.testutils.JavaFXTestUtils;
// import javafx.application.Platform;
// import javafx.scene.Group;
// import javafx.scene.control.Label;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.HBox;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.*;

// class LevelViewTest {

//     private LevelView levelView;
//     private Group root;

//     @BeforeAll
//     public static void initJavaFX() {
//         JavaFXTestUtils.initializeJavaFX(); // Ensure JavaFX is initialized
//     }

//     @BeforeEach
//     void setUp() {
//         root = new Group();
//         levelView = new LevelView(root, 5); // Initialize with 5 hearts
//     }

//     @Test
//     void testShowShield() {
//         Platform.runLater(() -> {
//             levelView.showShield();
//             ImageView shieldImage = (ImageView) root.getChildren().stream()
//                 .filter(node -> node instanceof ImageView && node.getStyleClass().contains("shield-image"))
//                 .findFirst()
//                 .orElse(null);
//             assertNotNull(shieldImage, "Shield image should exist in the root.");
//             assertTrue(shieldImage.isVisible(), "Shield image should be visible.");
//         });
//     }

//     @Test
//     void testHideShield() {
//         Platform.runLater(() -> {
//             levelView.hideShield();
//             ImageView shieldImage = (ImageView) root.getChildren().stream()
//                 .filter(node -> node instanceof ImageView && node.getStyleClass().contains("shield-image"))
//                 .findFirst()
//                 .orElse(null);
//             assertNotNull(shieldImage, "Shield image should exist in the root.");
//             assertFalse(shieldImage.isVisible(), "Shield image should not be visible.");
//         });
//     }

//     @Test
//     void testShowHeartDisplay() {
//         Platform.runLater(() -> {
//             levelView.showHeartDisplay();
//             HBox heartDisplay = (HBox) root.getChildren().stream()
//                 .filter(node -> node instanceof HBox)
//                 .findFirst()
//                 .orElse(null);
//             assertNotNull(heartDisplay, "Heart display should be added to the root.");
//             assertEquals(5, heartDisplay.getChildren().size(), "Heart display should have 5 hearts.");
//         });
//     }

//     @Test
//     void testRemoveHearts() {
//         Platform.runLater(() -> {
//             levelView.showHeartDisplay();
//             levelView.removeHearts(3); // Remove hearts, leaving 3 remaining
//             HBox heartDisplay = (HBox) root.getChildren().stream()
//                 .filter(node -> node instanceof HBox)
//                 .findFirst()
//                 .orElse(null);
//             assertNotNull(heartDisplay, "Heart display should exist in the root.");
//             assertEquals(3, heartDisplay.getChildren().size(), "Heart display should show 3 hearts.");
//         });
//     }

//     @Test
//     void testShowWinImage() {
//         Platform.runLater(() -> {
//             levelView.showWinImage();
//             ImageView winImage = (ImageView) root.getChildren().stream()
//                 .filter(node -> node instanceof ImageView && node.getStyleClass().contains("win-image"))
//                 .findFirst()
//                 .orElse(null);
//             assertNotNull(winImage, "Win image should exist in the root.");
//             assertTrue(winImage.isVisible(), "Win image should be visible.");
//         });
//     }

//     @Test
//     void testShowGameOverImage() {
//         Platform.runLater(() -> {
//             levelView.showGameOverImage();
//             ImageView gameOverImage = (ImageView) root.getChildren().stream()
//                 .filter(node -> node instanceof ImageView && node.getStyleClass().contains("game-over-image"))
//                 .findFirst()
//                 .orElse(null);
//             assertNotNull(gameOverImage, "Game over image should exist in the root.");
//             assertTrue(gameOverImage.isVisible(), "Game over image should be visible.");
//         });
//     }

//     @Test
//     void testShowHealth() {
//         Platform.runLater(() -> {
//             levelView.showHealth("Health: 3");
//             Label healthLabel = (Label) root.getChildren().stream()
//                 .filter(node -> node instanceof Label)
//                 .findFirst()
//                 .orElse(null);
//             assertNotNull(healthLabel, "Health display label should exist in the root.");
//             assertEquals("Health: 3", healthLabel.getText(), "Health display text should match.");
//         });
//     }
// }
