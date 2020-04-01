package application;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 * This class has the responsibility to build balls.
 * It contains the ArraryList of Ball objects. 
 *
 */
public class BallConfiguration extends Configuration {
	
	private CueBall cueBall; //Min added.
	private ArrayList<BallComponent> ballList;
	
	static BallDirector director = new BallDirector();
	
	
	/**
	 * Min: Constructor of the BallConfiguration class.
	 * 
	 * @param jsonBalls
	 */
	BallConfiguration(JSONObject jsonBalls) {
		ballList = new ArrayList<BallComponent>();
		readData(jsonBalls);
		
	}
	
	/**
	 * Read values from the ball jsonObject.
	 * @param jsonObject for the ball that was read in and passed from ConfigurationProducer
	 */
	@Override
	void readData(JSONObject jsonObject) {
	
		// reading the "Balls: ball" array:
		JSONArray jsonBallArray = (JSONArray) jsonObject.get("ball"); //Min: changed the name from jsonBallsBall to jsonBallArray
		// reading from the array:
		BallComponent ball;
		// Create ball object for every ball
		for (Object obj : jsonBallArray) {
			JSONObject jsonBall = (JSONObject) obj;
			ball = getBall(jsonBall);
			ballList.add(ball);
			if (ball.getColour().equalsIgnoreCase("white")) { //Min: changed to equalsIgnoreCase
				setCueBall(ball);
			}
			
		}
		setRadius(ballList);
		
	
	}
	
	
	
	/**
	 * Based on the passed information, this method delegates the ball building task to respective builder class
	 * @param jsonBall
	 */
	public static BallComponent getBall(JSONObject jsonBall) { //Min: changed access modifier from public to private
		BallBuilder builder;
		String colour = (String)jsonBall.get("colour");
		if(colour.equalsIgnoreCase("white")) {
			builder = new CueBallBuilder(jsonBall); //Min: added an argument
			director.constructBall(builder);
			return builder.getResult();
		}
		else  {
			builder = new PoolBallBuilder(jsonBall);
			director.constructBall(builder);
			return builder.getResult();
		}
	}
	
	/**
	 * Min: Get a ball list created in this class
	 * @return ballList
	 */
	ArrayList<BallComponent> getBalls() {
		return ballList;
	}
	
	/**
	 * Min: Set a ball as a cue ball
	 * @param ball
	 */
	private void setCueBall(BallComponent ball) {
		this.cueBall = (CueBall) ball;
	}
	
	/**
	 * Min: Loop through the ball list and set radius to the balls 
	 * @param ballList
	 */
	private void setRadius(ArrayList<BallComponent> ballList) {
		for (BallComponent bigBall : ballList) {
			if (bigBall.getComponentList() != null) {
				for (BallComponent smallBall : bigBall.getComponentList()) {
					double smallBallRadius = bigBall.getRadius() * 0.66;
					smallBall.setRadius(smallBallRadius);
					smallBall.updateViewRadius(smallBallRadius);
					
					smallBall.updateViewRadius(smallBallRadius);

					if (smallBall.getComponentList() != null) {
						setRadius(bigBall.getComponentList()); //Set radius recursively to the component balls
					}
					
				}
			}
		}
	}
	
	

}
