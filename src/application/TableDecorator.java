package application;

import javafx.scene.shape.Rectangle;
/**
 * 
 * @author minoh
 * This class contains a reference to a Table object and 
 * defines the interface for the concrete decorators (ColorTable, ImageTable)
 */
public class TableDecorator implements Table {
	
	Table table;
	
	/**
	 * Constructor of the TalbeDecorator class
	 * It takes a Table object as a parameter.
	 * @param newTable
	 */
	public TableDecorator(Table newTable) {	
		table = newTable;
	}
	
	/**
	 * Get friction  of the table
	 * @return friction of the table
	 */
	@Override
	public double getFriction() {
		
		return table.getFriction();
	}

	/**
	 * Get width of the table
	 * @return x: width of the table
	 */
	@Override
	public long getX() {
		
		return table.getX();
	}

	/**
	 * Get height of the table
	 * @return: height of the table
	 */
	@Override
	public long getY() {
		
		return table.getY();
	}

	/**
	 * Gets the view of the table
	 * @return: view of the table
	 */
	@Override
	public Rectangle getView() {
		
		return table.getView();
	}
	
	
	/**
	 * Sets the view of the table as a rectangle with specified parameters
	 */
	@Override
	public void setView() {
		table.setView();
		
	}

	
	
}
