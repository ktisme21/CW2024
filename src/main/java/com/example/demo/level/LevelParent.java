package com.example.demo.level;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.LevelChangeListener;
import com.example.demo.controller.Main;
import com.example.demo.manager.GlobalGameTimer;
import com.example.demo.manager.InputManager;
import com.example.demo.manager.LeaderboardManager;
import com.example.demo.manager.ProjectileManager;
import com.example.demo.manager.UIManager;
import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.EnemyPlane;
import com.example.demo.model.UserPlane;
import com.example.demo.utilities.Constant;
import com.example.demo.view.LeaderBoard;
import com.example.demo.view.LevelView;
import com.example.demo.view.PauseScreen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class LevelParent {

    private LevelChangeListener listener;

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

    private int currentNumberOfEnemies;
    private LevelView levelView;
    private boolean hasAlerted = false;
    private boolean isTransitioned = false;
    private final InputManager inputManager;
    private final ProjectileManager projectileManager;
    private UIManager uiManager;

    private final GlobalGameTimer gameTimer = GlobalGameTimer.getInstance(); // Use global timer instance

    public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.timeline = new Timeline();
        this.user = new UserPlane(playerInitialHealth);
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.projectileManager = new ProjectileManager(userProjectiles, enemyProjectiles);
        this.inputManager = new InputManager(user, projectileManager);

        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.enemyMaximumYPosition = screenHeight - Constant.SCREEN_HEIGHT_ADJUSTMENT;
        this.levelView = instantiateLevelView();
        this.currentNumberOfEnemies = 0;
        this.hasAlerted = false;
        this.isTransitioned = false;
        initializeTimeline();
        friendlyUnits.add(user);
    }

    // Abstract Methods
    protected abstract void checkIfGameOver();

    protected abstract void spawnEnemyUnits();

    protected abstract LevelView instantiateLevelView();

    // Initialization Methods
    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();
        uiManager = new UIManager(root, screenWidth, this::showPauseScreen);
        return scene;
    }

    protected void initializeFriendlyUnits() {
        root.getChildren().add(getUser());
    }

    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(Constant.MILLISECOND_DELAY), e -> updateScene());
        timeline.getKeyFrames().add(gameLoop);
    }

    private void initializeBackground() {
        inputManager.setupInputHandlers(background, root, screenHeight, screenWidth);
        root.getChildren().add(background);
    }

    // Gameplay Methods
    public void startGame() {
        background.requestFocus();
        timeline.play();
        GlobalGameTimer.getInstance().start();
    }

    protected void updateScene() {
        spawnEnemyUnits();
        updateActors();
        projectileManager.generateEnemyFire(enemyUnits, root);
        updateNumberOfEnemies();
        handleEnemyPenetration();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handlePlaneCollisions();
        removeInvisibleProjectiles();
        removeAllDestroyedActors();
        updateKillCount();
        updateLevelView();
        checkIfGameOver();
        updateTopRightLabel(); // Update the timer
    }

    private void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    private void updateKillCount() {
        for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
            user.incrementKillCount();
        }
    }

    private void removeInvisibleProjectiles() {
    projectileManager.removeInvisibleProjectiles(root);
}

    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : new ArrayList<>(enemyUnits)) {
            if (enemy instanceof EnemyPlane && hasEnemyExitedScreen(enemy)) {
                user.takeDamage();
                enemy.destroy();
            } else if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
            }
        }
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    // Collision Handling Methods
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
            for (ActiveActorDestructible actor2 : actors2) { // Use custom collision bounds for both actors
                if (actor1.getCollisionBounds().intersects(actor2.getCollisionBounds())) {
                    actor1.takeDamage();
                    actor2.takeDamage();
                }
            }
        }
    }

    // UI Handling
    private void showPauseScreen() {
        if (timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.pause();
            gameTimer.stop();
        }

        PauseScreen pauseScreen = new PauseScreen(
            (Stage) scene.getWindow(),
            this::resumeGame,
            () -> returnToMainMenu((Stage) scene.getWindow())
        );
        pauseScreen.show();
    }

    private void resumeGame() {
        timeline.play();
        gameTimer.start();
        background.requestFocus();
    }

    private void updateTopRightLabel() {
        String elapsedTime = gameTimer.formatElapsedTime();
        uiManager.updateTimer(elapsedTime);
    }
    

    // Utilities
    private boolean hasEnemyExitedScreen(ActiveActorDestructible enemy) {
        double enemyX = enemy.getLayoutX() + enemy.getTranslateX();
        return enemyX + enemy.getBoundsInParent().getWidth() < 0 || enemyX > screenWidth;
    }

    protected void updateLevelView() {
        levelView.removeHearts(user.getHealth());
    }

    // Getters
    protected UserPlane getUser() {
        return user;
    }

    protected Group getRoot() {
        return root;
    }

    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
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

    // Initialization
    public void setLevelChangeListener(LevelChangeListener listener) {
        this.listener = listener;
    }

    // Game State Management
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

    protected void winGame() {
        timeline.stop();
		GlobalGameTimer.getInstance().stop();
		String timeUsed = formatElapsedTime();
        saveToLeaderboard(timeUsed);
        levelView.showWinImage();
        addQuitEventHandler(timeUsed);
    }

    protected void loseGame() {
        timeline.stop();
		GlobalGameTimer.getInstance().stop();
		String timeUsed = formatElapsedTime();
        levelView.showGameOverImage();
        addQuitEventHandler(timeUsed);
    }

    // Utility Methods
    public List<ActiveActorDestructible> getUserProjectiles() {
        return userProjectiles;
    }

    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
        
    }

    private void saveToLeaderboard(String timeUsed) {
        LeaderboardManager leaderboardManager = new LeaderboardManager(); // Load existing leaderboard
        leaderboardManager.addEntry("Player", parseTimeToMillis(timeUsed)); // Add the current score
    }

    private int parseTimeToMillis(String timeUsed) {
        try {
            String[] parts = timeUsed.split("[:.]");
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);
            int millis = Integer.parseInt(parts[2]);
            return (minutes * 60 * 1000) + (seconds * 1000) + millis;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Invalid time format: " + timeUsed);
            return 0; // Default to 0 on error
        }
    }

    private String formatElapsedTime() {
        return gameTimer.formatElapsedTime();
    }

    // Leaderboard and Navigation
    private void addQuitEventHandler(String timeUsed) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.Q) {
                showLeaderBoard(timeUsed);
            }
        });
    }

    private void showLeaderBoard(String timeUsed) {
        Stage stage = (Stage) scene.getWindow();
        LeaderboardManager manager = new LeaderboardManager(); // Load leaderboard
    
        new LeaderBoard(
            stage,
            manager, // Pass the manager instance
            "Player", // Player's name
            parseTimeToMillis(timeUsed), // Player's score
            event -> returnToMainMenu(stage), // Back to main menu action
            event -> restartGame(stage) // Restart game action
        );
    }

    private void restartGame(Stage stage) {
        timeline.stop();
		GlobalGameTimer.getInstance().reset();
		
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
        timeline.stop();
		gameTimer.reset();
        Main main = new Main();
        main.showMainMenu(stage);
    }

    protected void addEnemyUnit(ActiveActorDestructible enemy) { // Check if the enemy is already in the root
        if (!getRoot().getChildren().contains(enemy)) {
            enemyUnits.add(enemy);
            root.getChildren().add(enemy);
        }
    }

}
