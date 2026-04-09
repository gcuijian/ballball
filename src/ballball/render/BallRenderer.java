package ballball.render;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import ballball.model.Ball;

public class BallRenderer {

	public void render(Graphics2D graphics, List<Ball> balls, double alpha) {
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (Ball ball : balls) {
			int baseSize = ball.getSize();
			double scaleX = clampScale(1.0 + ball.getJellyX());
			double scaleY = clampScale(1.0 + ball.getJellyY());
			int drawWidth = Math.max(2, (int) Math.round(baseSize * scaleX));
			int drawHeight = Math.max(2, (int) Math.round(baseSize * scaleY));
			int centerX = (int) Math.round(ball.getInterpolatedCenterX(alpha));
			int centerY = (int) Math.round(ball.getInterpolatedCenterY(alpha));
			int drawX = centerX - (drawWidth / 2);
			int drawY = centerY - (drawHeight / 2);
			graphics.setColor(ball.getColor());
			graphics.fillOval(drawX, drawY, drawWidth, drawHeight);
		}
	}

	private double clampScale(double value) {
		if (value < 0.75) {
			return 0.75;
		}
		if (value > 1.25) {
			return 1.25;
		}
		return value;
	}
}
