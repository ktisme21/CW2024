package com.example.demo.manager;

import java.util.List;

import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.FighterPlane;
import com.example.demo.model.UserPlane;
import com.example.demo.utilities.Constant;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Label;

/**
 * Manages the projectiles fired by the user and enemy units in the game.
 * Handles spawning, updating, and removing invisible projectiles.
 */
public class ProjectileManager {

    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;

    /**
     * Constructs a {@code ProjectileManager}.
     *
     * @param userProjectiles A list to store projectiles fired by the user.
     * @param enemyProjectiles A list to store projectiles fired by the enemies.
     */
    public ProjectileManager(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyProjectiles) {
        this.userProjectiles = userProjectiles;
        this.enemyProjectiles = enemyProjectiles;
    }

    /**
     * Removes projectiles that are no longer visible from the scene and the list.
     *
     * @param root The root node of the game scene.
     */
    public void removeInvisibleProjectiles(Group root) {
        userProjectiles.stream()
            .filter(projectile -> !projectile.isVisible())
            .forEach(projectile -> root.getChildren().remove(projectile));
        userProjectiles.removeIf(projectile -> !projectile.isVisible());
    }

    /**
     * Fires a projectile from the user's plane and adds it to the scene.
     *
     * @param user The user's plane.
     * @param root The root node of the game scene.
     */
    public void fireProjectile(UserPlane user, Group root) {
        ActiveActorDestructible projectile = user.fireProjectile();
        if (projectile != null) {
            root.getChildren().add(projectile);
            userProjectiles.add(projectile);
        } else {
            displayCannotShootMessage(root);
        }
    }

    private void displayCannotShootMessage(Group root) {
        Label cannotShootLabel = new Label("Cannot shoot while the plane is invisible!");
        cannotShootLabel.setStyle(Constant.MESSAGE_LABEL_STYLE);
        cannotShootLabel.setLayoutX(Constant.INSTRUCTION_LABEL_X - 100);
        cannotShootLabel.setLayoutY(Constant.INSTRUCTION_LABEL_Y + 50);

        root.getChildren().add(cannotShootLabel);

        Timeline timeline = new Timeline(
            new KeyFrame(javafx.util.Duration.seconds(Constant.INSTRUCTION_MESSAGE_DURATION),
                event -> root.getChildren().remove(cannotShootLabel))
        );
        timeline.play();
    }

    /**
     * Generates and spawns projectiles for all active enemy units.
     *
     * @param enemyUnits A list of active enemy units.
     * @param root The root node of the game scene.
     */
    public void generateEnemyFire(List<ActiveActorDestructible> enemyUnits, Group root) {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile(), root));
    }

    /**
     * Spawns a projectile fired by an enemy unit and adds it to the scene.
     *
     * @param projectile The projectile fired by an enemy.
     * @param root The root node of the game scene.
     */
    private void spawnEnemyProjectile(ActiveActorDestructible projectile, Group root) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }
}
