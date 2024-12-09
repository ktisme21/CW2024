package com.example.demo.manager;

import com.example.demo.model.UserPlane;
import com.example.demo.testutils.JavaFXTestUtils;
import com.example.demo.utilities.Constant;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


import static org.mockito.Mockito.*;

class InputManagerTest {

    private InputManager inputManager;
    private UserPlane user;
    private ProjectileManager projectileManager;
    private ImageView background;
    private Group root;

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @BeforeEach
    void initialize() {
        user = mock(UserPlane.class);
        projectileManager = mock(ProjectileManager.class);
        inputManager = new InputManager(user, projectileManager);
        background = new ImageView();
        root = new Group();
    }

    @Test
    void testHandleKeyPressedMovement() {
        // Simulate key press events for movement
        inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_UP, false, false, false, false), root);
        verify(user).moveUp();

        inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_DOWN, false, false, false, false), root);
        verify(user).moveDown();

        inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_LEFT, false, false, false, false), root);
        verify(user).moveLeft();

        inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_RIGHT, false, false, false, false), root);
        verify(user).moveRight();
    }

    @Test
    void testHandleKeyPressedFireProjectile() {
        // Simulate key press event for firing projectile
        inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_FIRE, false, false, false, false), root);
        verify(projectileManager).fireProjectile(user, root);
    }

    @Test
    void testHandleKeyReleasedMovement() {
        // Simulate key release events for stopping movement
        inputManager.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", Constant.KEY_UP, false, false, false, false));
        verify(user).stopVertical();

        inputManager.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", Constant.KEY_DOWN, false, false, false, false));
        verify(user).stopVertical();

        inputManager.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", Constant.KEY_LEFT, false, false, false, false));
        verify(user).stopHorizontal();

        inputManager.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", Constant.KEY_RIGHT, false, false, false, false));
        verify(user).stopHorizontal();
    }

    @Test
    void testSetupInputHandlers() {
        // Test if input handlers are correctly set up on the background
        inputManager.setupInputHandlers(background, root, 800, 600);

        assertTrue(background.isFocusTraversable(), "Background should be focus traversable.");
        verify(background, atLeastOnce()).setOnKeyPressed(any());
        verify(background, atLeastOnce()).setOnKeyReleased(any());
        verify(background, atLeastOnce()).setOnMouseClicked(any());
    }
}
