package application;

import java.awt.List;
import java.util.ArrayList;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

public class PhysicsEngine {

	//Min: deleted the main method in PhysicsEngine class
	
	private boolean ballBroken = false;
	
	
	//Min: created the constructor and hid physics calculations in private methods instead of using the public static methods.
	/**
	 * Constructor of PhysicsEngine
	 * @param table
	 * @param tableBounds
	 */
	public PhysicsEngine(Table table, Bounds tableBounds) {
		applyFriction(table);
		updateCollision();
		moveBalls(tableBounds);
		
	};

	
	/**
	 * Change all balls velocity due to given table friction
	 */
	private void applyFriction(Table table) { //Min: public to private , give argument
		BallComponent temp;
		for(int i = 0; i < GameEngine.ballList.size(); i++) {	// Calculate for every ball
			temp = GameEngine.ballList.get(i);
			// Set x velocity
			if(temp.getxVelocity() > 0) {	// Positive velocity
				temp.setxVelocity(Math.max(0,temp.getxVelocity() - table.getFriction()*0.4));
			}
			else if(temp.getxVelocity() < 0){
				temp.setxVelocity(Math.min(0,temp.getxVelocity() + table.getFriction()*0.4));
			}
			// Set y velocity
			if(temp.getyVelocity() > 0) {	// Positive velocity
				temp.setyVelocity(Math.max(0,temp.getyVelocity() - table.getFriction()*0.4));
			}
			else if(temp.getyVelocity() < 0){
				temp.setyVelocity(Math.min(0,temp.getyVelocity() + table.getFriction()*0.4));
			}
		}
	}
	
	
	/**
	 * Update velocities of all ball by considering collisions
	 */
	private void updateCollision() { //Min: from public to private
		for(int i = 0; i < GameEngine.ballList.size(); i++) {	// Check every ball against all other balls
			BallComponent ball1 = GameEngine.ballList.get(i);
			BallComponent ball2;
			for(int j = 0; j < GameEngine.ballList.size(); j++) {
				if(i == j)	// Do not check a ball against itself
					continue;
				ball2 = GameEngine.ballList.get(j);
				if(colliding(ball1, ball2)) {
					// Construct points to pass on to the physics engine
					Point2D posA = new Point2D(ball1.getView().getCenterX(), ball1.getView().getCenterY());
					Point2D velA = new Point2D(ball1.getxVelocity(), ball1.getyVelocity());
					double massA = ball1.getMass();

					Point2D posB = new Point2D(ball2.getView().getCenterX(), ball2.getView().getCenterY());
					Point2D velB = new Point2D(ball2.getxVelocity(), ball2.getyVelocity());
					double massB = ball2.getMass();
					
					// Invoke the physics engine with calculated points
					Pair<Point2D, Point2D> results = calculateCollision(posA, velA, massA, posB, velB, massB);
					
					// Set velocities of colliding balls according to calculated velocity
					ball1.setxVelocity(results.getKey().getX());
					ball1.setyVelocity(results.getKey().getY());
					ball2.setxVelocity(results.getValue().getX());
					ball2.setyVelocity(results.getValue().getY()); 
					
					breakBall(velA, results.getKey(), ball1);
					breakBall(velB, results.getValue(), ball2);
				
				}
			}        			
		}
	}
	
	/**
	 * Move all balls on table according to their speeds. This method does not detect to change position due to collision.
	 * Collision is handled in a separate method with parameter of ball positions in the next frame
	 * @param tableBounds
	 */
	private void moveBalls(Bounds tableBounds) { //Min: from public to private
		BallComponent ball;
		// Move every ball on the table
		for(int i = 0; i < GameEngine.ballList.size(); i++) {
			ball = GameEngine.ballList.get(i);
			calculatePosition(tableBounds, ball);
		}
	}
	
