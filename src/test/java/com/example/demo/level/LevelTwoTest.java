package com.example.demo.level;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.model.Boss;
import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.Constant;

import javafx.scene.Group;

public class LevelTwoTest {

    private static final double SCREEN_WIDTH = 800;
    private static final double SCREEN_HEIGHT = 600;

    private LevelTwo levelTwo;

    @BeforeAll
    public static void initJavaFX() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @BeforeEach
    public void setUp() {
        levelTwo = new LevelTwo(SCREEN_HEIGHT, SCREEN_WIDTH);
    }

    @Test
    public void testLevelTwoInitialization() {
        assertNotNull(levelTwo, "LevelTwo instance should not be null.");
        assertEquals(SCREEN_WIDTH, levelTwo.getScreenWidth(), "Screen width should match initialization value.");
        assertEquals(SCREEN_HEIGHT, levelTwo.getScreenHeight(), "Screen height should match initialization value.");
    }

    @Test
    public void testInstantiateLevelView() {
        assertNotNull(levelTwo.instantiateLevelView(), "instantiateLevelView() should return a non-null LevelView.");
    }

    @Test
    public void testInitializeFriendlyUnits() {
        // Before calling, the root should not contain the user plane
        assertFalse(levelTwo.getRoot().getChildrenUnmodifiable().contains(levelTwo.getUser()),
                "User should not be in the scene root before calling initializeFriendlyUnits.");

        levelTwo.initializeFriendlyUnits();

        // After calling, the user's plane should be added to the scene's root
        assertTrue(levelTwo.getRoot().getChildrenUnmodifiable().contains(levelTwo.getUser()),
                "User plane should be in the scene root after calling initializeFriendlyUnits.");
    }

    @Test
    public void testSpawnEnemyUnits() throws Exception {
        Group root = levelTwo.getRoot();

        // Initially, no enemies (boss) should be present
        assertEquals(0, levelTwo.getCurrentNumberOfEnemies(), "Initially, there should be no enemies.");

        // Call spawnEnemyUnits
        levelTwo.spawnEnemyUnits();

        // After spawning, we expect one enemy: the boss
        assertEquals(1, levelTwo.getCurrentNumberOfEnemies(), "There should be one enemy (the boss) after spawning.");

        // Check if shieldImage is present in the root
        boolean shieldPresent = root.getChildrenUnmodifiable().stream()
                .anyMatch(node -> node.getClass().getSimpleName().equals("ShieldImage"));
        assertTrue(shieldPresent, "ShieldImage should be present after spawning the boss.");
    }

    @Test
    public void testGameOverWhenUserDestroyed() {
        // Start with user intact
        levelTwo.checkIfGameOver();
        assertFalse(levelTwo.isGameLost(), "Game should not be lost if user is not destroyed.");

        // Destroy the user
        levelTwo.getUser().destroy();
        levelTwo.checkIfGameOver();
        assertTrue(levelTwo.isGameLost(), "Game should be lost if user is destroyed.");
    }

    @Test
    public void testTransitionWhenBossDestroyed() throws Exception {
        // Spawn the boss
        levelTwo.spawnEnemyUnits();
        assertFalse(levelTwo.isTransitioned(), "Game should not be transitioned before boss is destroyed.");

        // Access the boss via reflection
        Field bossField = LevelTwo.class.getDeclaredField("boss");
        bossField.setAccessible(true);
        Boss boss = (Boss) bossField.get(levelTwo);

        // Destroy the boss
        boss.destroy();

        levelTwo.checkIfGameOver();
        assertTrue(levelTwo.isTransitioned(), "Game should transition to next level when boss is destroyed.");
    }

    @Test
    public void testShieldBehavior() throws Exception {
        // Spawn boss and shield
        levelTwo.spawnEnemyUnits();

        // Access the boss and shieldImage via reflection
        Field bossField = LevelTwo.class.getDeclaredField("boss");
        bossField.setAccessible(true);
        Boss boss = (Boss) bossField.get(levelTwo);

        Field shieldField = LevelTwo.class.getDeclaredField("shieldImage");
        shieldField.setAccessible(true);
        Object shieldImage = shieldField.get(levelTwo);

        // For testing shield, we need the boss to be shielded.
        // If Boss doesn't have a method to set shielded, consider adding one or reflect on that field as well.
        // Example (if Boss has a setShielded method):
        // boss.setShielded(true);

        // If no direct setter, and if shielded is a field, do reflection:
        try {
            Field shieldedField = Boss.class.getDeclaredField("shielded");
            shieldedField.setAccessible(true);
            shieldedField.setBoolean(boss, true);
        } catch (NoSuchFieldException nsfe) {
            fail("Boss class does not have a 'shielded' field. Add a method or field to test shield logic.");
        }

        // Update the level view after making the boss shielded
        levelTwo.updateLevelView();

        // Check if shield is visible after update (initially might be blinking or visible)
        boolean shieldVisible = levelTwo.getRoot().getChildrenUnmodifiable().stream()
                .anyMatch(node -> node.getClass().getSimpleName().equals("ShieldImage") && node.isVisible());
        assertTrue(shieldVisible, "Shield should become visible when boss is shielded.");
    }
}
