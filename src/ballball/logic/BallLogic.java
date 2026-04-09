package ballball.logic;

import ballball.Core;
import ballball.model.Ball;

public class BallLogic {

	private Ball ball;
	
	public BallLogic(Ball ball) {
		this.ball = ball;
	}

	public void update() {
		//撞墙操作
		int i = 0;
		if((i = ballWall()) == 0) {
			//根据速度移动
			ballMove();
		} else {
			ballPosition(i);
		}
	}
	
	//根据速度控制小球移动
	private void ballMove() {
		ball.setX(ball.getX() + ball.getSpeedX());
		ball.setY(ball.getY() + ball.getSpeedY());
	}
	
	/**
	 * 要处理即将撞墙问题
	 */
	
	//即将撞墙的位置手动预测并将位置改进去
	private void ballPosition(int i) {
		if(i == 1) {
			ball.setX(ball.getSpeedX() - ball.getX());
		}
		if(i == 2) {
			ball.setY(ball.getSpeedY() - ball.getY());
		}
		if(i == 3) {
			ball.setX((Core.windowWidth * 2) - (0 - ball.getSpeedX()) - ball.getX() - (ball.getSize() * 2));
		}
		if(i == 4) {
			ball.setY((Core.windowHeight * 2) - (0 - ball.getSpeedY()) - ball.getY() - (ball.getSize() * 2));
		}
	}
	
	//当小球与某一个墙壁距离小于速度时，改变速度 左、上、右、下
	private int ballWall() {
		int i = 0;
		//横向
		if((ball.getX() < (0 - ball.getSpeedX()) && ball.getSpeedX() < 0)) {
			ball.setSpeedX(0 - ball.getSpeedX());
			i = 1;
		}
		if((Core.windowWidth - ball.getX() - ball.getSize()) < ball.getSpeedX() && ball.getSpeedX() > 0) {
			ball.setSpeedX(0 - ball.getSpeedX());
			i = 3;
		}
		//纵向
		if(ball.getY() < (0 - ball.getSpeedY()) && ball.getSpeedY() < 0) {
			ball.setSpeedY(0 - ball.getSpeedY());
			i = 2;
		}
		if(Core.windowHeight - ball.getY() - ball.getSize() < ball.getSpeedY() && ball.getSpeedY() > 0) {
			ball.setSpeedY(0 - ball.getSpeedY());
			i = 4;
		}
		//加一层判断，如果是小于速度，返回false,如果大于等于速度,返回true
		return i;
	}
	
}
