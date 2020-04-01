package application;


/**
 * An interface that specifies steps in building a ball
 *
 */
public interface BallBuilder {
	public void setColour();
	public void setpositionX(); ///Min: camel Case!!!!!
	public void setpositionY();
	public void setvelocityX();
	public void setvelocityY();
	public void setmass();
	public void setRadius();
	public void setStrength();
	public void setView();
	public BallComponent getResult();
}
