# SkyBattle

## About
This project focuses on refactoring and enhancing an existing retro game, "1942" to meet the requirements outlined by Dr. Tan for the module Developing Maintainable Software. The main goal is to maintain, extend, and improve the provided codebase while following different design patterns like SRP and Layered Architecture, etc. The final implementation should also fix existing bugs, improve gameplay and adhere to good software engineering practices.

## Introduction
SkyBattle is an intense combat game that let players control advanced fighter planes to battle waves of enemies and powerful bosses. Designed for both casual and competitive gamers, it features multiple gameplay modes, strategic power-ups and precise shooting mechanics. With its immersive visuals, dynamic leaderboard and customizable settings, SkyBattle delivers an action-packed gaming experience.

## New Classes Added
#### 1. MainMenu.java
#### Key Features: 
- Provides the initial screen with options to start the game, enter endless mode, access settings or quit.
- Each corresponding event handler passed as a parameter to handle user actions.
- Acts as the entry screen for the application, providing navigation to other parts of the game.
- Use: Implements the Single Responsibility Principle (SRP) by separating the starting logic from gameplay.

#### 2. SettingsPage.java
#### Key Features: 
- Allows users to adjust sound effects and background music using sliders and mute/unmute buttons. Back button for navigating back to the main menu.
- Use: Provides user control over the audio experience for a customized gameplay environment.

#### 3. PauseScreen.java
#### Key Features: 
- Enables pausing the game with options to resume or quit game.
- Includes real-time volume adjustment for background music and sound effects.
- Pop-up Screen
- Use: Enhances user experience by offering dynamic control during gameplay.

#### 4. LeaderBoard.java & LeaderboardManager.java
#### Key Features: 
- Displays the top 5 players based on time-based rankings stored in a leaderboard.txt file.
- Create a box to display the leaderboard title and top scores. Fetches top scores from LeaderboardManager.java (LeaderBoard.java).
- Handles game state, including spawning enemies, collision detection and updating the UI (LeaderboardManager.java).
- Use: Keeps players motivated by introducing competition through rankings.

#### 5. MusicPlayer.java & BackgroundUtil.java
#### Key Features: 
- Manages background music and sound effects like shooting & sets a consistent background image for all screens.
- Set the dimensions to 1300x750, use Constant.BACKGROUND_IMAGE_PATH for the image path
- Use: Centralizes audio control for consistency and scalability & enhance UI consistency

#### 6. GlobalGameTimer.java
#### Key Features: 
- Tracks gameplay time in milliseconds, used for leaderboard rankings and other gameplay features.
- Make it accessible across different levels and components to maintain a consistent time reference.
- Converts elapsed time into a readable format (MM:SS:MS) used for leaderboard display by using method formatElapsedTime().

#### 7. LevelThree.java
#### Key Features:
- Introduces new gameplay mechanics, such as invisibility mode triggered by pressing 'D'.
- Players cannot attack while in invisible mode, but can avoid fire projectile from bosses while in invisible mode.
- Use: Adds complexity and depth to gameplay

#### 8. Manager Classes (InputManager, ProjectileManager, UIManager)
#### Key Features:
- Handle specific aspects of the game mechanics
- InputManager: Processes user input for movement, shooting and special abilities. (setupInputHandlers, handleKeyPress) methods
- ProjectileManager: Manages user and enemy projectiles, including their trajectories and collision detection. Implements the shrink factor for collision bounds to improve accuracy. (generateEnemyFire - inrandom, removeInvisibleProjectiles) methods
- UIManager: Handles transitions between the game and pause screen, including preserving game state. Updates the timer, score, health display and other UI components.
- Use: SRP and Layered Architecture ensure each manager focuses on a single task

## Removed Files
#### 1. LevelViewLevelTwo.java
#### Key Features:
- Removed due to redundancy; its functionality was integrated into LevelTwo.java.
- Move ShieldImage logic into the LevelTwo class by adding addShieldImage() method to handle shield initialization
- Separate addImagesToRoot() as it can be generalized and managed by a higher-level class.
- Use: Centralize the code base for better readability and maintainability.

