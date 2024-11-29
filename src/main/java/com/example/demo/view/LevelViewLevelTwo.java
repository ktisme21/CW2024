// package com.example.demo.view;

// import com.example.demo.display.ShieldImage;
// import com.example.demo.utilities.Constant;

// import javafx.scene.Group;

// public class LevelViewLevelTwo extends LevelView {

// 	private final Group root;
// 	private final ShieldImage shieldImage;
	
// 	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
//         super(root, heartsToDisplay);
//         this.root = root;
//         this.shieldImage = new ShieldImage(Constant.SHIELD_X_POSITION, Constant.SHIELD_Y_POSITION);
//         addImagesToRoot();
//     }
	
// 	private void addImagesToRoot() {
// 		root.getChildren().addAll(shieldImage);
// 	}
	
// 	public void showShield() {
// 		shieldImage.showShield();
// 	}

// 	public void hideShield() {
// 		shieldImage.hideShield();
// 	}

// }