package ballball;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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
	
	public static Map<Long, Ball> balls;
	private static Map<Long, BallLogic> ballLogics;
	private static AtomicLong idGenerator = new AtomicLong(0);
	
	public static void main(String[] args) {
		
		bj = new BallJFrame();
		
		BallEvent ballEvent = new BallEvent();
		bj.addMouseListener(ballEvent.mouseClick());
		
		balls = new ConcurrentHashMap<>();
		ballLogics = new ConcurrentHashMap<>();
		
		//创造面板
		bp = new BallPanel(balls);
		//把面板加进去
		bj.add(bp);
		bj.setVisible(true);
		
		refresh();
	}
	
	public static void addBall(int x, int y) {
		/*
		 * 点击后触发
		 */
		//创造随机的小球
		//颜色
		Color color = colors[(int)(Math.random() * colors.length)];
		//速度，不可两者都为0
		int speedX = 0;
		int speedY = 0;
		while(speedX == 0 && speedY == 0) {
			speedX = (int)(Math.random() * 21)-10;
			speedY = (int)(Math.random() * 21)-10;
		}
		//大小
		int ballSize = ((int)(Math.random() * 60)) + 1;
		
		Ball ball = new Ball((x-(ballSize/2)), (y-ballSize), ballSize, color, speedX, speedY);
		
		long id = idGenerator.getAndIncrement();
		balls.put(id, ball);
		ballLogics.put(id, new BallLogic(ball));
	}
	
	public static void refresh() {
		//实时获取窗口大小辅助类
		while(true) {
			Container contentPane = bj.getContentPane();
			Dimension size = contentPane.getSize();
			windowWidth = (int) size.getWidth();
			windowHeight = (int) size.getHeight();
			
			// 更新小球位置
			for (BallLogic logic : ballLogics.values()) {
				logic.update();
			}
			
			// 处理碰撞
			BallPeng.ballPeng.isCollision(balls);
			
			// 重绘
			bp.repaint();
			
			try {
				// 约 60 帧
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
