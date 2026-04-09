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
		int dx = (b1.getX() + (b1.getSize() / 2)) - (b2.getX() + (b2.getSize() / 2));
		int dy = (b1.getY() + (b1.getSize() / 2)) - (b2.getY() + (b2.getSize() / 2));
		int distSq = dx * dx + dy * dy;
		
		int radiusSum = (b1.getSize() / 2) + (b2.getSize() / 2);
		if (distSq <= radiusSum * radiusSum) {
			changeSpeed(b1, b2);
		}
	}
	
	//对有碰撞的两个球做速度的改变
	private void changeSpeed(Ball b1, Ball b2) {
		//碰撞互换速度
		int speedX = b1.getSpeedX();
		b1.setSpeedX(b2.getSpeedX());
		b2.setSpeedX(speedX);
		
		int speedY = b1.getSpeedY();
		b1.setSpeedY(b2.getSpeedY());
		b2.setSpeedY(speedY);
	}
	
	private int cell(int position) {
		return Math.floorDiv(position, CELL_SIZE);
	}
	
	private long bucketKey(int x, int y) {
		return (((long) x) << 32) ^ (y & 0xffffffffL);
	}
	
}