#### 2. LevelParent.java
#### Key Features:
- Functionality previously encapsulated in LevelParent.java and it has been replaced with modular manager classes to adhere to SRP such as InputManager.java, ProjectileManager.java, UIManager.java
- Use: Reduces file size and complexity


## Refactoring Changes
#### 1. Bugs
* ShieldImage
- The ShieldImage object was not instantiated or properly added to the scene graph, resulting in it not being rendered during gameplay.
- File path wrote wrongly. (jpg -> png)
- Solution: Dynamically position the shield relative to the boss and integrate animation ()

* Transitioning Logic (isTransitioned, hasNotified)
- In the previous implementation, level-loading logic was triggered multiple times due to a lack of proper state management.
- Introduced the isTransitioned and hasNotified flags to ensure level-loading logic is executed only once per transitions.
- Prevents duplicate level-loading logic from causing errors by adding if (!hasNotified && listener != null).

#### 2. Replace Observer and Observable with EventListener
- Use: Observer is outdated. EventListener improves event handling by decoupling the listener logic.
- Implementing LevelChangeListener to define specific actions when a level transition occurs

#### 3. Enhance Projectile Collision
- Added logic to shrink collision bounds without affecting the visual size (shrinkfactor() method)
- Applied these bounds during collision checks in handleUserProjectileCollisions() in CollisionManager. 
- Use: Makes collisions more accurate and intuitive

#### 4. Create Constants.java
- Repeated use of hardcoded values (e.g., screen dimensions, file paths) made the code less readable and harder to maintain.
- Centralizes frequently used values like screen dimensions and file paths.
- Call Constant.(variable name) when the value is need to be shown.

#### 5. Use Modular Packages
- Grouping related classes into packages promotes separation of concerns and adheres to the Layered Architecture pattern.
- Divided code into packages like utilities, model, view, etc.
- Use: Improves code organization and makes it easier to navigate and maintain.

## Appearance Refactor
#### 6. Adjust Lower Bound of Boss
- Boss' lower movement restricted, affecting gameplay visibility.
- Solution: Dynamically set by adjusting the LOWER_BOUND_Y in Constant.java.

#### 7. Y Position
- Userplane doesnt equally tranverse the screen.
- Solution: Adjust the value of y position in Constant of userplane

#### 8. Image Improvement
- ShieldImage and FireProjectile and Boss lacks clarity.
- Solution: Replace the model with a higher-resolution, transparent version
- use https://www.remove.bg/upload to improve the quality of the .png

#### 9. Accurate Fire Shooting
- Overly sensitive shooting mechanics
- Solution: Create shrink factor for the collision bounds getCollisionBounds() in enemy plane
- Creating red container can visually indicate the boundaries of each actor

#### 10. Full-Screen User Plane Control
- User Plane restricted to control the plane vertically
- Allow movement across the entire screen using dynamic bounds
- Solution: Declare X_LEFT_BOUND & X_RIGHT_BOUND in Constant and apply it in User plane.java


## Additional Features
#### 1. Level Adjustments:
* Level 3
- Introduce invisibility mode. Triggered by pressing 'D'. 
- When 'D' is pressed, the p;ayer's plane becomes invisible, dodging enemy projectiles but unable to shoot. Pressing 'D' again reverts the plane back to visible mode.
- Adds multiple bosses for increased difficulty. Make the player has multiple target.

#### 2. Audio Improvements
* MusicPlayer.java
- Allows real-time volume adjustment and integrates shooting effects
* SettingsPage
- Provides sliders for adjusting audio levels. Allows users to customize audio preferences dynamically without pausing the game.

#### 3. UI Enhancements
* ShieldImage
- Added blinking animations to indicate activation
- Made semi-transparent while blinking
- Animation logic: A timeline alternates the visibility and opacity of the shieldimage at regular intervals. Timeline is used to schedule repeated changes (visibility and opacity) at intervals of 300 milliseconds. The setCycleCount(8) makes the animation runs for eight cycles. At the end of the animation, the shield is set back to fully visible, and isBlinking is reset to allow future activations.
* Background Images
- Centered using StackPane for consistent alignment across screens. Ensures consistent alignment of background images across all screens.


