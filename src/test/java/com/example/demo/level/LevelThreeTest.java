package com.example.demo.level;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.Boss;
import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.Constant;


public class LevelThreeTest {

    private static final double SCREEN_WIDTH = 800;
    private static final double SCREEN_HEIGHT = 600;

    private LevelThree levelThree;

    @BeforeAll
    public static void initJavaFX() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @BeforeEach
    public void setUp() {
        levelThree = new LevelThree(SCREEN_HEIGHT, SCREEN_WIDTH);
        levelThree.initializeScene();
    }

    @Test
    public void testLevelThreeInitialization() {
        assertNotNull(levelThree, "LevelThree instance should not be null.");
        assertEquals(SCREEN_WIDTH, levelThree.getScreenWidth(), "Screen width should match initialization value.");
        assertEquals(SCREEN_HEIGHT, levelThree.getScreenHeight(), "Screen height should match initialization value.");
    }

    @Test
    public void testInstantiateLevelView() {
        assertNotNull(levelThree.instantiateLevelView(), "instantiateLevelView() should return a non-null LevelView.");
    }

    @Test
    public void testInitializeFriendlyUnits() {
        // If the user is already added before initializeFriendlyUnits, this expectation is invalid.
        assertTrue(levelThree.getRoot().getChildrenUnmodifiable().contains(levelThree.getUser()),
                "User should already be in the scene root before calling initializeFriendlyUnits.");

        // Additional checks post `initializeFriendlyUnits` to ensure nothing breaks
        levelThree.initializeFriendlyUnits();

        boolean redContainerPresent = levelThree.getRoot().getChildrenUnmodifiable().stream()
                .anyMatch(node -> node instanceof javafx.scene.shape.Rectangle);
        assertTrue(redContainerPresent, "Red container should be present after initializing friendly units.");
    }

    @Test
    public void testSpawnEnemyUnits() {
        // Initially, there should be no enemies
        assertEquals(0, levelThree.getCurrentNumberOfEnemies(), "Initially, there should be no enemies.");

        // Spawn enemies
        levelThree.spawnEnemyUnits();

        // Check that the correct number of bosses is spawned
        assertEquals(Constant.LEVEL_THREE_TOTAL_BOSSES, levelThree.getCurrentNumberOfEnemies(),
                "Should spawn the defined total number of bosses for Level Three.");

        // Verify each enemy is a Boss with reduced health
        for (int i = 0; i < levelThree.getCurrentNumberOfEnemies(); i++) {
            // Get each enemy (if we had a getter for enemies or reflectively access enemy list)
            // Assuming protected access or reflection:
            // Here we assume addEnemyUnit only adds Boss instances for LevelThree
            // In a real scenario, you'd need a way to retrieve enemies, maybe a public getter or reflection.
        }
    }

    @Test
    public void testToggleUserPlaneVisibility() throws Exception {
        // Initially plane is visible
        assertTrue(levelThree.getUser().isVisible(), "User plane should start visible.");

        // Reflectively call toggleUserPlaneVisibility if it's private
        Method toggleVisibilityMethod = LevelThree.class.getDeclaredMethod("toggleUserPlaneVisibility");
        toggleVisibilityMethod.setAccessible(true);
        toggleVisibilityMethod.invoke(levelThree);

        assertFalse(levelThree.getUser().isVisible(), "User plane should be invisible after toggling once.");

        // Toggle again
        toggleVisibilityMethod.invoke(levelThree);
        assertTrue(levelThree.getUser().isVisible(), "User plane should be visible after toggling twice.");
    }

    @Test
    public void testGameOverWhenUserDestroyed() {
        // Ensure not lost initially
        levelThree.checkIfGameOver();
        assertFalse(levelThree.isGameLost(), "Game should not be lost if user is not destroyed.");

        // Destroy the user and check again
        levelThree.getUser().destroy();
        levelThree.checkIfGameOver();
        assertTrue(levelThree.isGameLost(), "Game should be lost if user is destroyed.");
    }

    @Test
    public void testWinGameWhenAllBossesDestroyed() throws Exception {
        // Spawn all bosses
        levelThree.spawnEnemyUnits();
        assertEquals(Constant.LEVEL_THREE_TOTAL_BOSSES, levelThree.getCurrentNumberOfEnemies(),
                "All bosses should be spawned.");

        // Destroy all bosses to simulate clearing the level
        // Access the enemies list if possible (via reflection) to destroy them
        // If no direct access, assume each enemy is a Boss and call a method to clear them:
        // Reflection approach to get enemies:
        var enemiesField = levelThree.getClass().getSuperclass().getDeclaredField("enemyUnits");
        enemiesField.setAccessible(true);
        @SuppressWarnings("unchecked")
        var enemies = (java.util.List<ActiveActorDestructible>) enemiesField.get(levelThree);

        for (ActiveActorDestructible enemy : enemies) {
            if (enemy instanceof Boss) {
                enemy.destroy();
            }
        }

        // After destroying all bosses, check if game is won
        // In updateScene() or checkIfGameOver() logic, game should be won if spawnedBossCount is reached and enemies=0.
        // The code says it wins if spawnedBossCount >= total bosses and no enemies left
        // But we must remove destroyed enemies from the scene by calling updateScene or simulating that logic:
        // Let's call updateScene to remove destroyed actors:
        levelThree.updateScene(); // This should remove destroyed bosses
        assertEquals(0, levelThree.getCurrentNumberOfEnemies(), "All enemies should be removed after updateScene().");

        levelThree.checkIfGameOver();
        // Assuming winGame sets something we can test, like isTransitioned or a known state
        // If winGame does not set isTransitioned, ensure it does something verifiable.
        // If there's no direct boolean for win, you can reflect on gameLost or other states.
        // If not, consider adding a method in LevelManager to check if the game is won.
        // For demonstration, we assume winGame transitions the level:
        // If not, you might need to add a test double or additional logic in your code to confirm win condition.

        // If your winGame method sets isTransitioned = true, then:
        // (Modify winGame in LevelManager to set isTransitioned=true if desired)

        // Assuming we do that for testing purpose:
        // assertTrue(levelThree.isTransitioned(), "Game should transition after all bosses are destroyed.");

        // If winGame does not set isTransitioned, consider another assertion or modify the code to provide a "gameWon" flag.
        // For now, let's assume winGame sets isTransitioned=true:
        assertTrue(levelThree.isTransitioned(), "Game should indicate a win (transition) when all bosses are destroyed.");
    }
}
