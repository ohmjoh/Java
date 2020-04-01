package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * 
 * @author minoh
 * This class extends from TableDecorator and defines color table objects.
 */
public class ColorTable extends TableDecorator {
	
	private Color color;
	private Rectangle view;

	/**
	 * Constructor of the color table.
	 * It takes a Table object and the color of the table and set the color of the table.
	 * @param newTable
	 * @param color
	 */
	public ColorTable(Table newTable, String color) {
		super(newTable);
		this.color = Color.web(color);
		setView();
	}
	
	/**
	 * Set the color of the table.
	 */
	@Override
	public void setView() {
		view = table.getView();
		view.setFill(this.color);
		
	}
	/**
	 * Return the view of the color table.
	 * @return the view of the color table.
	 */
	@Override
	public Rectangle getView() {
		
		return view;
	}
	

}
