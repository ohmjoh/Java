package application;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
/**
 * 
 * @author minoh
 * This class defines image table and extends from TableDecorator. 
 */
public class ImageTable extends TableDecorator {
	
	private ImageView tableImageView;
	private Rectangle view;
	private long tableSizeX;
	private long tableSizeY;
	private long offsetX;
	private long offsetY;
	
	/**
	 * Constructor of the ImageTable class. It takes 6 parameters.
	 * @param newTable is a basic table.
	 * @param imagePath is the file path for the table image.
	 * @param imgSizeX is image width.
	 * @param imgSizeY is image height.
	 * @param offsetX is x position of the offset of the table, which means x coordinate of the upper left corner of the playable area.
	 * @param offsetY is y position of the offset of the table. which means y coordinate of the upper left corner of the playable area.
	 */
	public ImageTable(Table newTable, String imagePath, long imgSizeX, long imgSizeY, long offsetX, long offsetY) {
		super(newTable);
		Image tableImage = new Image(imagePath);
		tableImageView = new ImageView();
		tableImageView.setImage(tableImage);
		tableImageView.setFitWidth(imgSizeX);
		tableImageView.setFitHeight(imgSizeY);
		this.tableSizeX = imgSizeX;
		this.tableSizeY = imgSizeY;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		setView();
		
		
	}
	/**
	 * Get width of the table
	 * @return x: width of the table
	 */
	@Override
	public long getX() {
		
		return tableSizeX;
	}
	
	/**
	 * Get height of the table
	 * @return: height of the table
	 */
	@Override
	public long getY() {
		
		return tableSizeY;
	}
	
	/**
	 * Set the view of the playable area and set the colour to transparent.
	 */
	@Override
	public void setView() {
		view = table.getView();
		view.setFill(Color.TRANSPARENT);
		view.setWidth(tableSizeX - offsetX);
		view.setHeight(tableSizeY - offsetY);

	}
	
	/**
	 * Get the view of the playable area
	 * @return: view of the playable area
	 */
	@Override
	public Rectangle getView() {
		return view;
	}
	
	/**
	 * Get the table image
	 * @return tableImageView
	 */
	public ImageView getTableImage() {
		return tableImageView;
	}
	
	/**
	 * Get the x offset of the table.
	 * @return offsetX:  x coordinate of the upper left corner of the playable area
	 */
	public long getPlayAreaX() {
		return offsetX;
	}
	
	/**
	 * Get the y offset of the table.
	 * @return offsetY:  y coordinate of the upper left corner of the playable area
	 */
	public long getPlayAreaY() {
		return offsetY;
	}
	
}
