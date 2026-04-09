package ballball.logic;

import ballball.model.Ball;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	private List<Long> keys;
	
	private Map<Long, Ball> balls;
	
	private BallPeng() {}
	
	public static final BallPeng ballPeng = new BallPeng();
	
	//入口类
	public Map<Long, Ball> isCollision(Map<Long, Ball> balls) {
		//如果长度为零或者1，不存在碰撞直接返回。
		if(balls == null || balls.size() <= 1)
			return balls;
		this.balls = balls;
		keys = new ArrayList<>(this.balls.keySet());
		
		//将list中的keys从第1项与二项以后的分别对比，然后是2项与以后的分别对比，防止重复比对的情况
		for (int i = 0; i < keys.size(); i++) {
			for (int j = i + 1; j < keys.size(); j++) {
				assessCollision(keys.get(i), keys.get(j));
			}
		}
		
		return this.balls;
	}
	
	//对比两个球球是否碰撞，如果碰撞就交给 changeSpeed ，没碰撞就算了
	private void assessCollision(Long ballKey1, Long ballKey2) {
		Ball b1 = balls.get(ballKey1);
		Ball b2 = balls.get(ballKey2);
		if (b1 == null || b2 == null) return;

		int dx = b1.getX() - b2.getX();
		int dy = b1.getY() - b2.getY();
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
	
}
