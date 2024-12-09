package com.example.demo.manager;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.testutils.JavaFXTestUtils;

import javafx.geometry.Bounds;
import javafx.scene.Group;

class CollisionManagerTest {

    private CollisionManager collisionManager;
    private ActiveActorDestructible friendlyUnit;
    private ActiveActorDestructible enemyUnit;
    private ActiveActorDestructible userProjectile;
    private ActiveActorDestructible enemyProjectile;
    private ActiveActorDestructible user;
    private Group root;

    @BeforeAll
    public static void initializeJavaFX() {
        JavaFXTestUtils.initializeJavaFX(); // Initialize JavaFX for testing
    }

    @BeforeEach
    void setUpTestEnvironment() {
        collisionManager = new CollisionManager();
        friendlyUnit = mock(ActiveActorDestructible.class);
        enemyUnit = mock(ActiveActorDestructible.class);
        userProjectile = mock(ActiveActorDestructible.class);
        enemyProjectile = mock(ActiveActorDestructible.class);
        user = mock(ActiveActorDestructible.class);
        root = new Group();
    }

    @Test
    void testHandlePlaneCollisions() {
        List<ActiveActorDestructible> friendlyUnits = List.of(friendlyUnit);
        List<ActiveActorDestructible> enemyUnits = List.of(enemyUnit);

        // Mock bounds for collision
        Bounds friendlyBounds = mock(Bounds.class);
        Bounds enemyBounds = mock(Bounds.class);

        when(friendlyUnit.getCollisionBounds()).thenReturn(friendlyBounds);
        when(enemyUnit.getCollisionBounds()).thenReturn(enemyBounds);
        when(friendlyBounds.intersects(enemyBounds)).thenReturn(true);

        collisionManager.handlePlaneCollisions(friendlyUnits, enemyUnits);

        // Verify both units take damage
        verify(friendlyUnit).takeDamage();
        verify(enemyUnit).takeDamage();
    }

    @Test
    void testHandleUserProjectileCollisions() {
        List<ActiveActorDestructible> userProjectiles = List.of(userProjectile);
        List<ActiveActorDestructible> enemyUnits = List.of(enemyUnit);

        // Mock bounds for collision
        Bounds projectileBounds = mock(Bounds.class);
        Bounds enemyBounds = mock(Bounds.class);

        when(userProjectile.getCollisionBounds()).thenReturn(projectileBounds);
        when(enemyUnit.getCollisionBounds()).thenReturn(enemyBounds);
        when(projectileBounds.intersects(enemyBounds)).thenReturn(true);

        collisionManager.handleUserProjectileCollisions(userProjectiles, enemyUnits);

        // Verify both projectile and enemy take damage
        verify(userProjectile).takeDamage();
        verify(enemyUnit).takeDamage();
    }

    @Test
    void testHandleEnemyProjectileCollisions() {
        List<ActiveActorDestructible> enemyProjectiles = List.of(enemyProjectile);

        // Mock bounds for collision
        Bounds userBounds = mock(Bounds.class);
        Bounds projectileBounds = mock(Bounds.class);

        when(user.isVisible()).thenReturn(true);
        when(user.getCollisionBounds()).thenReturn(userBounds);
        when(enemyProjectile.getCollisionBounds()).thenReturn(projectileBounds);
        when(userBounds.intersects(projectileBounds)).thenReturn(true);

        collisionManager.handleEnemyProjectileCollisions(enemyProjectiles, user, root);

        // Verify user takes damage and projectile is destroyed
        verify(user).takeDamage();
        verify(enemyProjectile).destroy();
    }

    @Test
    void testNoCollisionOccurs() {
        List<ActiveActorDestructible> actors1 = List.of(friendlyUnit);
        List<ActiveActorDestructible> actors2 = List.of(enemyUnit);

        // Mock bounds for no collision
        Bounds friendlyBounds = mock(Bounds.class);
        Bounds enemyBounds = mock(Bounds.class);

        when(friendlyUnit.getCollisionBounds()).thenReturn(friendlyBounds);
        when(enemyUnit.getCollisionBounds()).thenReturn(enemyBounds);
        when(friendlyBounds.intersects(enemyBounds)).thenReturn(false);

        collisionManager.handlePlaneCollisions(actors1, actors2);

        // Verify no damage is taken
        verify(friendlyUnit, never()).takeDamage();
        verify(enemyUnit, never()).takeDamage();
    }
}
