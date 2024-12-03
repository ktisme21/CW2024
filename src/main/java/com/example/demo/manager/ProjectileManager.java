package com.example.demo.manager;

import java.util.List;

import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.FighterPlane;
import com.example.demo.model.UserPlane;

import javafx.scene.Group;

public class ProjectileManager {

    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;

    public ProjectileManager(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyProjectiles) {
        this.userProjectiles = userProjectiles;
        this.enemyProjectiles = enemyProjectiles;
    }

    public void removeInvisibleProjectiles(Group root) {
        userProjectiles.stream()
            .filter(projectile -> !projectile.isVisible())
            .forEach(projectile -> root.getChildren().remove(projectile));
        userProjectiles.removeIf(projectile -> !projectile.isVisible());
    }

    public void fireProjectile(UserPlane user, Group root) {
        ActiveActorDestructible projectile = user.fireProjectile();
        if (projectile != null) {
            root.getChildren().add(projectile);
            userProjectiles.add(projectile);
        } else {
            System.out.println("Cannot shoot while the plane is invisible!");
        }
    }

    public void generateEnemyFire(List<ActiveActorDestructible> enemyUnits, Group root) {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile(), root));
    }

    private void spawnEnemyProjectile(ActiveActorDestructible projectile, Group root) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }
}
