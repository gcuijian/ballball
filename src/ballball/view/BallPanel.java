package ballball.view;

import ballball.model.Ball;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

public class BallPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8330425786435304407L;

	private Map<Long, Ball> balls;
	
	public BallPanel(Map<Long, Ball> balls) {
		super();
		this.balls = balls;
	}
	
	public Map<Long, Ball> getBall() {
		return balls;
	}
	
	public void setBall(Map<Long, Ball> balls) {
		this.balls = balls;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Ball ball : balls.values()) {
			if (ball != null) {
				g.setColor(ball.getColor());
				g.fillOval(ball.getX(), ball.getY(), ball.getSize(), ball.getSize());
			}
		}
		
	}
	
}
