package com.example.demo.mechanics;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the Destructible interface.
 */
class DestructibleTest {

    // Mock implementation of Destructible for testing
    private static class MockDestructible implements Destructible {
        private int health = 3; // Example health
        private boolean destroyed = false;

        @Override
        public void takeDamage() {
            if (health > 0) {
                health--;
                if (health == 0) {
                    destroy();
                }
            }
        }

        @Override
        public void destroy() {
            destroyed = true;
        }

        public int getHealth() {
            return health;
        }

        public boolean isDestroyed() {
            return destroyed;
        }
    }

    private MockDestructible destructible;

    @BeforeEach
    void setUp() {
        destructible = new MockDestructible();
    }

    @Test
    void testTakeDamage() {
        assertEquals(3, destructible.getHealth(), "Initial health should be 3.");
        destructible.takeDamage();
        assertEquals(2, destructible.getHealth(), "Health should decrease by 1 after taking damage.");
        destructible.takeDamage();
        assertEquals(1, destructible.getHealth(), "Health should decrease to 1 after another hit.");
    }

    @Test
    void testDestroy() {
        destructible.takeDamage();
        destructible.takeDamage();
        destructible.takeDamage(); // This should destroy it
        assertTrue(destructible.isDestroyed(), "Object should be destroyed when health reaches 0.");
    }

    @Test
    void testCannotTakeDamageAfterDestroyed() {
        destructible.takeDamage();
        destructible.takeDamage();
        destructible.takeDamage(); // Health reaches 0 and it's destroyed
        destructible.takeDamage(); // Any further damage should not affect it
        assertTrue(destructible.isDestroyed(), "Object should remain destroyed.");
        assertEquals(0, destructible.getHealth(), "Health should not go below 0.");
    }
}
