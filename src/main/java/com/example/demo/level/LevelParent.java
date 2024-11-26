package com.example.demo.level;

import com.example.demo.controller.Main;
import com.example.demo.display.GlobalGameTimer;
import com.example.demo.LevelChangeListener;
import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.EnemyPlane;
import com.example.demo.model.FighterPlane;
import com.example.demo.model.UserPlane;
import com.example.demo.view.LeaderBoard;
import com.example.demo.view.LevelView;
import com.example.demo.view.PauseScreen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class LevelParent {

    private LevelChangeListener listener;

    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
    private static final int MILLISECOND_DELAY = 50;
    private final double screenHeight;
    private final double screenWidth;
    private final double enemyMaximumYPosition;

    private final Group root;
    private final Timeline timeline;
    private final UserPlane user;
    private final Scene scene;
    private final ImageView background;

    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;

    private Label topRightLabel;
    private int currentNumberOfEnemies;
    private LevelView levelView;
    private boolean hasAlerted = false;
    private boolean isTransitioned = false;

    private final GlobalGameTimer gameTimer = GlobalGameTimer.getInstance(); // Use global timer instance
    private final String PAUSE_BUTTON = "-fx-font-size: 14px; -fx-padding: 5px 10px;";

    public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.timeline = new Timeline();
        this.user = new UserPlane(playerInitialHealth);
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();

        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
        this.levelView = instantiateLevelView();
        this.currentNumberOfEnemies = 0;
        this.hasAlerted = false;
        this.isTransitioned = false;
        initializeTimeline();
        friendlyUnits.add(user);
    }

    private void initializeTopRightUI() {
        // Create the Pause button
        Button topRightButton = new Button("Pause");
        topRightButton.setStyle(PAUSE_BUTTON);

        // Position the Pause button
        topRightButton.setLayoutX(screenWidth - 120); // Position near the right edge
        topRightButton.setLayoutY(20); // Position near the top

        // Add action to the Pause button
        topRightButton.setOnAction(event -> showPauseScreen());

        // Create the Label for the timer
        topRightLabel = new Label("Time: 00:00");
        topRightLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        topRightLabel.setLayoutX(screenWidth - 300); // Position left of the Pause button
        topRightLabel.setLayoutY(25); // Align with the Pause button

        // Add the button and label to the root
        root.getChildren().addAll(topRightButton, topRightLabel);

        // Start the timer
        startTimer();
    }

    private void startTimer() {
        gameTimer.start(); // Start the global timer
    }

    private void updateTopRightLabel() {
        Duration elapsedTime = gameTimer.getElapsedTime();
        int minutes = (int) elapsedTime.toMinutes();
        int seconds = (int) elapsedTime.toSeconds() % 60;
        topRightLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    public void startGame() {
        background.requestFocus();
        timeline.play();
        gameTimer.start(); // Ensure the timer starts
    }

    private void showPauseScreen() {
        // Pause the game timeline
        timeline.pause();

        // Create and show the PauseScreen
        PauseScreen pauseScreen = new PauseScreen(
                (Stage) scene.getWindow(),
                () -> {
                    // Resume action: Close pause screen and resume gameplay
                    timeline.play();
                    background.requestFocus(); // Return focus to the game background
                },
                () -> returnToMainMenu((Stage) scene.getWindow()) // Quit action
        );
        pauseScreen.show();
    }

    public void setLevelChangeListener(LevelChangeListener listener) {
        this.listener = listener;
    }

    protected void initializeFriendlyUnits() {
        root.getChildren().add(getUser()); // Add user plane to the root
        getUser().addRedContainerToRoot(root); // Add red container to the root
    }

    protected abstract void checkIfGameOver();

    protected abstract void spawnEnemyUnits();

    protected abstract LevelView instantiateLevelView();

    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();
        initializeTopRightUI();
        return scene;
    }

    public boolean isTransitioned() {
        return isTransitioned;
    }

    public void goToNextLevel(String levelName) {
        if (!hasAlerted && listener != null) {
            listener.onLevelChange(levelName);
            hasAlerted = true;
            isTransitioned = true;
        }
    }

    private void updateScene() {
        spawnEnemyUnits();
        updateActors();
        generateEnemyFire();
        updateNumberOfEnemies();
        handleEnemyPenetration();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handlePlaneCollisions();
        removeAllDestroyedActors();
        updateKillCount();
        updateLevelView();
        checkIfGameOver();
        updateTopRightLabel(); // Update the timer label
    }

    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
        timeline.getKeyFrames().add(gameLoop);
    }

    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.UP) user.moveUp();
                if (kc == KeyCode.DOWN) user.moveDown();
                if (kc == KeyCode.LEFT) user.moveLeft();
                if (kc == KeyCode.RIGHT) user.moveRight();
                if (kc == KeyCode.SPACE) fireProjectile();
            }
        });

        background.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stopVertical();
                if (kc == KeyCode.LEFT || kc == KeyCode.RIGHT) user.stopHorizontal();
            }
        });
        root.getChildren().add(background);
    }

    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        root.getChildren().add(projectile);
        userProjectiles.add(projectile);
    }

    private void generateEnemyFire() {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    private void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    private void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    private void handleEnemyProjectileCollisions() {
        for (ActiveActorDestructible projectile : new ArrayList<>(enemyProjectiles)) {
            if (getUser().isVisible() && projectile.getCollisionBounds().intersects(getUser().getCollisionBounds())) {
                getUser().takeDamage(); // Deduct health only if the UserPlane is visible
                projectile.destroy();   // Destroy the projectile on collision
            }
        }
    }

    private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor1 : actors1) {
            for (ActiveActorDestructible actor2 : actors2) {
                // Use custom collision bounds for both actors
                if (actor1.getCollisionBounds().intersects(actor2.getCollisionBounds())) {
                    actor1.takeDamage();
                    actor2.takeDamage();
                }
            }
        }
    }

    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : new ArrayList<>(enemyUnits)) {
            if (enemy instanceof EnemyPlane && hasEnemyExitedScreen(enemy)) {
                // Deduct one heart
                user.takeDamage();

                // Destroy the enemy plane
                enemy.destroy();
            } else if (enemyHasPenetratedDefenses(enemy)) {
                // Handle enemy penetration logic
                user.takeDamage();
                enemy.destroy();
            }
        }
    }

    private boolean hasEnemyExitedScreen(ActiveActorDestructible enemy) {
        // Check if the enemy has exited the screen bounds
        double enemyX = enemy.getLayoutX() + enemy.getTranslateX();
        double screenWidth = getScreenWidth();
        return enemyX + enemy.getBoundsInParent().getWidth() < 0 || enemyX > screenWidth;
    }

    protected void updateLevelView() {
        levelView.removeHearts(user.getHealth());
    }

    private void updateKillCount() {
        for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
            user.incrementKillCount();
        }
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    protected void winGame() {
        timeline.stop();
        levelView.showWinImage();
        addQuitEventHandler();
    }

    protected void loseGame() {
        timeline.stop();
        levelView.showGameOverImage();
        addQuitEventHandler();
    }

    private void addQuitEventHandler() {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.Q) {
                gotToLeaderBoard();
            }
        });
    }

    private void gotToLeaderBoard() {
        // Get the current stage
        Stage stage = (Stage) scene.getWindow();

        // Create ScorePage with restart functionality
        LeaderBoard leaderboard = new LeaderBoard(
                stage,
                event -> returnToMainMenu(stage), // Back to main menu
                event -> restartGame() // Restart the game
        );

        // Show the ScorePage as a pop-up
        leaderboard.show();
    }

    private void restartGame() {
        // Stop the current timeline to prevent further updates
        timeline.stop();
		gameTimer.reset();

        // Get the current stage
        Stage stage = (Stage) scene.getWindow();

        // Restart the game from LevelOne
        try {
            LevelOne levelOne = new LevelOne(screenHeight, screenWidth);
            levelOne.setLevelChangeListener(listener); // Reattach the level change listener
            Scene newScene = levelOne.initializeScene();
            stage.setScene(newScene);
            levelOne.startGame(); // Start LevelOne
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnToMainMenu(Stage stage) {
        Main main = new Main();
        main.showMainMenu(stage);
    }

    protected UserPlane getUser() {
        return user;
    }

    protected Group getRoot() {
        return root;
    }

    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        // Check if the enemy is already in the root
        if (!getRoot().getChildren().contains(enemy)) {
            enemyUnits.add(enemy);
            root.getChildren().add(enemy);
            enemy.addRedContainerToRoot(root); // Assuming this adds additional visuals to the enemy
        }
    }

    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    protected double getScreenWidth() {
        return screenWidth;
    }

    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = enemyUnits.size();
    }
}
