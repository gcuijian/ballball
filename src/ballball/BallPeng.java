package ballball;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	//入口类
	public Map<Long, Ball> isCollision(Map<Long, Ball> balls) {
		//如果长度为零或者1，不存在碰撞直接返回。
		if(balls.isEmpty() || balls.size() == 1)
			return balls;
		this.balls = balls;
		int i = -1;
		keys = new ArrayList<Long>();
		//将key放入keys，顺便完成了第一轮遍历
		for (Long key : balls.keySet()) {
			keys.add(key);
			i++;
			if(i == 0) continue;
			assessCollision(balls.get(keys.get(0)), balls.get(key));
		}
		//如果做出了改变，需要在成员变量里直接做出改变即可返回。
		if(balls.size() == 2) return this.balls;
		//将list中的keys从第二项与二项以后的分别对比，然后是三项与以后的分别对比，防止重复比对的情况
		for (i = 1; i < keys.size(); i++) {
			for (int j = i + 1; j < keys.size(); j++) {
				assessCollision(balls.get(keys.get(i)), balls.get(keys.get(j)));
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
//		Map<Long, Ball> balls = new HashMap<Long, Ball>();
//		balls.put(1L, new Ball(0, 0, 0));
//		balls.put(2L, new Ball(0, 0, 0));
//		balls.put(3L, new Ball(0, 0, 0));
//		balls.put(4L, new Ball(0, 0, 0));
//		balls.put(5L, new Ball(0, 0, 0));
//		balls.put(6L, new Ball(0, 0, 0));
//		BallPeng b = new BallPeng();
//		b.isCollision(balls);
	}
	
	//对比两个球球是否碰撞，如果碰撞就交给 changeSpeed ，没碰撞就算了
	private void assessCollision(Ball ball, Ball SecondBall) {
		System.out.println("比较一次la~");
	}
	
	//对有碰撞的两个球做速度的改变
	private void changeSpeed(Long key, Long Secondkey) {
		
	}
	
}
