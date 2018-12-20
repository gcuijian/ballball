package ballball;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

public class Core {

	static BallJFrame bj;
	static BallPanel bp;
	
	static int windowWidth;
	static int windowHeight;
	
	static final Color colors[] = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY,
			Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
			Color.PINK, Color.RED, Color.YELLOW};
	
	static Map<Long, Ball> balls;
	
	public static void main(String[] args) {
		
		bj = new BallJFrame();
		
		BallEvent ballEvent = new BallEvent();
		bj.addMouseListener(ballEvent.mouseClick());
		
		balls = new HashMap<Long, Ball>();
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
		
		//使用一个独立线程控制小球的移动
		BallThread bt = new BallThread(ball);
		//把小球加入map
		balls.put(bt.getId(), ball);
		bt.start();
	}
	
	public static void refresh() {
		//实时获取窗口大小辅助类
		while(true) {
			Container contentPane=bj.getContentPane();
			//Point contentPos = contentPane.getLocationOnScreen();// 在屏幕的坐标
			Dimension size = contentPane.getSize();
			windowWidth = (int) size.getWidth();
			windowHeight = (int) size.getHeight();
			
			//在这里加小球碰撞
			
			
			bp.repaint();
		}
	}
	
}