## New Gameplay Mechanics
#### 1. Level Three:
- Introduced invisibility mode where players can dodge attacks but cannot shoot.
- Visibility state is tracked using boolean variable (isVisible). Collision detection between the player and enemy projectiles is disabled.
- Shooting actions are disabled to balance the strategic advantage of being invulnerable. (Message will be send during the game)
- Use: Adds strategic depth to the gameplay.

#### 2. Endless Mode:
- Allows unlimited playtime with increasing difficulty.
- Use: Appeals to players seeking a continuous challenge.

#### 3. Leaderboard:
- Time-based rankings motivate players to complete levels faster.
- Saved and loaded using the LeaderboardManager class. (loadLeaderboard()).
- Add every entry only if players win the game. If the leaderboard.txt doesn't exists, then create a new one and save it in a new txt file.
- Use: Provides a sense of accomplishment and replayability.


## Achievements
#### 1. Visible mode in Level 3
- Adds the ability to toggle visibility for strategic gameplay

#### 2. Time-Based Leaderboard
- Tracks the fastest completion times for competitive gameplay


## Problems and Solution
#### 1. Timer Stops Randomly:
- GlobalGameTimer occasionally stops randomly during LevelTwo and LevelThree gameplay. Likely caused by conflicts between the blinking animations (startShieldBlinkAnimation) and the global timer. Both may be running on the same JavaFX thread, causing thread contention or delays.
- Possible Solution: Decouple timer logic from animations / Optimize Animations
- Use a dedicated thread or task for the timer to avoid conflicts. Running GlobalGameTimer using a ScheduledExecutorService instead of replying solely on JavaFX's Animation threads

#### 2. Screen Alignment Issues:
- After returning to certain classes or screens, the UI alignment becomes misaligned.
- This is due to hardcoded dimensions and possible SDK version issues when handling JavaFX layouts.
- Possible solution: The SDK version could introduce subtle layout changes if there are compatibility issues.


## Feature would like to add
#### Input and Record Players' Names in LeaderboardManager.java
- The LeaderboardManager currently records scores but lacks functionality for player name input. 
- Steps: Add a name input field for players to enter their names. Next, modifying the method for adding leaderboard entries to accept both the player's name and score. Ensure names and scores are saved together in leaderboard.txt.


## Design Patterns Used
#### 1. Singleton:
- GlobalGameTimer: Implements the Singleton Pattern, ensuring only one instance of the timer exists across the entire application.
- Maintains a single state for gameplay duration, which can be shared across different levels or screens.
- Advantages: Centralized timer logic prevents conflicts, especially when transitioning between levels or screens.

#### 2. Factory Method: Used for creating projectiles and other game objects dynamically.
- Creates objects dynamically without specifying their exact classes, improving flexibility and scalability.
- Used for creating projectiles (e.g., BossProjectile, UserProjectile) and other dynamic objects like enemies or shields.
- Advantages: Centralized timer logic prevents conflicts, especially when transitioning between levels or screens.

#### 3. Layered Architecture:
- Divides the application into distinct layers such as utilities, view, and model, each responsible for a specific functionality.(e.g., utilities, view, model).
- Advantages: Promotes clean architecture, making the application easier to maintain. 

#### 4. SRP:
- A class should have one and only one reason to change, focusing on a single responsibility.
- By dividing responsibilities, each manager focuses on its specific task, making the code more modular and easier to debug.
- Split LevelParent into multiple manager classes for modular design.
- Advantages: Reduces file size and complexity, making the codebase easier to navigate and extend.


## Packages and methods
#### Controller
- **Controller**: Manages game-wide logic and coordinates transitions between different views
- **Main**: Entry point of the application. Initializes and launches the JavaFX application

#### Data
- **leaderboard.txt**: Stores player scores for leaderboard functionality

