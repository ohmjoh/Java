package application;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
/**
 * The game engine class is responsible for storing game information, reading configurations,
 * as well as calculate and store ball and table properties.
 *
 */
public class GameEngine {
	private Pane root;
	private TableConfiguration tableConfig;
	private BallConfiguration ballConfig;
	static boolean dragged = false;
	static ArrayList<BallComponent> ballList = new ArrayList<BallComponent>();
	private Table table;
	private Rectangle playArea;
	private CueBall cueBall;
	private Double cueBallDeltaX;
	private Double cueBallDeltaY;
	private ToggleButton toggle = new ToggleButton("Show balls");
	private Button undo = new Button("Undo");
	private Button reset = new Button("Reset");
	private MementoCaretaker caretaker;
	private int savedStates = 0;
	private int currentState = 0;
	private ArrayList<BallComponent> toggledBalls = new ArrayList<BallComponent>();
	private String filePath;
	
	/**
	 * This constructor takes a filePath name and load game information
	 * stored in the given file
	 * @param filePath: file path to read configuration from
	 */
	public GameEngine(String filePath) {  //Min: Moved constructor to the top
		this.filePath = filePath;
		caretaker = new MementoCaretaker(); //Min: Create caretaker object for memento
		
		ConfigurationProducer configProducer = new ConfigurationProducer(filePath);// Min: Create ConfigurationProducer object
		tableConfig = new TableConfiguration(configProducer.getJsonTable()); //Min: This three lines of code was taken from the readConfig() method that is now moved to ConfigurationProdcuer
		ballConfig = new BallConfiguration(configProducer.getJsonBalls()); //Min
		
		//Min: get ball list and table object
		ballList = ballConfig.getBalls();
		table = tableConfig.getTable();
		
		//Min: call game logic methods
		createScene();
		hitCueBall(cueBall);
		toggleVisibility();
		handleButtons();

	}
		
