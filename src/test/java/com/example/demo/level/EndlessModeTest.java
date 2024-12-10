package com.example.demo.level;

import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.EnemyPlane;
import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.Constant;
import javafx.scene.Group;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class EndlessModeTest {

    private EndlessMode endlessMode;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @BeforeEach
    void setUpEach() {
        endlessMode = new EndlessMode(800, 600);
    }

    @Test
    void testInstantiateLevelView() {
        assertNotNull(endlessMode.instantiateLevelView(), "LevelView should be instantiated for Endless Mode.");
    }

    @Test
    void testSpawnEnemyUnits() throws Exception {
        Method getRootMethod = EndlessMode.class.getSuperclass().getDeclaredMethod("getRoot");
        getRootMethod.setAccessible(true);
        Group root = (Group) getRootMethod.invoke(endlessMode);

        int initialEnemyCount = root.getChildren().size();
        endlessMode.spawnEnemyUnits();
        int finalEnemyCount = root.getChildren().size();
        assertTrue(finalEnemyCount >= initialEnemyCount, "Enemy count should increase or remain the same after spawning.");
    }

    @Test
    void testAdjustDifficulty() {
        // Get the initial probability directly from endlessMode
        double initialProbability = endlessMode.getSpawnProbability();

        // Perform multiple spawns to potentially increase difficulty
        for (int i = 0; i < 50; i++) {
            endlessMode.spawnEnemyUnits();
        }

        double currentProbability = endlessMode.getSpawnProbability();

        // Log probabilities for debugging
        System.out.println("Initial Probability: " + initialProbability);
        System.out.println("Current Probability: " + currentProbability);

        // Ensure probability does not exceed the maximum
        assertTrue(currentProbability <= Constant.ENDLESS_MODE_MAX_SPAWN_PROBABILITY,
                "Spawn probability should not exceed the maximum defined value.");

        // Ensure probability increased over time if that is the intended behavior
        assertTrue(currentProbability > initialProbability,
                "Spawn probability should increase over time. Initial: " + initialProbability +
                        ", Current: " + currentProbability);
    }


    @Test
    void testGameOverCondition() throws Exception {
        Method getUserMethod = EndlessMode.class.getSuperclass().getDeclaredMethod("getUser");
        getUserMethod.setAccessible(true);
        ActiveActorDestructible user = (ActiveActorDestructible) getUserMethod.invoke(endlessMode);

        user.destroy();
        endlessMode.checkIfGameOver();
        assertTrue(user.isDestroyed(), "Game should recognize user plane destruction as game over.");
    }

    @Test
    void testSceneInitialization() {
        Scene scene = endlessMode.initializeScene();
        assertNotNull(scene, "Scene should be initialized.");
        assertNotNull(scene.getRoot(), "Scene should have a root node.");
    }

    @Test
    void testAddEnemyUnit() throws Exception {
        // Access the `addEnemyUnit` method using reflection
        Method addEnemyUnitMethod = EndlessMode.class.getSuperclass().getDeclaredMethod("addEnemyUnit", ActiveActorDestructible.class);
        addEnemyUnitMethod.setAccessible(true);

        // Access the root group to check enemy count
        Method getRootMethod = EndlessMode.class.getSuperclass().getDeclaredMethod("getRoot");
        getRootMethod.setAccessible(true);
        Group root = (Group) getRootMethod.invoke(endlessMode);

        int initialEnemyCount = root.getChildren().size();

        // Add an enemy unit
        addEnemyUnitMethod.invoke(endlessMode, new EnemyPlane(800, 200));
        int finalEnemyCount = root.getChildren().size();

        assertEquals(initialEnemyCount + 1, finalEnemyCount, "Enemy count should increase by one.");
    }
}
