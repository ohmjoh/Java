package application;

import java.util.ArrayList;

import javafx.util.Pair;

/**
 * 
 * @author minoh
 * Memento class stores the internal state of the the ball list from GameEngine.
 */
public class Memento {
	private ArrayList<BallComponent> memento = new ArrayList<BallComponent>();
	private double[] xPositions;
	private double[] yPositions;
	private ArrayList<Pair<Integer, BallComponent>> smallBalls = new ArrayList<Pair<Integer, BallComponent>>();
	private ArrayList<Pair<BallComponent, BallComponent>> smallerBalls = new ArrayList<Pair<BallComponent, BallComponent>>();
	
	/**
	 * Constructor of the Memento class
	 * It takes a current ball list from GameEngine as a parameter.
	 * @param ballList 
	 */
	public Memento(ArrayList<BallComponent> ballList) {
		xPositions = new double[ballList.size()];
		yPositions = new double[ballList.size()];
		
		
		for(int i=0; i < ballList.size(); i++) {
			memento.add(ballList.get(i));
			xPositions[i] = ballList.get(i).getView().getCenterX();
			yPositions[i] = ballList.get(i).getView().getCenterY();
			
		}
		getSmallBalls(ballList);

	}
	
	/**
	 * This class captures the state of the sub-balls.
	 * @param ballList
	 */
	private void getSmallBalls(ArrayList<BallComponent> ballList) {

		for(int i=0; i < ballList.size(); i++) {
			if (ballList.get(i).getComponentList() != null && ballList.get(i).getComponentList().size() > 0) {
				
				for (BallComponent b: ballList.get(i).getComponentList()) {
					Pair<Integer, BallComponent> smallBallPair = new Pair<Integer, BallComponent>(i, b);
					smallBalls.add(smallBallPair);
					
					if(b.getComponentList() != null && b.getComponentList().size() > 0) {
						for (BallComponent sb: b.getComponentList()){
							Pair<BallComponent, BallComponent> smallerBallPair = new Pair<BallComponent, BallComponent>(b, sb);
							smallerBalls.add(smallerBallPair);

						}

					}
				}
				
				
			}
			
		}
	
	}
	
	/**
	 * Get saved ball list
	 * @return a list of most recently saved ball list 
	 */
	public ArrayList<BallComponent> getSavedBalls() {
		for(int i=0; i < memento.size(); i++) {
			memento.get(i).setxVelocity(0);
			memento.get(i).setyVelocity(0);
			memento.get(i).updateViewPosition(xPositions[i], yPositions[i]);
			
			if (memento.get(i).getComponentList() != null && memento.get(i).getComponentList().size() ==0) {

				for (Pair<Integer, BallComponent> pair : smallBalls) {
					if (pair.getKey() == i) {
						memento.get(i).add(pair.getValue());
						if(pair.getValue().getComponentList() != null && pair.getValue().getComponentList().size() == 0) {
							for (Pair<BallComponent, BallComponent> ballpair: smallerBalls) {
								if(ballpair.getKey() == pair.getValue()) {
									pair.getValue().add(ballpair.getValue());
									
								}
							}
						}
				
					}
				}
				
				
			}
			
			
		}

		return memento;
	}
}
