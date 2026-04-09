package ballball.logic;

import ballball.Core;
import ballball.model.Ball;

public class BallLogic {

	private final Ball ball;
	
	public BallLogic(Ball ball) {
		this.ball = ball;
	}

	public void update() {
		int maxX = Math.max(0, Core.windowWidth - ball.getSize());
		int maxY = Math.max(0, Core.windowHeight - ball.getSize());
		
		int nextX = ball.getX() + ball.getSpeedX();
		if (nextX < 0 || nextX > maxX) {
			ball.setSpeedX(-ball.getSpeedX());
			nextX = clamp(ball.getX() + ball.getSpeedX(), 0, maxX);
		}
		
		int nextY = ball.getY() + ball.getSpeedY();
		if (nextY < 0 || nextY > maxY) {
			ball.setSpeedY(-ball.getSpeedY());
			nextY = clamp(ball.getY() + ball.getSpeedY(), 0, maxY);
		}
		
		ball.setX(nextX);
		ball.setY(nextY);
	}

	private int clamp(int value, int min, int max) {
		if (value < min) {
			return min;
		}
		if (value > max) {
			return max;
		}
		return value;
	}
	
}
