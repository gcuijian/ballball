package ballball.view;

import ballball.model.Ball;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

public class BallPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8330425786435304407L;

	private final List<Ball> balls;
	
	public BallPanel(List<Ball> balls) {
		super();
		this.balls = balls;
	}
	
	public List<Ball> getBall() {
		return balls;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (Ball ball : balls) {
			if (ball != null) {
				int baseSize = ball.getSize();
				double scaleX = clampScale(1.0 + ball.getJellyX());
				double scaleY = clampScale(1.0 + ball.getJellyY());
				int drawWidth = Math.max(2, (int) Math.round(baseSize * scaleX));
				int drawHeight = Math.max(2, (int) Math.round(baseSize * scaleY));
				int centerX = ball.getX() + (baseSize / 2);
				int centerY = ball.getY() + (baseSize / 2);
				int drawX = centerX - (drawWidth / 2);
				int drawY = centerY - (drawHeight / 2);
				g2.setColor(ball.getColor());
				g2.fillOval(drawX, drawY, drawWidth, drawHeight);
			}
		}
		g2.dispose();
		
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
