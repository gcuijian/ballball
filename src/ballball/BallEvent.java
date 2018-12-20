package ballball;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BallEvent {

	public BallEvent(){
		super();
	}
	
	public MouseListener mouseClick() {
		return new MouseListener() {
			
			//一次按下后抬起的点击事件
			@Override
			public void mouseClicked(MouseEvent e) {
				Core.addBall(e.getX(), e.getY());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
		};
	}
	
}
