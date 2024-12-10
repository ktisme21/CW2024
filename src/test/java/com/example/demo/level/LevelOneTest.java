package com.example.demo.level;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.Constant;
import com.example.demo.level.LevelOne;

public class LevelOneTest {

    private static final double SCREEN_WIDTH = 800;
    private static final double SCREEN_HEIGHT = 600;

    private static LevelOne levelOne;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
        levelOne = new LevelOne(SCREEN_HEIGHT, SCREEN_WIDTH);
    }

    @Test
    public void testLevelOneInitialization() {
        assertNotNull(levelOne, "LevelOne instance should not be null.");
        assertEquals(SCREEN_WIDTH, levelOne.getScreenWidth(), "Screen width should match initialization value.");
        assertEquals(SCREEN_HEIGHT, levelOne.getScreenHeight(), "Screen height should match initialization value.");
    }

    @Test
    public void testInstantiateLevelView() {
        // The method should return a non-null LevelView
        assertNotNull(levelOne.instantiateLevelView(), "instantiateLevelView() should return a non-null LevelView.");
    }

    @Test
    public void testInitializeFriendlyUnits() {
        // Before calling, the root should not contain the user plane
        assertFalse(levelOne.getRoot().getChildrenUnmodifiable().contains(levelOne.getUser()),
                "User should not be in the scene root before calling initializeFriendlyUnits.");

        levelOne.initializeFriendlyUnits();

        // After calling, the user's plane should be added to the scene's root
        assertTrue(levelOne.getRoot().getChildrenUnmodifiable().contains(levelOne.getUser()),
                "User plane should be in the scene root after calling initializeFriendlyUnits.");
    }

    @Test
    public void testSpawnEnemyUnits() {
        // Initially, there should be no enemies
        assertEquals(0, levelOne.getCurrentNumberOfEnemies(), "Initially, there should be no enemies.");

        // Call spawnEnemyUnits multiple times
        for (int i = 0; i < 100; i++) {
            levelOne.spawnEnemyUnits();
        }

        // Even though spawning is probabilistic, we should not exceed the defined total number of enemies
        assertTrue(levelOne.getCurrentNumberOfEnemies() <= Constant.LEVEL_ONE_TOTAL_ENEMIES,
                "Number of enemies should never exceed the defined total for Level One.");
    }

    @Test
    public void testCheckIfGameOverConditions() {
        // If the user is not destroyed and has not reached kill target, the game should not transition or lose
        levelOne.checkIfGameOver();
        assertFalse(levelOne.isGameLost(), "Game should not be lost if user is not destroyed.");
        assertFalse(levelOne.isTransitioned(), "Game should not transition if kill target not reached.");

        // Simulate user destruction
        levelOne.getUser().destroy();
        levelOne.checkIfGameOver();
        assertTrue(levelOne.isGameLost(), "Game should be lost if user is destroyed.");

        // Reset state for next test
        // Re-instantiate levelOne if necessary or add methods in LevelManager to reset state
        // For simplicity, let's re-create it:
        levelOne = new LevelOne(SCREEN_HEIGHT, SCREEN_WIDTH);

        // Simulate reaching kill target
        // If no setNumberOfKills method exists, use incrementKillCount:
        for (int i = 0; i < Constant.LEVEL_ONE_KILLS_TO_ADVANCE; i++) {
            levelOne.getUser().incrementKillCount();
        }
        levelOne.checkIfGameOver();
        assertTrue(levelOne.isTransitioned(), "Game should transition if user reaches kill target.");
    }
}
