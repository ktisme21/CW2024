package com.example.demo.manager;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.ActiveActorDestructible;
import javafx.scene.Group;

/**
 * Manages collision detection and handling between various game objects.
 * This includes handling collisions between friendly units, enemy units, 
 * projectiles, and the player's unit.
 */
public class CollisionManager {

    /**
     * Handles collisions between friendly units and enemy units.
     *
     * @param friendlyUnits a list of friendly units (e.g., player's allies).
     * @param enemyUnits a list of enemy units.
     */
    public void handlePlaneCollisions(List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits) {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     *
     * @param userProjectiles a list of projectiles fired by the user.
     * @param enemyUnits a list of enemy units.
     */
    public void handleUserProjectileCollisions(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyUnits) {
        handleCollisions(userProjectiles, enemyUnits);
    }

    /**
     * Handles collisions between enemy projectiles and the user's unit.
     *
     * @param enemyProjectiles a list of projectiles fired by enemy units.
     * @param user the user's active actor (e.g., the player's plane).
     * @param root the root group containing game objects (for visual updates).
     */
    public void handleEnemyProjectileCollisions(
        List<ActiveActorDestructible> enemyProjectiles, 
        ActiveActorDestructible user, 
        Group root
    ) {
        for (ActiveActorDestructible projectile : new ArrayList<>(enemyProjectiles)) {
            if (user.isVisible() && projectile.getCollisionBounds().intersects(user.getCollisionBounds())) {
                user.takeDamage(); // Deduct health only if the UserPlane is visible
                projectile.destroy();   // Destroy the projectile on collision
            }
        }
    }

    /**
     * Private method to handle generic collisions between two lists of actors.
     * Each actor from the first list is checked for collisions with every actor
     * in the second list.
     *
     * @param actors1 the first list of active actors.
     * @param actors2 the second list of active actors.
     */
    private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor1 : actors1) {
            for (ActiveActorDestructible actor2 : actors2) {
                if (actor1.getCollisionBounds().intersects(actor2.getCollisionBounds())) {
                    actor1.takeDamage();
                    actor2.takeDamage();
                }
            }
        }
    }
}
