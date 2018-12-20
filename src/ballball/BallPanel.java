package ballball;

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

	public void paint(Graphics g) {
		super.paint(g);
		
		Set<Long> set = balls.keySet();
		for (Iterator<Long> iterator = set.iterator(); iterator.hasNext();) {
			Long key = (Long) iterator.next();
			Ball ball = balls.get(key);
			g.setColor(ball.getColor());
			g.fillOval(ball.getX(), ball.getY(), ball.getSize(), ball.getSize());
		}
		
	}
	
}