	/**
	 * Calculate position of the ball in next frame, change velocity if touching wall
	 * @param tableBounds: boundaries of the table
	 * @param ball: component ball
	 */
	private void calculatePosition(Bounds tableBounds, BallComponent ball) { ///Min: moved from ball class
		double x = ball.getView().getCenterX();
		double y = ball.getView().getCenterY();
		// Do nothing if ball at best
		if(new Double(x).equals(new Double(0)) && new Double(y).equals(new Double(0))) {
			return;
		}
		// Increase x and y velocity based on animation shows 60 frames per second	
		x += ball.getxVelocity() / 60;
		y += ball.getyVelocity() / 60;
		// Detect if balls are touching the borders
		boolean atRightBorder = x > tableBounds.getWidth() - ball.getRadius();
		boolean atLeftBorder = x < tableBounds.getMinX() + ball.getRadius();
		boolean atTopBorder = y < tableBounds.getMinY() + ball.getRadius();
		boolean atBottomBorder = y > tableBounds.getHeight() - ball.getRadius();
		if(atLeftBorder || atRightBorder) {	// Left/right border
			ball.setxVelocity(ball.getxVelocity() * -1);
			// Ball breaks when it hits the ball
			breakBall(Point2D.ZERO, new Point2D (ball.getxVelocity(), ball.getyVelocity()), ball);
			return;
		}
		if(atTopBorder || atBottomBorder) {	// Top/bottom border
			// Ball breaks when it hits the wall
			ball.setyVelocity(ball.getyVelocity() * -1);
			breakBall(Point2D.ZERO, new Point2D (ball.getxVelocity(), ball.getyVelocity()), ball);
			return;
		}
		// Update position of the ball
		ball.getView().setCenterX(x);
		ball.getView().setCenterY(y);
	
	}
	
		
	/**
	 * Break a composite ball into smaller balls if the energy of collision is bigger than the composite ball's strength
	 * @param velocity0: ball's velocity before the collision
	 * @param velocity1: ball's velocity after the collision
	 * @param ball: Composite ball
	 */
	private void breakBall(Point2D velocity0, Point2D velocity1, BallComponent ball) {
		if (ball.getComponentList() != null && ball.getComponentList().size() > 0) {
			double strength = ball.getStrength();
			
			Point2D deltaV = velocity1.subtract(velocity0);
			float energyOfCollision = (float) (ball.getMass() * (Math.pow(deltaV.getX(), 2) + Math.pow(deltaV.getY(), 2)));
			
			if (strength < energyOfCollision) {
				System.out.println("It's strong!!!!");
				float energyPerBall = energyOfCollision / ball.getComponentList().size();
				Point2D pointOfCollision = deltaV.normalize().multiply(-1 * ball.getRadius());
		
				
				//for each component ball
				for (int i = 0; i < ball.getComponentList().size(); i++) {
					Point2D componentBallPosition = new Point2D(ball.getxPosition(), ball.getyPosition());
					Point2D componentBallDeltaV = componentBallPosition.subtract(pointOfCollision).normalize().multiply(Math.sqrt(energyPerBall/ball.getMass()));
					Point2D componentBallVelocity = velocity0.add(componentBallDeltaV);
					
					double relativePosX = ball.getComponent(i).getxPosition();
					double relativePosY = ball.getComponent(i).getyPosition();
					
					ball.getComponent(i).setxVelocity(componentBallVelocity.getX()/10);
					ball.getComponent(i).setyVelocity(componentBallVelocity.getY()/10);
					//ball.getComponent(i).setxPosition(ball.getxPosition() + relativePosX);
					//ball.getComponent(i).setyPosition(ball.getyPosition() + relativePosY);
					//ball.getComponent(i).updateViewPosition(ball.getComponent(i).getxPosition(), ball.getComponent(i).getyPosition());
					ball.getComponent(i).updateViewPosition(ball.getView().getCenterX() + relativePosX, ball.getView().getCenterY() + relativePosY);
					
					GameEngine.ballList.add(ball.getComponent(i));
					ballBroken = true;
				}
				ball.removeAll();
				
			} else {
				ballBroken = false;
			}
		} else {
			ballBroken = false;
		}
	}
	
	
	/**
	 * Detect if two balls are colliding.
	 * 
	 * @param b1 First Ball instance
	 * @param b2 Second Ball instance
	 * @param deltaX the distance of CenterX of b1 and CenterX of b2
	 * @param deltaY the distance of CenterY of b1 and CenterY of b2
	 * @return true if two balls are colliding, false otherwise.
	 */
	private boolean colliding(final BallComponent b1, final BallComponent b2) { /////MJ Implementaion change~!!!!!!
		final double deltaX = b2.getView().getCenterX() - b1.getView().getCenterX();
        final double deltaY = b2.getView().getCenterY() - b1.getView().getCenterY();
        final double radiusSum = b1.getRadius() + b2.getRadius();
        if (deltaX * deltaX + deltaY * deltaY <= radiusSum * radiusSum) {
            if ( deltaX * (b2.getxVelocity() - b1.getxVelocity()) + deltaY * (b2.getyVelocity() - b1.getyVelocity()) < 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * This is an updated collision calculation function for 2 balls colliding in 2D space. You may use it however
     * you wish for your assignment.
     *
     * This uses the optimised physics algorithm discussed here:
     * http://www.gamasutra.com/view/feature/3015/pool_hall_lessons_fast_accurate_.php?page=3
     * which has been converted into Java/JavaFX
     *
     * @param positionA The coordinates of the centre of ball A
     * @param velocityA The delta x,y vector of ball A (how much it moves per tick)
     * @param massA The mass of ball A (for the moment this should always be the same as ball B)
     * @param positionB The coordinates of the centre of ball B
     * @param velocityB The delta x,y vector of ball B (how much it moves per tick)
     * @param massB The mass of ball B (for the moment this should always be the same as ball A)
     *
     * @return A Pair<Point2D, Point2D> in which the first (key) Point2D is the new delta x,y vector for ball A, and the second
     *         (value) Point2D is the new delta x,y vector for ball B.
     */
    private Pair<Point2D, Point2D> calculateCollision(Point2D positionA, Point2D velocityA, double massA, Point2D positionB, Point2D velocityB, double massB) {
    	//Min: changed it to private
    	
        // Find the angle of the collision - basically where is ball B relative to ball A. We aren't concerned with
        // distance here, so we reduce it to unit (1) size with normalize() - this allows for arbitrary radii
        Point2D collisionVector = positionA.subtract(positionB);
        collisionVector = collisionVector.normalize();

        // Here we determine how 'direct' or 'glancing' the collision was for each ball
        double vA = collisionVector.dotProduct(velocityA);
        double vB = collisionVector.dotProduct(velocityB);

        // If you don't detect the collision at just the right time, balls might collide again before they leave
        // each others' collision detection area, and bounce twice. This stops these secondary collisions by detecting
        // whether a ball has already begun moving away from its pair, and returns the original velocities
        if (vB <= 0 && vA >= 0) {
            return new Pair<>(velocityA, velocityB);
        }

        // This is the optimisation function described in the gamasutra link. Rather than handling the full quadratic
        // (which as we have discovered allowed for sneaky typos) this is a much simpler - and faster - way of obtaining
        // the same results.
        double optimizedP = (2.0 * (vA - vB)) / (massA + massB);

        // Now we apply that calculated function to the pair of balls to obtain their final velocities
        Point2D velAPrime = velocityA.subtract(collisionVector.multiply(optimizedP).multiply(massB));
        Point2D velBPrime = velocityB.add(collisionVector.multiply(optimizedP).multiply(massA));

        return new Pair<>(velAPrime, velBPrime);
    }
}