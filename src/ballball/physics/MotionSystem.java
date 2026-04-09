package ballball.physics;

import ballball.model.Ball;
import ballball.model.WorldBounds;

public class MotionSystem {

	public void advance(Ball ball, WorldBounds bounds, double stepSeconds) {
		int maxX = Math.max(0, bounds.getWidth() - ball.getSize());
		int maxY = Math.max(0, bounds.getHeight() - ball.getSize());

		double nextX = ball.getX() + (ball.getSpeedX() * stepSeconds * 60.0);
		if (nextX < 0 || nextX > maxX) {
			ball.setSpeedX(-ball.getSpeedX());
			nextX = clamp(ball.getX() + (ball.getSpeedX() * stepSeconds * 60.0), 0, maxX);
		}

		double nextY = ball.getY() + (ball.getSpeedY() * stepSeconds * 60.0);
		if (nextY < 0 || nextY > maxY) {
			ball.setSpeedY(-ball.getSpeedY());
			nextY = clamp(ball.getY() + (ball.getSpeedY() * stepSeconds * 60.0), 0, maxY);
		}

		ball.setX(nextX);
		ball.setY(nextY);
		ball.relaxJelly();
	}

	public double clamp(double value, double min, double max) {
		if (value < min) {
			return min;
		}
		if (value > max) {
			return max;
		}
		return value;
	}
}
