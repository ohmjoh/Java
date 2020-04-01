package application;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * This is a pool ball class which are coloured ball other than a white ball. 
 *
 */
public class PoolBall extends Ball {
	
	//Min: Deleted the constructor to prevent direct construction with 'new' operator.


	@Override
	public String toString() {
		return "TargetBall [colour=" + colour + ", xPosition=" + xPosition + ", yPosition=" + yPosition + ", xVelocity="
				+ xVelocity + ", yVelocity=" + yVelocity + ", mass=" + mass + ", radius=" + radius + "]";
	}
	
	
	/**
	 * This method is for composite ball.
	 */
	public ArrayList<BallComponent> getComponentList() {
		return null;
	}

}
