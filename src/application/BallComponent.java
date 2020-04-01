package application;

import java.util.ArrayList;

import javafx.scene.shape.Circle;

/**
 * This is a common interface for Ball (Leaf) and CompositeBall (Composite).
 * The structure of these three classes follows the composite pattern which represents a tree structure.
 * @author minoh
 *
 */
public abstract class BallComponent {
	
	/**
	 * Add new component ball in the component ball list.
	 * @param newBallComponent
	 */
	public void add(BallComponent newBallComponent) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Remove all balls in the component ball list.
	 */
	public void removeAll() {
		throw new UnsupportedOperationException();
	}
	/**
	 * Get a component ball from the component ball list where the index is 
 	 * @param component Index.
	
	 * @return BallComponent object
	 */
	public BallComponent getComponent(int componentIndex) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Get the component ball list
	 * @return the component ball list
	 */
	public ArrayList<BallComponent> getComponentList() {
		throw new UnsupportedOperationException();
	}
	/**
	 * Get the sum of the masses of the sub balls.
	 * @return total mass
	 */
	public double getTotalMass() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 *  Return colour of the ball
	 * @return colour of the ball
	 */
	public String getColour() {
		throw new UnsupportedOperationException();

	}

	/**
	 * Returns the x position of the ball
	 * @return x position of the ball
	 */
	public double getxPosition() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Returns the y position of the ball
	 * @return y position of the ball
	 */
	public double getyPosition() {
		throw new UnsupportedOperationException();

	}
	
	/**
	 * Gets the x velocity of the ball
	 * @return x velocity of the ball
	 */
	public double getxVelocity() {
		throw new UnsupportedOperationException();

	}
	
	/**
	 * Gets the y velocity of the ball
	 * @return y velocity of the ball
	 */
	public double getyVelocity() {
		throw new UnsupportedOperationException();

	}
	
	/**
	 * Gets the mass of the ball
	 * @return mass of the ball
	 */
	public double getMass() {
		throw new UnsupportedOperationException();

	}
	
	/**
	 * Gets the strength of the ball
	 * @return strength of the ball
	 */
	public double getStrength() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Returns the radius of the ball
	 * @return: radius of the ball
	 */
	public double getRadius() {
		throw new UnsupportedOperationException();

	}
	
	/**
	 * Returns the view of the ball
	 * @return view of the ball
	 */
	public Circle getView() {
		throw new UnsupportedOperationException();

	}
	
	/**
	 * Sets the colour of the abll
	 * @param colour colour of the ball
	 */
	public void setColour(String colour) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 *  Sets the x position of the ball
	 * @param xPosition x position of the ball
	 */
	public void setxPosition(double xPosition) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Sets the y position of the ball
	 * @param yPosition: y position of the ball
	 */
	public void setyPosition(double yPosition) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Sets the x velocity of the ball
	 * @param xVelocity: x velocity of the ball
	 */
	public void setxVelocity(double xVelocity) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Sets the y velocity of the ball
	 * @param yVelocity: y velocity of the ball
	 */
	public void setyVelocity(double yVelocity) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Sets the mass of the ball
	 * @param mass: mass of the ball
	 */
	public void setMass(double mass) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Sets the strength of the ball
	 * @param mass: mass of the ball
	 */
	public void setStrength(double strength) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Sets the radius of the ball
	 * @param radius: radius of the ball
	 */
	public void setRadius(double radius) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Sets the view of the ball
	 * @param view: view of the ball
	 */
	public void setView(Circle view) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Update the view's radius
	 */
	public void updateViewRadius(double radius) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Update the view's position
	 */
	public void updateViewPosition(double xPosition, double yPosition) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Returns true if cueball is at rest
	 * @return true if cueball is at rest
	 */
	public boolean atRest() {
		return Double.compare(this.getxVelocity(), 0) == 0 && Double.compare(this.getyVelocity(),0) == 0;
	}

}
