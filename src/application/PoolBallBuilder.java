package application;

import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
/**
 * This class specifies the steps in building a non-white balls for the pool game.
 *
 */
public class PoolBallBuilder implements BallBuilder {
	private String colour;
	private double xPosition;
	private double yPosition;
	private double xVelocity;
	private double yVelocity;
	private double mass;
	private static double radius = 15;
	private double strength;
	private Circle view;
	private boolean isComposite = false;

	private BallComponent ball;

	/**
	 * Constructor of the PoolBallBuilder class
	 * Read values from balls jsonObject and assign values to the attributes.
	 * @param jsonBall is a JSONObject for the balls that was read in ConfigurationProducer
	 */
	public PoolBallBuilder(JSONObject jsonBall) {
		colour = (String)jsonBall.get("colour");
		xPosition = (Double) ((JSONObject) jsonBall.get("position")).get("x");
		yPosition = (Double) ((JSONObject) jsonBall.get("position")).get("y");
		if (isComposite = false) {
			xVelocity = (Double) ((JSONObject) jsonBall.get("velocity")).get("x");
			yVelocity = (Double) ((JSONObject) jsonBall.get("velocity")).get("y");
			
		} else {
			xVelocity = 0;
			yVelocity = 0;
			
		}
		
		try {
			mass = (Double)jsonBall.get("mass");
			if (mass > 0) {
				isComposite = false;
				ball = new PoolBall();
			
			} 
		} catch (Exception e) {
			isComposite = true;
		}
		
		if (isComposite) {
			// reading the "Balls: ball" array:
			JSONArray jsonBallArray = (JSONArray) jsonBall.get("balls");
			if (jsonBallArray.size() > 0) {
				isComposite = true;
				ball = new CompositeBall();
				strength = (double) jsonBall.get("strength");

			}
			// reading from the array:
			BallComponent smallBall;
			// Create ball object for every ball
			for (Object obj : jsonBallArray) {
				JSONObject jsonBallObject = (JSONObject) obj;
				smallBall = BallConfiguration.getBall(jsonBallObject);
				ball.add(smallBall);
			}
		}
		
		
	}	
	
	/**
	 * Set the colour of a ball
	 */
	@Override
	public void setColour() {
		ball.setColour(colour);
	}
	
	/**
	 * Set the x position of the ball
	 */
	@Override
	public void setpositionX() {
		ball.setxPosition(xPosition);

	}
	/**
	 * Set the y position of the ball
	 */
	@Override
	public void setpositionY() {
		ball.setyPosition(yPosition);
		
	}
	
	/**
	 * Set the x velocity of the ball
	 */
	@Override
	public void setvelocityX() {
		ball.setxVelocity(xVelocity);
		
	}
	
	/**
	 * Set the y velocity of the ball
	 */
	@Override
	public void setvelocityY() {
		ball.setyVelocity(yVelocity);
		
	}
	
	/**
	 * Set mass of the ball
	 */
	@Override
	public void setmass() {
		if (isComposite) {
			mass = ball.getTotalMass();
		}
		ball.setMass(mass);
	}
	
	/**
	 * Set the radius of the ball
	 */
	@Override
	public void setRadius() {
		ball.setRadius(radius);
		
	}
	
	/**
	 * Set the strength of the ball only when a ball is a composite ball
	 */
	@Override
	public void setStrength() {
		if (isComposite) {
			ball.setStrength(strength);
		}	
	}
	
	/**
	 * Set the view of the ball
	 */
	@Override
	public void setView() {
		this.view = new Circle(this.xPosition,this.yPosition,this.radius,Paint.valueOf(this.colour));
		ball.setView(view);
	}
	
	/**
	 * Returns the built BallComponent object
	 */
	public BallComponent getResult() {
		return ball;
	}
	
	
}
