// package com.example.demo.manager;

// import com.example.demo.model.UserPlane;
// import com.example.demo.projectiles.UserProjectile;
// import com.example.demo.testutils.JavaFXTestUtils;
// import com.example.demo.utilities.Constant;

// import javafx.scene.Group;
// import javafx.scene.image.ImageView;
// import javafx.scene.input.KeyEvent;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.*;

// class InputManagerTest {

//     private InputManager inputManager;
//     private TestUserPlane testUser;
//     private TestProjectileManager testProjectileManager;
//     private ImageView background;
//     private Group root;

//     @BeforeAll
//     public static void setUp() {
//         JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
//     }

//     @BeforeEach
//     void initialize() {
//         testUser = new TestUserPlane();
//         testProjectileManager = new TestProjectileManager();
//         inputManager = new InputManager(testUser, testProjectileManager);
//         background = new ImageView();
//         root = new Group();
//     }

//     @Test
//     void testHandleKeyPressedMovement() {
//         inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_UP, false, false, false, false), root);
//         assertTrue(testUser.movedUp, "User should have moved up after KEY_UP pressed.");

//         inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_DOWN, false, false, false, false), root);
//         assertTrue(testUser.movedDown, "User should have moved down after KEY_DOWN pressed.");

//         inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_LEFT, false, false, false, false), root);
//         assertTrue(testUser.movedLeft, "User should have moved left after KEY_LEFT pressed.");

//         inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_RIGHT, false, false, false, false), root);
//         assertTrue(testUser.movedRight, "User should have moved right after KEY_RIGHT pressed.");
//     }

//     @Test
//     void testHandleKeyPressedFireProjectile() {
//         inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_FIRE, false, false, false, false), root);
//         assertTrue(testProjectileManager.firedProjectile, "Projectile should have been fired after KEY_FIRE pressed.");
//         assertSame(testUser, testProjectileManager.firedUser, "Projectile should have been fired from the testUser.");
//         assertTrue(root.getChildren().contains(testProjectileManager.lastFiredProjectile), "Fired projectile should have been added to the root.");
//     }

//     @Test
//     void testHandleKeyReleasedMovement() {
//         // Press keys first, so we actually have something to stop
//         inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_UP, false, false, false, false), root);
//         inputManager.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", Constant.KEY_UP, false, false, false, false));
//         assertTrue(testUser.verticalStopped, "User's vertical movement should have stopped after releasing KEY_UP.");

//         inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_DOWN, false, false, false, false), root);
//         inputManager.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", Constant.KEY_DOWN, false, false, false, false));
//         assertTrue(testUser.verticalStopped, "User's vertical movement should have stopped after releasing KEY_DOWN.");

//         inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_LEFT, false, false, false, false), root);
//         inputManager.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", Constant.KEY_LEFT, false, false, false, false));
//         assertTrue(testUser.horizontalStopped, "User's horizontal movement should have stopped after releasing KEY_LEFT.");

//         inputManager.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", Constant.KEY_RIGHT, false, false, false, false), root);
//         inputManager.handleKeyReleased(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", Constant.KEY_RIGHT, false, false, false, false));
//         assertTrue(testUser.horizontalStopped, "User's horizontal movement should have stopped after releasing KEY_RIGHT.");
//     }

//     @Test
//     void testSetupInputHandlers() {
//         inputManager.setupInputHandlers(background, root, 800, 600);
//         assertTrue(background.isFocusTraversable(), "Background should be focus traversable.");

//         // Since we're not using Mockito anymore, we can't verify methods like setOnKeyPressed directly.
//         // Instead, we can ensure that these handlers are not null:
//         assertNotNull(background.getOnKeyPressed(), "OnKeyPressed handler should be set.");
//         assertNotNull(background.getOnKeyReleased(), "OnKeyReleased handler should be set.");
//         assertNotNull(background.getOnMouseClicked(), "OnMouseClicked handler should be set.");
//     }

//     /**
//      * Test double for UserPlane. Records whether movement methods were called.
//      */
//     private static class TestUserPlane implements UserPlane {
//         boolean movedUp = false;
//         boolean movedDown = false;
//         boolean movedLeft = false;
//         boolean movedRight = false;
//         boolean verticalStopped = false;
//         boolean horizontalStopped = false;

//         @Override
//         public void moveUp() { movedUp = true; }
//         @Override
//         public void moveDown() { movedDown = true; }
//         @Override
//         public void moveLeft() { movedLeft = true; }
//         @Override
//         public void moveRight() { movedRight = true; }
//         @Override
//         public void stopVertical() { verticalStopped = true; }
//         @Override
//         public void stopHorizontal() { horizontalStopped = true; }

//         @Override
//         public UserProjectile fireProjectile() {
//             // Return a dummy projectile
//             return new UserProjectile();
//         }

//         @Override
//         public boolean isVisible() {
//             return true;
//         }
//     }

//     /**
//      * Test double for ProjectileManager. Records whether fireProjectile was called.
//      */
//     private static class TestProjectileManager extends ProjectileManager {
//         boolean firedProjectile = false;
//         UserPlane firedUser = null;
//         UserProjectile lastFiredProjectile = null;

//         public TestProjectileManager() {
//             super(null, null); // Pass empty lists if needed
//         }

//         @Override
//         public void fireProjectile(UserPlane user, Group root) {
//             firedProjectile = true;
//             firedUser = user;
//             // Simulate adding a projectile to the root
//             lastFiredProjectile = user.fireProjectile();
//             root.getChildren().add(lastFiredProjectile);
//         }
//     }
// }
