package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * This class has the responsibility to handle user input(mouse events),
 * constructs game, and calculate and update game data by delegating the task to game engine methods.
 *	
 * Added by Min: 
 * Date: 27/10/2018
 * This is the Main class to run Pool Game for COMP9201 Assignment Stage 2.
 * 
 * This is an extension from the codebase that was given for the assignment.
 * Comments that were added by me throughout the program starts with "Min:"
 * 
 * How to run the game: This program takes one command line argument which is the path to the configuration file.
 * Default configuration file is saved in src/application/Stage_2_config.json. 
 * If no argument is provided, default configuration file will be used. 
 * 
 */
public class Main extends Application {
	
	//Min: public variable filePath has been moved inside the main method.
	
	static GameEngine gameEngine; //Min: Changed the variable name to from config to gameEngine because it shows what the class does better
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = gameEngine.getScene(); //Min: Instead of creating the scene in Main, get the scene from GameEngine.
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Display graphic user interface and delegate handling responsibilities to respective methods
	 * @param args: file path
	 */
	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				String filePath = args[0];
				gameEngine = new GameEngine(filePath); 
				launch(args);
			} else {
				
				gameEngine = new GameEngine("src/application/Stage_2_config.json"); //Min: Added default configuration file for better exception handling.
				launch(args);
			}
			
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.err.println("Please specify the file path");
		}
		
	}
}