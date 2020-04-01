package application;

import java.util.ArrayList;

import javafx.scene.shape.Circle;

/**
 * This class defines characteristics of a cue ball which extends the ball class
 */
public class CueBall extends Ball {
	
	private boolean isSelected;

	
	//Min: Deleted the constructor to prevent direct construction with 'new' operator.

	

	@Override
	public String toString() {
		return "CueBall [colour=" + colour + ", xPosition=" + xPosition + ", yPosition=" + yPosition + ", xVelocity="
				+ xVelocity + ", yVelocity=" + yVelocity + ", mass=" + mass + ", radius=" + radius + "]";
	}
	/**
	 * Returns if the ball has been selected by clicking on the mouse
	 * @return If the ball has been selected by clicking on the mouse
	 */
	public boolean isSelected() {
		return isSelected;
	}
	/**
	 * Set if the ball has been selected by clicking on the mouse
	 * @param isSelecte: if the ball has been selected by clicking on the mouse
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	/**
	 * Returns If cueball is at rest
	 * @return If cueball is at rest
	 */
	public boolean atRest() {
		return Double.compare(this.getxVelocity(), 0) == 0 && Double.compare(this.getyVelocity(),0) == 0;
	}
	
	/**
	 * This method takes location where the mouse was clicked, calculate distance based on the location of the
	 * mouse and cue ball, and apply the new velocity to the cue ball
	 * @param x: x position of where the mouse was clicked
	 * @param y: y position of where the mouse was clicked
	 */
	public void registerShot(double x, double y) {
		this.setxVelocity((this.getView().getCenterX() - x)*2);
		this.setyVelocity((this.getView().getCenterY() - y)*2);
	}
	/**
	 * This method is for composite ball. 
	 */
	public ArrayList<BallComponent> getComponentList() {
		return null;
	}

}
