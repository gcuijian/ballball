package ballball;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ballball.event.BallEvent;
import ballball.logic.BallLogic;
import ballball.logic.BallPeng;
import ballball.model.Ball;
import ballball.view.BallJFrame;
import ballball.view.BallPanel;

public class Core {

	static BallJFrame bj;
	static BallPanel bp;
	
	public static int windowWidth;
	public static int windowHeight;
	
	static final Color colors[] = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY,
			Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
			Color.PINK, Color.RED, Color.YELLOW};
	
	public static final List<Ball> balls = new ArrayList<>();
	private static final List<BallLogic> ballLogics = new ArrayList<>();
	
	public static void main(String[] args) {
		bj = new BallJFrame();
		
		BallEvent ballEvent = new BallEvent();
		bj.addMouseListener(ballEvent.mouseClick());
		
		//创造面板
		bp = new BallPanel(balls);
		//把面板加进去
		bj.add(bp);
		bj.setVisible(true);
		
		refresh();
	}
	
	public static void addBall(int x, int y) {
		//创造随机的小球
		ThreadLocalRandom random = ThreadLocalRandom.current();
		Color color = colors[random.nextInt(colors.length)];
		int speedX = 0;
		int speedY = 0;
		while (speedX == 0 && speedY == 0) {
			speedX = random.nextInt(-10, 11);
			speedY = random.nextInt(-10, 11);
		}
		int ballSize = random.nextInt(1, 61);
		
		Ball ball = new Ball((x - (ballSize / 2)), (y - ballSize), ballSize, color, speedX, speedY);
		
		synchronized (balls) {
			balls.add(ball);
			ballLogics.add(new BallLogic(ball));
		}
	}
	
	public static void refresh() {
		final long frameNanos = 16_666_667L;
		while (true) {
			long frameStart = System.nanoTime();
			windowWidth = bp.getWidth();
			windowHeight = bp.getHeight();
			
			synchronized (balls) {
				for (BallLogic logic : ballLogics) {
					logic.update();
				}
				BallPeng.ballPeng.isCollision(balls);
			}
			
			bp.repaint();
			
			long elapsed = System.nanoTime() - frameStart;
			long sleepNanos = frameNanos - elapsed;
			if (sleepNanos > 0) {
				try {
					Thread.sleep(sleepNanos / 1_000_000L, (int) (sleepNanos % 1_000_000L));
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
		}
	}
	
}
