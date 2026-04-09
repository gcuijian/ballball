package ballball.view;

import ballball.model.Ball;
import java.awt.Graphics;
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
		
		synchronized (balls) {
			for (Ball ball : balls) {
				if (ball != null) {
					g.setColor(ball.getColor());
					g.fillOval(ball.getX(), ball.getY(), ball.getSize(), ball.getSize());
				}
			}
		}
		
	}
	
}
