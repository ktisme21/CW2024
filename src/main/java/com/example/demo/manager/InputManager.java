package com.example.demo.manager;

import com.example.demo.model.UserPlane;
import com.example.demo.utilities.Constant;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Handles user input for controlling the game.
 * This includes managing keyboard events for movement and firing projectiles.
 */
public class InputManager {

    private final UserPlane user;
    private final ProjectileManager projectileManager;

    /**
     * Initializes the InputManager with the user and projectile manager.
     *
     * @param user              the user's plane.
     * @param projectileManager the manager responsible for firing projectiles.
     */
    public InputManager(UserPlane user, ProjectileManager projectileManager) {
        this.user = user;
        this.projectileManager = projectileManager;
    }

    /**
     * Sets up input handlers for the game background, enabling keyboard and mouse interactions.
     *
     * @param background    the background ImageView where input events are captured.
     * @param root          the root group containing game objects.
     * @param screenHeight  the height of the screen.
     * @param screenWidth   the width of the screen.
     */
    public void setupInputHandlers(ImageView background, Group root, double screenHeight, double screenWidth) {
        background.setId("background");
        background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setOnKeyPressed(e -> handleKeyPressed(e, root));
        background.setOnKeyReleased(this::handleKeyReleased);
        background.setOnMouseClicked(event -> background.requestFocus());
    }

    /**
     * Handles key press events for user movement and firing projectiles.
     *
     * @param e    the KeyEvent triggered by a key press.
     * @param root the root group for adding new projectiles.
     */
    public void handleKeyPressed(KeyEvent e, Group root) {
        KeyCode kc = e.getCode();
        if (kc == Constant.KEY_UP) user.moveUp();
        if (kc == Constant.KEY_DOWN) user.moveDown();
        if (kc == Constant.KEY_LEFT) user.moveLeft();
        if (kc == Constant.KEY_RIGHT) user.moveRight();
        if (kc == Constant.KEY_FIRE) projectileManager.fireProjectile(user, root);
    }

    /**
     * Handles key release events to stop user movement.
     *
     * @param e the KeyEvent triggered by a key release.
     */
    public void handleKeyReleased(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == Constant.KEY_UP || kc == Constant.KEY_DOWN) user.stopVertical();
        if (kc == Constant.KEY_LEFT || kc == Constant.KEY_RIGHT) user.stopHorizontal();
    }
}
