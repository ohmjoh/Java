package application;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
/**
 * 
 * @author minoh
 * This class defines composite ball class and extends from BallComponent class.
 * Composite ball contains small balls inside itself and keeps a list of its components.
 */
public class CompositeBall extends BallComponent{
	
	ArrayList<BallComponent> ballComponents = new ArrayList<BallComponent>();
	private Circle view;
	protected String colour;
	protected double xPosition;
	protected double yPosition;
	protected double xVelocity;
	protected double yVelocity;
	protected double mass;
	protected double radius;
	protected double strength;
	
	/**
	 * Constructor of CompositeBall
	 */
	public CompositeBall() {
		super();
	}
	
	@Override
	public String toString() {
		return "CompositeBall [colour=" + colour + ", xPosition=" + xPosition + ", yPosition=" + yPosition + ", xVelocity="
				+ xVelocity + ", yVelocity=" + yVelocity + ", mass=" + mass + ", radius=" + radius + ", strength=" + strength + "]";
	}
	
	/**
	 * Add new component ball in the component ball list.
	 * @param newBallComponent
	 */
	public void add(BallComponent newBallComponent) {
		ballComponents.add(newBallComponent);
	}
	
	/**
	 * Remove all balls in the component ball list.
	 */
	public void removeAll() {
		ballComponents.removeAll(ballComponents);
	}
	
	/**
	 * Get a component ball from the component ball list where the index is 
 	 * @param component Index.
	
	 * @return BallComponent object
	 */
	public BallComponent getComponent(int componentIndex) {
		return (BallComponent)ballComponents.get(componentIndex);
	}
	
	/**
	 * Get the component ball list
	 * @return the component ball list
	 */
	public ArrayList<BallComponent> getComponentList() {
		return ballComponents;
	}

	/**
	 * Get the sum of the masses of the sub balls.
	 * @return total mass
	 */
	public double getTotalMass() {
		Iterator ballIterator = ballComponents.iterator();
		double totalMass = 0;
		while (ballIterator.hasNext()) {
			BallComponent ball = (BallComponent) ballIterator.next();
			totalMass += ball.getMass();
		}
		return totalMass;
	}
	
	/**
	 *  Return colour of the ball
	 * @return colour of the ball
	 */
	public String getColour() {
		return colour;
	}
	/**
	 * Sets the colour of the abll
	 * @param colour colour of the ball
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}
	/**
	 * Returns the x position of the ball
	 * @return x position of the ball
	 */
	public double getxPosition() {
		return xPosition;
	}
	/**
	 *  Sets the x position of the ball
	 * @param xPosition x position of the ball
	 */
	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}
	/**
	 * Returns the y position of the ball
	 * @return y position of the ball
	 */
	public double getyPosition() {
		return yPosition;
	}
	/**
	 * Sets the y position of the ball
	 * @param yPosition: y position of the ball
	 */
	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}
	/**
	 * Gets the x velocity of the ball
	 * @return x velocity of the ball
	 */
	public double getxVelocity() {
		return xVelocity;
	}
	/**
	 * Sets the x velocity of the ball
	 * @param xVelocity: x velocity of the ball
	 */
	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	/**
	 * Gets the y velocity of the ball
	 * @return y velocity of the ball
	 */
	public double getyVelocity() {
		return yVelocity;
	}
	/**
	 * Sets the y velocity of the ball
	 * @param yVelocity: y velocity of the ball
	 */
	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	/**
	 * Gets the mass of the ball
	 * @return mass of the ball
	 */
	public double getMass() {
		return mass;
	}
	/**
	 * Sets the mass of the ball
	 * @param mass: mass of the ball
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}
	/**
	 * Gets the strength of the ball
	 * @return strength of the ball
	 */
	public double getStrength() {
		return strength;
	}
	/**
	 * Sets the strength of the ball
	 * @param strength: strength of the ball
	 */
	public void setStrength(double strength) {
		this.strength = strength;
	}
	
	
	/**
	 * Returns the radius of the ball
	 * @return: radius of the ball
	 */
	public double getRadius() {
		return radius;
	}
	/**
	 * Sets the radius of the ball
	 * @param radius: radius of the ball
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
	/**
	 * Returns the view of the ball
	 * @return view: of the ball
	 */
	public Circle getView() {
		return view;
	}
	/**
	 * Sets the view of the ball
	 * @param view: view of the ball
	 */
	public void setView(Circle view) {
		this.view = view;
	}
	/**
	 * Update the view's radius
	 */
	public void updateViewRadius(double radius) {
		view.setRadius(radius);
		
	}
	/**
	 * Update the view's position
	 */
	public void updateViewPosition(double xPosition, double yPosition) {
		view.setCenterX(xPosition);
		view.setCenterY(yPosition);
	}

}
