package application;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
/**
 * This class defines a table class for the pool game.
 *
 */
public interface Table {
	
	/**
	 * Gets friction  of the table
	 * @return friction of the table
	 */
	public double getFriction();
	
	/**
	 * Get width of the table
	 * @return x: width of the table
	 */
	public long getX();
	
	/**
	 * Get height of the table
	 * @return: height of the table
	 */
	public long getY();
	
	/**
	 * Gets the view of the table
	 * @return: view of the table
	 */
	public Rectangle getView();
	
	/**
	 * Sets the view of the table as a rectangle with specified parameters
	 */
	public void setView();
	
}
