// package com.example.demo.controller;

// import com.example.demo.manager.MusicPlayer;
// import javafx.fxml.FXML;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.Slider;
// import javafx.scene.image.ImageView;

// public class SettingsController {

//     @FXML
//     private ImageView backgroundImage;

//     @FXML
//     private Slider volumeSlider;

//     @FXML
//     private Slider effectVolumeSlider;

//     @FXML
//     private Button volumeDecrementButton;

//     @FXML
//     private Button volumeIncrementButton;

//     @FXML
//     private Button effectVolumeDecrementButton;

//     @FXML
//     private Button effectVolumeIncrementButton;

//     @FXML
//     private Button muteButton;

//     @FXML
//     private Button backButton;

//     private boolean isMuted = false;
//     private Runnable onBackAction;

//     /**
//      * Initializes the settings page components and their event handlers.
//      */
//     @FXML
//     public void initialize() {
//         // Set up volume slider listener
//         volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
//             if (!isMuted) {
//                 MusicPlayer.setVolume(newValue.doubleValue() / 100.0);
//             }
//         });

//         // Set up sound effect volume slider listener
//         effectVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
//             MusicPlayer.setSoundEffectVolume(newValue.doubleValue() / 100.0);
//         });

//         // Set up volume decrement button
//         volumeDecrementButton.setOnAction(event -> volumeSlider.setValue(Math.max(0, volumeSlider.getValue() - 5)));

//         // Set up volume increment button
//         volumeIncrementButton.setOnAction(event -> volumeSlider.setValue(Math.min(100, volumeSlider.getValue() + 5)));

//         // Set up effect volume decrement button
//         effectVolumeDecrementButton.setOnAction(event -> effectVolumeSlider.setValue(Math.max(0, effectVolumeSlider.getValue() - 5)));

//         // Set up effect volume increment button
//         effectVolumeIncrementButton.setOnAction(event -> effectVolumeSlider.setValue(Math.min(100, effectVolumeSlider.getValue() + 5)));

//         // Set up mute button
//         muteButton.setOnAction(event -> toggleMute());

//         // Set up back button
//         backButton.setOnAction(event -> {
//             if (onBackAction != null) {
//                 onBackAction.run();
//             }
//         });
//     }

//     /**
//      * Toggles the mute status for the music player.
//      */
//     private void toggleMute() {
//         isMuted = !isMuted;
//         if (isMuted) {
//             MusicPlayer.setVolume(0.0);
//             muteButton.setText("Unmute");
//         } else {
//             MusicPlayer.setVolume(volumeSlider.getValue() / 100.0);
//             muteButton.setText("Mute");
//         }
//     }

//     /**
//      * Sets the action to execute when the back button is clicked.
//      *
//      * @param onBackAction A {@link Runnable} representing the back action.
//      */
//     public void setOnBackAction(Runnable onBackAction) {
//         this.onBackAction = onBackAction;
//     }
// }
