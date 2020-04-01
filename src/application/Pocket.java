package application;

import javafx.scene.shape.Circle;

/**
 * This defines Pocket class. 
 * @author minoh
 *
 */
public class Pocket {
	
	private double posX;
	private double posY;
	private double radius;
	
	/**
	 * Constructor of the Pocket class
	 * @param x is x coordinate of the center of the pocket
	 * @param y is y coordinate of the center of the pocket
	 * @param radius of the pocket
	 */
	public Pocket(double x, double y, double radius) {
		this.posX = x;
		this.posY = y;
		this.radius = radius;
	}
	
	/**
	 * Get x position of the pocket
	 * @return posX
	 */
	public double getX() {
		return posX;
	}
	
	/**
	 * Get y position of the pocket
	 * @return posY
	 */
	public double getY() {
		return posY;
	
	}
	
	/**
	 * Get radius of the pocket
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}
	
	/**
	 * Get the view (Circle object) of the pocket
	 * @return a Circle object
	 */
	public Circle getView() {
		return new Circle(posX, posY, radius);
	}
}
