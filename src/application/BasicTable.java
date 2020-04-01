package application;

import javafx.scene.shape.Rectangle;
/**
 * @author minoh
 * This class implements Table interface and defines basic table objects.
 */
public class BasicTable implements Table {
	
	private Rectangle view;
	private double friction;
	private long x;	//Min: table width
	private long y; //Min: table height
	
	
	/**
	 * Construct a table object with the given parameters.
	 * @param friction: friction of the table
	 * @param x: width of the table
	 * @param y: height of the table
	 */
	public BasicTable(double friction, long x, long y) {
		this.friction = friction;
		this.x = x;
		this.y = y;
		this.setView();
		
	}

	/**
	 * Gets friction  of the table
	 * @return friction of the table
	 */
	@Override
	public double getFriction() {
		
		return friction;
	}

	/**
	 * Gets width of the table
	 * @return x: width of the table
	 */
	@Override
	public long getX() {
		return x;
	}

	/**
	 * Gets height of the table
	 * @return: height of the table
	 */
	@Override
	public long getY() {
		return y;
	}

	/**
	 * Gets the view of the table
	 * @return: view of the table
	 */
	@Override
	public Rectangle getView() {
		return view;
	}

	/**
	 * Sets the view of the table with width x and height y.
	 */
	@Override
	public void setView() {
		this.view = new Rectangle(this.x, this.y);
		
	}

}