#### Display
- **GameOverImage**: Display the "Game Over" image when the player loses.
(showGameOverImage()- display game over image)
---
- **HeartDisplay**: Represents the player's health in terms of hearts on the UI
(removeHeart() - removes a heart from the display when the player takes damage)
(heartDisplay() - display heart in the screen)
---
- **ShieldImage**: Display the shield around the boss or player
(showShield() - makes the shield visible)
(startShieldBlinkAnimation() - creates a blinking effect when the shield is activated)
---
- **WinImage**: Display the "You Win" image when the player completes a level
(showWinImage() - displays the win screen)
---

#### Level
- **EndlessMode** - Implements the endless gameplay mode where the game continues indefinitely with increasing difficulty
(spawnEnemyUnits() - spawns enemies in waves with increasing intensity)
---
- **LevelOne, LevelTwo, LevelThree**
(startShieldBlinkAnimation() - adds blinking animations to the boss's shield)
(updateLevelView() - Updates the position and visibility of level elements like shields)
(toggleInvisibility() - enables invisibility mode for the player)
---

#### Manager
- **CollisionManager** - Detects and handles collisions between entities (projectiles, enemies and the player)
(handlePlaneCollisions() - manages collisions between planes)
(handleProjectileCollisions() - handles projectile collisions)
---
- **GlobalGameTimer** - Singleton class that tracks the game's total elapsed time
(start() - starts the timer)
(reset() - resets the timer to zero)
---
- **InputManager** - Manages player input (movement and shooting)
(setupInputHandlers() - maps key presses to game actions like movement of firing projectiles)
---
- **Leaderboard** - Manages leaderboard data, including saving and retrieving scores.
(getTopEntries() - Retrieves the top player scores)
---
- **LevelManager** - Abstract class managing common functionalities across all levels
(goToNextLevel() - Transitions to the next level when the current level is completed)
---
- **MusicPlayer** - Manages background music and sound effects
(startMusic() - Plays background music)
(playShootingSound() -  Plays the sound effect for shooting)
---
- **ProjectileManager** - Manages projectiles fired by the player and enemies
(generateEnemyFire() - Handles enemy firing logic)
(removeInvisibleProjectiles() - Clears projectiles that leave the screen)
---
- **UIManager** - Handles UI elements like timers and health displays
(updateTimer(String time) - Updates the timer display in the UI)


#### Mechanics
- **Destructible** - Abstract class or interface for entities that can be destroyed
(takeDamage() - Reduces the entity's health or durability)
(isDestroyed() - Checks if the entity is destroyed)

#### Model
- **ActiveActor, ActiveActorDestructible** - Base classes for entities that can perform actions and be destroyed
(updateActor() - Update the entity's state during the game loop)
---
- **Boss** - Represents a boss enemy in the game
(isShielded() - Checks if the boss has an active shield)
---
- **EnemyPlane** - Represents enemy planes
(fireProjectile() - Fires projectiles at the player)
--
- **Fighter plane, User plane** - Represents the player's plane
(move() - Moves the plane in response to player input)

#### Projectiles
- **BossProjectile, EnemyProjectile, Projectile, UserProjectile** - Represent projectiles fired by various entities
(updateActor() - Updates projectile movement)
(detectCollision() - Checks for collisions with other entities)

#### Utilities
- **BackgroundUtil** - Sets consistent background images across all screens
(setBackgroundImage(Pane pane) - Applies a background image to the specifies pane)
---
- **Constant** - Contains constants like screen dimensions, file paths and styling options


#### View
- **Leaderboard** - Displays the leaderboard UI.
(createLeaderboardBox() - Applies a background image to the specifies pane)
---
- **LevelView** - Abstract class for visual representations of levels.
(showHeartDisplay() - Displays the player's health.)
---
- **MainMenu** - Displays the main menu UI.
(createButton(): Dynamically generates buttons for the menu.)
---
- **PauseScreen** - Displays the pause menu.
(toggleMute() - Mutes or unmutes the background music.)
---
- **SettingsPage** - Allows users to adjust volume and sound effects.
(createVolumeSlider() - Creates sliders for audio adjustments.)


## Git
- fork from kooitt/CW2024 - https://github.com/kooitt/CW2024.git
- code recorded in - https://github.com/ktisme21/CW2024.git
