package ballball;

import java.awt.Color;

/**
 * 
 * <p>位置，大小，
 * 颜色，速度(横向，纵向)，非45°角</p>
 * @ClassName Ball
 * @Description 
 * @Author Cui Jian
 * @version
 * @Date 2018年12月19日 下午7:59:48
 */

public class Ball {

	private int x;
	private int y;
	
	//1-100
	private int size;
	
	private Color color;
	
	/**
	 * 根据横竖速度不同决定总速度
	 * 速度在-10 —— 10之间，可以为零，不可都为零
	 */
	private int speedX;
	private int speedY;
	
	public Ball(int x, int y, int size, Color color, int speedX, int speedY) {
		super();
		this.x = x;
		this.y = y;
		this.size = size;
		this.color = color;
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getSpeedX() {
		return speedX;
	}
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	public int getSpeedY() {
		return speedY;
	}
	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	@Override
	public String toString() {
		return "Ball [x=" + x + ", y=" + y + ", size=" + size + ", color=" + color + ", speedX=" + speedX + ", speedY="
				+ speedY + "]";
	}
	
}