	/**
	 * Min: Get the scene which includes table, pockets, and balls
	 * This method is called in Main method, and the output is added in the primary stage.
	 * @return Scene object
	 */
	public Scene getScene() {
		return new Scene(root);
	}
	
	
	//Min: This method is moved from Main class
	/**
	 * Create the content to be displayed in the stage. Delegates hitting cue ball and
	 * update on balls to respective Event Handlers and methods
	 * 
	 * 
	 */
	private void createScene() { //Min: Hid game logic in the private method
		
		root = new Pane();
		root.setPrefSize(table.getX(),table.getY());
		
		
		// Min: Adding table, balls, and pockets into display
		playArea = table.getView();
		
		try {
			playArea.setX(((ImageTable) table).getPlayAreaX());
			playArea.setY(((ImageTable) table).getPlayAreaY());
			root.getChildren().add(((ImageTable)tableConfig.getTable()).getTableImage());
		} catch (Exception e1) {
			System.out.println("Color Table is created");
		}
		
		// Min: Add toggle button
		root.getChildren().add(playArea);
		toggle.setTranslateX(110);
		toggle.setTranslateY(5);
		root.getChildren().add(toggle);
		
		// Min: Add undo, reset buttons
		undo.setTranslateX(460);
		undo.setTranslateY(5);
		root.getChildren().add(undo);
		reset.setTranslateX(520);
		reset.setTranslateY(5);
		root.getChildren().add(reset);
		
		
		// Min: Add pockets
		for (Pocket p : tableConfig.getPockets()) {
			root.getChildren().add(p.getView());
		}
		
		// Add balls
		for(BallComponent i : ballList) {			
			root.getChildren().add(i.getView());
			if(i.getColour().equalsIgnoreCase("white")) { //Min: changed 'equals' to 'equalgsIgnoreCase'
				cueBall = (CueBall) i;
			}
		}
		
		// Get the boundaries of the table
		Bounds tableBounds = playArea.getBoundsInLocal(); //Min: get only the play area's boundary 		
	
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				PhysicsEngine physicsEngine = new PhysicsEngine(table, tableBounds);
				fallPocket();
		
				// Min: Because ballList is updated when the balls fall into the pocket or when undo/reset button is hit, we need to draw from the new ball list in the animation timer.
				for(BallComponent b : ballList) {
					if (!root.getChildren().contains(b.getView())) { // Min: prevent duplicates
						root.getChildren().add(b.getView());
					}
				}
				if(toggle.isSelected()==true){ // Min: If the visibility button is on, the small balls should be inside the big ball and follow the big ball. drawToggledBalls implements that behaviour.
					for (BallComponent b : ballList) {
						if (b.getComponentList() != null) {
							drawToggledBalls(b);
			
						}
					}
				}

			}
		};
		timer.start();	
	}
	
	/**
	 * Min: draw balls that are inside the toggled ball list. 
	 * This function gets executed inside the animation timer so that the toggled small balls can move around inside the big balls.
	 * 
	 * @param b: BallComponent object
	 */
	private void drawToggledBalls(BallComponent b) {
		for (BallComponent ball : b.getComponentList()) {
			if (toggledBalls.contains(ball)) {
				root.getChildren().remove(ball.getView());
				ball.updateViewPosition(ball.getxPosition()+ b.getView().getCenterX(), ball.getyPosition() + b.getView().getCenterY());
				root.getChildren().add(ball.getView());
				
			}
			if (ball.getComponentList() != null && ball.getComponentList().size()>0) {
				drawToggledBalls(ball); //call the function recursively for smaller balls inside the small balls
			}
		
		}
	}
	
	/**
	 * Min: Define behaviors when the white cue ball is hit by the cue stick.
	 * A cue stick appears when mouse enters.
	 * When the cue ball is clicked and dragged, a guide line appears, 
	 * and when it is released, the cue ball moves with the velocity proportionaal to the distance of the dragged line.
	 * @param ball 
	 */
	private void hitCueBall(CueBall ball) {
		Image cueImage = new Image("resources/pool-stick.png"); 
		Line line = new Line(); //for guideline
		
		//event handler for mouse enter
		root.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				// cue stick appears when mouse enters
				root.setCursor(new ImageCursor(cueImage));
				
			}
			
		});
		
		//event handler for mouse drag
		ball.getView().setOnMouseDragged(new EventHandler<MouseEvent>() {
				
			@Override
			public void handle(MouseEvent event) {
					
					
				//if all balls has stopped moving
				boolean allStopped = true;
				for (BallComponent b : ballList) {
			        if (!b.atRest()) {
			            allStopped = false;
			    	}
			    }
			    
				if (allStopped) {
						
					Double mouseX = event.getSceneX();
					Double mouseY = event.getSceneY();
					
					//draw guide line
			        line.setStartX(ball.getView().getCenterX());
			        line.setStartY(ball.getView().getCenterY());
			        line.setEndX(mouseX);
			        line.setEndY(mouseY);
			        line.getStrokeDashArray().addAll(2d, 5d);
			        line.setStroke(Color.BROWN);
			        root.getChildren().remove(line);
			        root.getChildren().add(line);
		        
				    cueBallDeltaX = ball.getView().getCenterX() - mouseX;
				    cueBallDeltaY = ball.getView().getCenterY() - mouseY;
				        
				    // Draw  a cue stick
				    // Change its direction always pointing to the white cue ball when being dragged.
				    Image rotatedImage = rotateImage(getAngle(cueBallDeltaY, cueBallDeltaX), cueImage);
				        
				    if (cueBallDeltaY > 0 && cueBallDeltaX > 0) { // 1 quadrant
				        root.setCursor(new ImageCursor(rotatedImage, rotatedImage.getWidth(), rotatedImage.getHeight() ));
				    } else if (cueBallDeltaY > 0 && cueBallDeltaX < 0) { //2 quadrant
				    	root.setCursor(new ImageCursor(rotatedImage, 0, rotatedImage.getHeight() ));

				    } else if (cueBallDeltaY <=0 && cueBallDeltaX > 0) { //4 quadrant
				        root.setCursor(new ImageCursor(rotatedImage, rotatedImage.getWidth(), 0 ));

				    } else { //3 quadrant
				        root.setCursor(new ImageCursor(rotatedImage));

				    }
				        
				} 
			        
				}
				
			});
			// event handler for mouse release
			ball.getView().setOnMouseReleased(new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent event) {
					
					//if all balls has stopped moving
					boolean allStopped = true;
					for (BallComponent b : ballList) {
				        if (!b.atRest()) {
				            allStopped = false;
				    	}
				    }
					
					if (allStopped) { //if cue ball have stopped moving
						root.getChildren().remove(line);
						Bounds boundsInScreen = ball.getView().localToScreen(ball.getView().getBoundsInLocal());
						
						//move the cursor to the cue ball location to make the 'hitting' visual effect
						Robot robot;
						try {
							
							robot = new Robot();
							robot.mouseMove((int)boundsInScreen.getMaxX(), (int)boundsInScreen.getMaxY());
						} catch (AWTException e) {
				
							e.printStackTrace();
						} 
						
						//save current sate before the shot
						caretaker.addMemento(storeBalls());
						savedStates++;
						currentState++;
					
						
						//set velocity of the ball proportional to the dragged line length. 
						cueBall.registerShot(event.getSceneX(), event.getSceneY());
						
					}
				}
				
			});
		
	}
	
	/**
	 * Min: Rotate an image and returns the rotated image.
	 * This is for rotating the cue stick image according to its location compared to the white cue ball.
	 * Cue stick only rotates when it is being dragged(inside the mouse dragged event handler).
	 * @param degrees 
	 * @param img
	 * @return rotatedImage
	 */
	private Image rotateImage(double degrees, Image img) {
  
        ImageView iv = new ImageView(img);
        iv.setRotate(degrees);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = iv.snapshot(params, null);
       
		return rotatedImage;
	}
	
	/**
	 * Min: Calculate the degree to rotate the cue stick with and returns the degree.
	 * 
	 * @param cueBallDeltaY is the Y-coordinate distance between the cue ball and the mouse
	 * @param cueBallDeltaX is the X-coordinate distance between the cue ball and the mouse
	 * @return angle in degrees
	 */
	private double getAngle(double cueBallDeltaY, double cueBallDeltaX) {
		
		double angle = Math.toDegrees(Math.atan2(-cueBallDeltaY, -cueBallDeltaX));
		return angle;
	}
	
	/**
	 * Min: Remove all the current views of the balls
	 * This method is used inside the undo/reset button handlers to redraw the new ball positions
	 */
	private void removeBalls() {
		for(BallComponent i : ballList) {			
			root.getChildren().remove(i.getView());
		}
	}
	
	/**
	 * Min: Make small balls visible.
	 * This method is used inside the toggleVisibility() method
	 * @param ball is a BallComponent object
	 */
	private void showToggledBalls(BallComponent ball) {
		for (BallComponent b : ball.getComponentList()) {
			b.updateViewPosition(b.getxPosition()+ ball.getView().getCenterX(), b.getyPosition() + ball.getView().getCenterY());
			if (!root.getChildren().contains(b.getView())) {
				root.getChildren().add(b.getView());
				toggledBalls.add(b);
				if (b.getComponentList() != null && b.getComponentList().size()>0) {
					showToggledBalls(b); // Min: call the function recursively to draw the balls inside other balls
				}
			}

		}
	}
	
	/**
	 * Min: Handle the visibility toggle button.
	 * Show balls when toggled, hide balls when not toggled.
	 */
	private void toggleVisibility() {
		
		 toggle.selectedProperty().addListener(new ChangeListener < Boolean > (){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(toggle.isSelected()==true){
					toggle.setText("Hide balls");
					
					for (BallComponent ball : ballList) {
						if (ball.getComponentList() != null && ball.getComponentList().size()>0) {
							showToggledBalls(ball);
							
						}
					}
				}
				else{
                    toggle.setText("Show balls");
                    for (BallComponent ball : ballList) {
            			if (ball.getComponentList() != null && ball.getComponentList().size()>0) {
            				deleteSmallBalls(ball);
            				
            			}
                    }
				}
				
			}
	   
		   });
		
	}
	
	/**
	 * Min: Remove the view of the balls when a ball completely enters inside the pocket.
	 */
	private void fallPocket() {
		//check if the distance of the pocket center and the ball center is smaller than pocket radius minus ball radius
		for (Pocket p : tableConfig.getPockets()) {
			Point2D pocketCenter = new Point2D(p.getView().getCenterX(), p.getView().getCenterY());
			Iterator<BallComponent> it = ballList.iterator();
			while (it.hasNext()) {
				BallComponent ball = it.next();
				Point2D ballCenter = new Point2D(ball.getView().getCenterX(), ball.getView().getCenterY());
				double distance = pocketCenter.distance(ballCenter); // Calculate the distance of the centers of a ball and a pocket
				if (distance < p.getRadius() - ball.getRadius()) { // if a ball completely goes inside the pocket
					
					if (ball.getComponentList() != null && ball.getComponentList().size() > 0) {// if the ball has small balls inside it
						deleteSmallBalls(ball); // delete the small balls first
						
					}
				
					root.getChildren().remove(ball.getView()); //then delete the ball view
					it.remove(); //delete from the list
					
				}
			}
		}

	}
	
	/**
	 * Min: Delete the view of the small balls.
	 * This method gets called inside the toggleVisiibility() method.
	 * @param ball
	 */
	private void deleteSmallBalls(BallComponent ball) {
		for (BallComponent b: ball.getComponentList()) {
			root.getChildren().remove(b.getView());
			toggledBalls.remove(b);
			if (b.getComponentList() != null && b.getComponentList().size()>0) {
				deleteSmallBalls(b); //recursive
			}

		}
	}
	
	/**
	 * Min: Create a Memento.
	 * @return Memento object that contains the current ball list.
	 */
	public Memento storeBalls() {
		return new Memento(ballList);
	}
	
	
	/**
	 * Min: Get the ball list saved in Memento
	 * @param memento
	 * @return savedBalls. Saved ball list in Memento.
	 */
	public ArrayList<BallComponent> restoreBalls(Memento memento){
		ArrayList<BallComponent> savedBalls = new ArrayList<BallComponent>(memento.getSavedBalls());
		return savedBalls;
	}
	
	/**
	 * Min: Handles undo/reset buttons.
	 * Get most recently stored ball list when the undo button is pressed.
	 * Get the first stored all list and delete all saved list when the reset button is pressed. 
	 */
	private void handleButtons() {
		undo.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				toggle.setSelected(false);
				System.out.println("Undo button clicked");
				if (currentState >= 1) {
		    		currentState--;
		    		ArrayList<BallComponent> storedBallList = restoreBalls(caretaker.getMemento(currentState));
		    		removeBalls();
		    		ballList = storedBallList;
		    	
		    	}
				
			}

		});
		reset.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				toggle.setSelected(false);
				
				if (currentState >= 1) {
					System.out.println("Reset button clicked");
					currentState = 0;
					savedStates = 0;
		    		
		    		ArrayList<BallComponent> storedBallList = restoreBalls(caretaker.getMemento(currentState));
		    		caretaker.resetStates();
		    		removeBalls();
		    		ballList = storedBallList;
		    
				}	
			}
			
		});
		
	}
	
	
}
