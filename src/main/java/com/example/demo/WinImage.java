package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);

		// pressAnyKeyLabel = new Label("Press any key to continue...");
		// pressAnyKeyLabel.setFont(new Font("Arial", 24));
        // pressAnyKeyLabel.setTextFill(Color.RED);
        // pressAnyKeyLabel.setVisible(false);
	}
	
	public void showWinImage() {
		this.setVisible(true);
		// pressAnyKeyLabel.setVisible(true);

		// // Listen for any key press to continue to the end screen
        // stage.getScene().setOnKeyPressed(event -> {
        //     // Hide the win image and text
        //     this.setVisible(false);
        //     pressAnyKeyLabel.setVisible(false);

        //     // Proceed to the end screen
        //     stage.setScene(endScreenScene);
        //     stage.getScene().setOnKeyPressed(null); // Remove the event handler after proceeding
        // });


	}



}
