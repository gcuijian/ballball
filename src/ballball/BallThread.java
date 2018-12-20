package ballball;

public class BallThread extends Thread {

	private Ball ball;
//	private int status;
	
	public BallThread(Ball ball) {
		super();
		this.ball = ball;
	}

	public void run() {
		
		//随机一个方向
//		status = ((int)(Math.random() * 4)) + 1;
		
		while(true) {
			//撞墙操作
			int i = 0;
			if((i = ballWall()) == 0) {
				//根据速度移动
				ballMove();
			} else {
				ballPosition(i);
			}
//			statusControl();
			Core.balls.put(this.getId(), ball);
			//保证25帧的流畅
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
	
	
	//小球状态控制
//	private void statusControl() throws Exception {
//		if(status == 1)
//			rightDown();
//		if(status == 2)
//			leftDown();
//		if(status == 3)
//			rightUp();
//		if(status == 4)
//			leftUp();
//		//触下
//		if((y + 50) > Core.windowHeight) {
//			//右下移动触下
//			if(status == 0 || status == 1)
//				status = 3;
//			//左下移动触下
//			if(status == 2)
//				status = 4;
//		}
//		//触上
//		if(y < 0) {
//			//左上
//			if(status == 4)
//				status = 2;
//			//右上
//			if(status == 3)
//				status = 1;
//		}
//		//触左
//		if(x < 0) {
//			//左下
//			if(status == 2)
//				status = 1;
//			//右下
//			if(status == 4)
//				status = 3;
//		}
//		//触右
//		if((x + 50) > Core.windowWidth) {
//			//右上
//			if(status == 3)
//				status = 4;
//			//右下
//			if(status == 1)
//				status = 2;
//		}
//	}
	
//	private void rightDown() throws InterruptedException {
//		y++;
//		x++;
//		Thread.sleep(40);
//	}
//	private void leftDown() throws InterruptedException {
//		y++;
//		x--;
//		Thread.sleep(40);
//	}
//	private void rightUp() throws InterruptedException {
//		y--;
//		x++;
//		Thread.sleep(40);
//	}
//	private void leftUp() throws InterruptedException {
//		y--;
//		x--;
//		Thread.sleep(40);
//	}
	
}
