package com.example.demo.display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	public GameOverImage(double xPosition, double yPosition) {
		// Load the image directly
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		
		// Set layout positions
		setLayoutX(xPosition);
		setLayoutY(yPosition);
		
		// Set default dimensions (optional)
		setFitWidth(750);  // Adjust as needed
		setFitHeight(600); // Adjust as needed
		setPreserveRatio(true); // Preserve aspect ratio
	}
}
