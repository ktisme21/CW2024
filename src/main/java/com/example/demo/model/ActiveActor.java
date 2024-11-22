package com.example.demo.model;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class ActiveActor extends ImageView {

	private final Rectangle redContainer;
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	// Shrink factor for reducing the collision sensitivity
	private static final double SHRINK_FACTOR = 0.5; // 80% of the original size

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		// Load image
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);

		// Initialize red container (collision box)
		this.redContainer = new Rectangle();
		this.redContainer.setStroke(Color.RED);
		this.redContainer.setFill(Color.TRANSPARENT);
		this.redContainer.setStrokeWidth(2);
		updateRedContainerPosition();
	}

	private void updateRedContainerPosition() {
		// Calculate reduced width and height based on SHRINK_FACTOR
		double reducedWidth = getBoundsInParent().getWidth() * SHRINK_FACTOR;
		double reducedHeight = getBoundsInParent().getHeight() * SHRINK_FACTOR;

		// Center the smaller red container within the original bounds
		double offsetX = (getBoundsInParent().getWidth() - reducedWidth) / 2;
		double offsetY = (getBoundsInParent().getHeight() - reducedHeight) / 2;

		redContainer.setWidth(reducedWidth);
		redContainer.setHeight(reducedHeight);
		redContainer.setX(getLayoutX() + getTranslateX() + offsetX);
		redContainer.setY(getLayoutY() + getTranslateY() + offsetY);
	}

	public abstract void updatePosition();

	public abstract void updateActor();

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

	public void updateRedContainer() {
		updateRedContainerPosition();
	}

	// Getter for the red container
	public Rectangle getRedContainer() {
		return redContainer;
	}

	// Add the red container to the scene graph
	public void addRedContainerToRoot(Group root) {
		if (!root.getChildren().contains(redContainer)) {
			root.getChildren().add(redContainer);
		}
	}

	// Remove the red container from the scene graph
	public void removeRedContainerFromRoot(Group root) {
		if (root.getChildren().contains(redContainer)) {
			root.getChildren().remove(redContainer);
		}
	}
}
