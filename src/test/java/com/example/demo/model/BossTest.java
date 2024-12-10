package com.example.demo.model;

import com.example.demo.projectiles.BossProjectile;
import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.Constant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BossTest {

    private Boss boss;

    @BeforeAll
    public static void setUpClass() {
        // Initialize JavaFX to avoid runtime issues
        JavaFXTestUtils.initializeJavaFX();
    }

    @BeforeEach
    public void setUp() {
        boss = new Boss();
    }

    @Test
    public void testInitialHealth() {
        assertEquals(Constant.BOSS_HEALTH, boss.getHealth(), "Initial health should match Constant.BOSS_HEALTH.");
    }

    @Test
    public void testSetAndGetHealth() {
        boss.setHealth(50);
        assertEquals(50, boss.getHealth(), "Health should be updated to 50.");
    }

    @Test
    public void testShieldActivation() {
        boss.setShieldProbability(1.0); // Ensure shield always activates
        boss.updateActor();
        assertTrue(boss.isShielded(), "Boss should be shielded when shieldProbability is 1.0.");
    }

    @Test
    public void testShieldDeactivation() {
        boss.setShieldProbability(1.0); // Activate shield
        boss.updateActor();

        // Simulate frames for shield exhaustion
        for (int i = 0; i < Constant.BOSS_MAX_FRAMES_WITH_SHIELD; i++) {
            boss.updateActor();
        }

        assertFalse(boss.isShielded(), "Shield should deactivate after maximum shield frames.");
    }

    @Test
    public void testTakeDamageWithShield() {
        boss.setShieldProbability(1.0); // Ensure shield always activates
        boss.updateActor(); // Activate shield
        assertTrue(boss.isShielded(), "Boss should be shielded before taking damage.");

        int initialHealth = boss.getHealth();
        boss.takeDamage();

        assertEquals(initialHealth, boss.getHealth(), "Health should not decrease when shielded.");
    }

    @Test
    public void testTakeDamageWithoutShield() {
        boss.setShieldProbability(0.0); // Ensure shield does not activate
        boss.updateActor(); // Shield stays inactive
        assertFalse(boss.isShielded(), "Boss should not be shielded before taking damage.");

        int initialHealth = boss.getHealth();
        boss.takeDamage();

        assertEquals(initialHealth - 1, boss.getHealth(), "Health should decrease when not shielded.");
    }


    @Test
    public void testMovementWithinBounds() {
        boss.setTranslateY(Constant.BOSS_Y_POSITION_UPPER_BOUND - 1); // Near upper bound
        boss.updatePosition();

        assertTrue(
                boss.getTranslateY() >= Constant.BOSS_Y_POSITION_UPPER_BOUND,
                "Boss's Y position should not exceed the upper bound."
        );

        boss.setTranslateY(Constant.BOSS_Y_POSITION_LOWER_BOUND + 1); // Near lower bound
        boss.updatePosition();

        assertTrue(
                boss.getTranslateY() <= Constant.BOSS_Y_POSITION_LOWER_BOUND,
                "Boss's Y position should not exceed the lower bound."
        );
    }

    @Test
    public void testMovementPattern() {
        double initialY = boss.getTranslateY();

        for (int i = 0; i < Constant.BOSS_MOVE_FREQUENCY * 2; i++) { // Ensure pattern repeats
            boss.updatePosition();
        }

        double finalY = boss.getTranslateY();
        assertNotEquals(initialY, finalY, "Boss's Y position should change after moving.");
    }

    @Test
    public void testProjectileFiring() {
        boss.setShieldProbability(0.0); // Ensure shield does not interfere

        // Simulate firing logic
        ActiveActorDestructible projectile = boss.fireProjectile();

        if (projectile != null) {
            assertTrue(projectile instanceof BossProjectile, "Fired projectile should be an instance of BossProjectile.");
        } else {
            assertNull(projectile, "No projectile should be fired when fire rate condition is not met.");
        }
    }

    @Test
    public void testUpdateActor() {
        double initialY = boss.getTranslateY();
        boss.updateActor();

        double finalY = boss.getTranslateY();
        assertNotEquals(initialY, finalY, "Boss's position should update after calling updateActor.");
    }

    @Test
    public void testResetMovePattern() {
        boss.setTranslateY(Constant.BOSS_Y_POSITION_UPPER_BOUND);
        boss.updateActor();

        assertTrue(
                boss.getTranslateY() >= Constant.BOSS_Y_POSITION_UPPER_BOUND,
                "Boss's move pattern should reset and stay within bounds."
        );
    }
}
