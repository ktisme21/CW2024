package com.example.demo.manager;

import com.example.demo.model.UserPlane;
import com.example.demo.utilities.Constant;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputManager {

    private final UserPlane user;
    private final ProjectileManager projectileManager;

    public InputManager(UserPlane user, ProjectileManager projectileManager) {
        this.user = user;
        this.projectileManager = projectileManager;
    }

    public void setupInputHandlers(ImageView background, Group root, double screenHeight, double screenWidth) {
        background.setId("background"); // Add an ID for lookup
        background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setOnKeyPressed(e -> handleKeyPressed(e, root));
        background.setOnKeyReleased(this::handleKeyReleased);

        background.setOnMouseClicked(event -> background.requestFocus());
    }

    public void handleKeyPressed(KeyEvent e, Group root) {
        KeyCode kc = e.getCode();
        if (kc == Constant.KEY_UP) user.moveUp();
        if (kc == Constant.KEY_DOWN) user.moveDown();
        if (kc == Constant.KEY_LEFT) user.moveLeft();
        if (kc == Constant.KEY_RIGHT) user.moveRight();
        if (kc == Constant.KEY_FIRE) projectileManager.fireProjectile(user, root);
    }

    public void handleKeyReleased(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == Constant.KEY_UP || kc == Constant.KEY_DOWN) user.stopVertical();
        if (kc == Constant.KEY_LEFT || kc == Constant.KEY_RIGHT) user.stopHorizontal();
    }
}
