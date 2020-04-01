package application;

/**
 * This class is the director class for creation of teh ball object.
 * It defines constructBall method which contains a set of steps to build a ball object.
 *
 */
public class BallDirector {
	
	/**
	 * This methods delegates the task of building balls to a builder and
	 * let that builder build a ball with given data read from jsonBall object
	 * @param builder: a builder object with the knowledge of how to build a ball
	 */
	public void constructBall(BallBuilder builder) { //MJ deleted separate constructor for cue and pool ball
		builder.setColour();
		builder.setpositionX();
		builder.setpositionY();
		builder.setvelocityX();
		builder.setvelocityY();
		builder.setmass();
		builder.setRadius();
		builder.setStrength();
		builder.setView();
	}
	

}
