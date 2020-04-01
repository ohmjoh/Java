package application;


import javafx.scene.shape.Circle;
/**
 * This is an abstract class that defines the object type ball.
 *
 */
public abstract class Ball extends BallComponent{
	private Circle view;
	protected String colour;
	protected double xPosition;
	protected double yPosition;
	protected double xVelocity;
	protected double yVelocity;
	protected double mass;
	protected double radius;
	

	//Min: Deleted the constructor to prevent direct construction with 'new' operator.
	
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
	 * Update the radius of the view of the ball
	 * @param radius: radius of the ball
	 */
	public void updateViewRadius(double radius) {
		view.setRadius(radius);
		
	}
	/**
	 * Min: Update the position of the view of the ball
	 * This method is used when small balls are showing but still contained in the big balls
	 * Only the position of the view of the small balls gets updated, but actual objective is not included in the ball list, s
	 * so it isn't affected by the collision.
	 * @param xPosition: x position of the ball, yPosition: y position of the ball
	 */
	public void updateViewPosition(double xPosition, double yPosition) {
		view.setCenterX(xPosition);
		view.setCenterY(yPosition);
	}
	
}
