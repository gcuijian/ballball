package ballball.logic;

import ballball.model.Ball;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 处理球的碰撞的
 * <p>这里添加描述</p>
 * @ClassName BallPeng
 * @Description 
 * @Author Cui Jian
 * @version
 * @Date 2018年12月19日 下午7:53:31
 */
public class BallPeng {

	private static final int CELL_SIZE = 64;
	private static final double RESTITUTION = 1.05;
	private static final double MIN_BOUNCE_SPEED = 1.2;
	private static final int MAX_SPEED = 14;
	private final HashMap<Long, List<Ball>> buckets = new HashMap<>();
	
	private BallPeng() {}
	
	public static final BallPeng ballPeng = new BallPeng();
	
	//入口类
	public List<Ball> isCollision(List<Ball> balls) {
		//如果长度为零或者1，不存在碰撞直接返回。
		if (balls == null || balls.size() <= 1) {
			return balls;
		}
		
		buckets.clear();
		for (Ball ball : balls) {
			if (ball == null) {
				continue;
			}
			int cellX = cell(ball.getX() + (ball.getSize() / 2));
			int cellY = cell(ball.getY() + (ball.getSize() / 2));
			for (int y = cellY - 1; y <= cellY + 1; y++) {
				for (int x = cellX - 1; x <= cellX + 1; x++) {
					assessBucketCollision(ball, bucketKey(x, y));
				}
			}
			buckets.computeIfAbsent(bucketKey(cellX, cellY), key -> new ArrayList<>()).add(ball);
		}
		
		return balls;
	}
	
	private void assessBucketCollision(Ball current, long bucketKey) {
		List<Ball> bucket = buckets.get(bucketKey);
		if (bucket == null) {
			return;
		}
		for (Ball other : bucket) {
			assessCollision(current, other);
		}
	}
	
	//对比两个球球是否碰撞，如果碰撞就交给 changeSpeed ，没碰撞就算了
	private void assessCollision(Ball b1, Ball b2) {
		double centerX1 = b1.getX() + (b1.getSize() / 2.0);
		double centerY1 = b1.getY() + (b1.getSize() / 2.0);
		double centerX2 = b2.getX() + (b2.getSize() / 2.0);
		double centerY2 = b2.getY() + (b2.getSize() / 2.0);
		double dx = centerX2 - centerX1;
		double dy = centerY2 - centerY1;
		double distSq = (dx * dx) + (dy * dy);
		
		double radiusSum = (b1.getSize() / 2.0) + (b2.getSize() / 2.0);
		if (distSq <= radiusSum * radiusSum) {
			changeSpeed(b1, b2, dx, dy, distSq, radiusSum);
		}
	}
	
	//对有碰撞的两个球做速度的改变
	private void changeSpeed(Ball b1, Ball b2, double dx, double dy, double distSq, double radiusSum) {
		double dist = Math.sqrt(distSq);
		double normalX;
		double normalY;
		if (dist < 0.0001) {
			normalX = 1.0;
			normalY = 0.0;
			dist = radiusSum;
		} else {
			normalX = dx / dist;
			normalY = dy / dist;
		}

		resolveOverlap(b1, b2, normalX, normalY, radiusSum - dist);
		
		double relativeVelocityX = b2.getSpeedX() - b1.getSpeedX();
		double relativeVelocityY = b2.getSpeedY() - b1.getSpeedY();
		double velocityAlongNormal = (relativeVelocityX * normalX) + (relativeVelocityY * normalY);
		if (velocityAlongNormal > -MIN_BOUNCE_SPEED) {
			velocityAlongNormal = -MIN_BOUNCE_SPEED;
		}
		
		double impulse = -((1.0 + RESTITUTION) * velocityAlongNormal) / 2.0;
		double impulseX = impulse * normalX;
		double impulseY = impulse * normalY;
		
		double nextSpeedX1 = b1.getSpeedX() - impulseX;
		double nextSpeedY1 = b1.getSpeedY() - impulseY;
		double nextSpeedX2 = b2.getSpeedX() + impulseX;
		double nextSpeedY2 = b2.getSpeedY() + impulseY;
		
		b1.setSpeedX(toSpeed(nextSpeedX1));
		b1.setSpeedY(toSpeed(nextSpeedY1));
		b2.setSpeedX(toSpeed(nextSpeedX2));
		b2.setSpeedY(toSpeed(nextSpeedY2));
		
		double jellyStrength = Math.max(MIN_BOUNCE_SPEED, Math.abs(velocityAlongNormal));
		b1.applyJelly(normalX, normalY, jellyStrength);
		b2.applyJelly(normalX, normalY, jellyStrength);
	}

	private void resolveOverlap(Ball b1, Ball b2, double normalX, double normalY, double overlap) {
		if (overlap <= 0) {
			return;
		}
		double separation = (overlap / 2.0) + 0.5;
		moveBall(b1, -normalX * separation, -normalY * separation);
		moveBall(b2, normalX * separation, normalY * separation);
	}

	private void moveBall(Ball ball, double offsetX, double offsetY) {
		int nextX = (int) Math.round(ball.getX() + offsetX);
		int nextY = (int) Math.round(ball.getY() + offsetY);
		int maxX = Math.max(0, ballball.Core.windowWidth - ball.getSize());
		int maxY = Math.max(0, ballball.Core.windowHeight - ball.getSize());
		ball.setX(clamp(nextX, 0, maxX));
		ball.setY(clamp(nextY, 0, maxY));
	}

	private int toSpeed(double value) {
		int speed = (int) Math.round(value);
		if (speed == 0 && Math.abs(value) > 0.15) {
			speed = value > 0 ? 1 : -1;
		}
		return clamp(speed, -MAX_SPEED, MAX_SPEED);
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
	
	private int cell(int position) {
		return Math.floorDiv(position, CELL_SIZE);
	}
	
	private long bucketKey(int x, int y) {
		return (((long) x) << 32) ^ (y & 0xffffffffL);
	}
	
}
