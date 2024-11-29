package com.example.demo.level;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.LevelChangeListener;
import com.example.demo.controller.Main;
import com.example.demo.manager.GlobalGameTimer;
import com.example.demo.manager.LeaderboardManager;
import com.example.demo.model.ActiveActorDestructible;
import com.example.demo.model.EnemyPlane;
import com.example.demo.model.FighterPlane;
import com.example.demo.model.UserPlane;
import com.example.demo.utilities.Constant;
import com.example.demo.view.LeaderBoard;
import com.example.demo.view.LevelView;
import com.example.demo.view.PauseScreen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

    private Label topRightLabel;
    private int currentNumberOfEnemies;
    private LevelView levelView;
    private boolean hasAlerted = false;
    private boolean isTransitioned = false;

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

    private void initializeTopRightUI() {
        // Create the Pause button with an image
        Image settingsImage = new Image(getClass().getResource("/com/example/demo/buttons/settings.png").toExternalForm());
        ImageView settingsImageView = new ImageView(settingsImage);
        settingsImageView.setFitWidth(Constant.PAUSE_BUTTON_WIDTH); // Set desired button width
        settingsImageView.setFitHeight(Constant.PAUSE_BUTTON_HEIGHT); // Set desired button height

        Button topRightButton = new Button();
        topRightButton.setGraphic(settingsImageView);
        topRightButton.setStyle("-fx-background-color: transparent;"); // Remove default button styling

        // Position the Pause button
        topRightButton.setLayoutX(screenWidth - Constant.PAUSE_BUTTON_X_POSITION);
        topRightButton.setLayoutY(Constant.PAUSE_BUTTON_Y_POSITION);

        // Add action to the Pause button
        topRightButton.setOnAction(event -> showPauseScreen());

        // Create the Label for the timer
        topRightLabel = new Label("Timer: 00:00:00");
        topRightLabel.setStyle(Constant.TIMER_LABEL_STYLE);
        topRightLabel.setLayoutX(screenWidth - Constant.TIMER_LABEL_X_POSITION);
        topRightLabel.setLayoutY(Constant.TIMER_LABEL_Y_POSITION);

        // Create a semi-transparent rectangle background for the timer
        Rectangle timerBackground = new Rectangle();
        timerBackground.setWidth(Constant.TIMER_BACKGROUND_WIDTH);
        timerBackground.setHeight(Constant.TIMER_BACKGROUND_HEIGHT);
        timerBackground.setFill(Color.web(Constant.TIMER_BACKGROUND_COLOR)); // Semi-transparent color
        timerBackground.setArcWidth(Constant.TIMER_BACKGROUND_CORNER_RADIUS); // Rounded corners
        timerBackground.setArcHeight(Constant.TIMER_BACKGROUND_CORNER_RADIUS);
        timerBackground.setLayoutX(screenWidth - Constant.TIMER_LABEL_X_POSITION - 10); // Adjust for padding
        timerBackground.setLayoutY(Constant.TIMER_LABEL_Y_POSITION - 5); // Adjust for padding

        // Add the button, rectangle, and label to the root
        root.getChildren().addAll(topRightButton, timerBackground, topRightLabel);

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
        int millis = (int) (elapsedTime.toMillis() % 1000);
        topRightLabel.setText(String.format(Constant.TIMER_FORMAT, minutes, seconds, millis));
    }


    public void startGame() {
        background.requestFocus();
        timeline.play();
        gameTimer.start();
    }

    private void showPauseScreen() {
        if (timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.pause();
            gameTimer.stop(); // Pause the game timer as well
        }
    
        PauseScreen pauseScreen = new PauseScreen(
            (Stage) scene.getWindow(),
            () -> {
                timeline.play();
                gameTimer.start(); // Resume the game timer
            },
            () -> returnToMainMenu((Stage) scene.getWindow())
        );
        pauseScreen.show();
    }
    

    public void setLevelChangeListener(LevelChangeListener listener) {
        this.listener = listener;
    }

    protected void initializeFriendlyUnits() {
        root.getChildren().add(getUser());
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

    protected void updateScene() {
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
        updateTopRightLabel(); // Update the timer
    }
    

    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(Constant.MILLISECOND_DELAY), e -> updateScene());
        timeline.getKeyFrames().add(gameLoop);
    }

    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setOnKeyPressed(this::handleKeyPressed);
        background.setOnKeyReleased(this::handleKeyReleased);
        root.getChildren().add(background);
    }
    
    private void handleKeyPressed(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == Constant.KEY_UP) user.moveUp();
        if (kc == Constant.KEY_DOWN) user.moveDown();
        if (kc == Constant.KEY_LEFT) user.moveLeft();
        if (kc == Constant.KEY_RIGHT) user.moveRight();
        if (kc == Constant.KEY_FIRE) fireProjectile();
    }
    
    private void handleKeyReleased(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == Constant.KEY_UP || kc == Constant.KEY_DOWN) user.stopVertical();
        if (kc == Constant.KEY_LEFT || kc == Constant.KEY_RIGHT) user.stopHorizontal();
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
		gameTimer.stop();
		String timeUsed = formatElapsedTime();
        saveToLeaderboard(timeUsed);
        levelView.showWinImage();
        addQuitEventHandler(timeUsed);
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

    protected void loseGame() {
        timeline.stop();
		gameTimer.stop();
		String timeUsed = formatElapsedTime();
        levelView.showGameOverImage();
        addQuitEventHandler(timeUsed);
    }

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

	private String formatElapsedTime() {
        Duration elapsedTime = gameTimer.getElapsedTime();
        int minutes = (int) elapsedTime.toMinutes();
        int seconds = (int) elapsedTime.toSeconds() % 60;
        int millis = (int) (elapsedTime.toMillis() % 1000);
        return String.format("%02d:%02d.%03d", minutes, seconds, millis);
    }

    private void restartGame(Stage stage) {
        // Stop the current timeline to prevent further updates
        timeline.stop();
		gameTimer.reset();
		
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
        timeline.stop();
		gameTimer.reset();
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
