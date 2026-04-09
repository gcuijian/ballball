package ballball.model;

import java.awt.Color;

public class Ball {

	private static final double JELLY_DECAY = 0.72;
	private static final double JELLY_EPSILON = 0.01;

	private double x;
	private double y;
	private double previousX;
	private double previousY;
	private final int size;
	private final Color color;
	private double speedX;
	private double speedY;
	private double jellyX;
	private double jellyY;

	public Ball(double x, double y, int size, Color color, double speedX, double speedY) {
		this.x = x;
		this.y = y;
		this.previousX = x;
		this.previousY = y;
		this.size = size;
		this.color = color;
		this.speedX = speedX;
		this.speedY = speedY;
	}

	public void beginStep() {
		previousX = x;
		previousY = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public double getRadius() {
		return size / 2.0;
	}

	public Color getColor() {
		return color;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public double getJellyX() {
		return jellyX;
	}

	public double getJellyY() {
		return jellyY;
	}

	public double getInterpolatedCenterX(double alpha) {
		return lerp(previousX, x, alpha) + getRadius();
	}

	public double getInterpolatedCenterY(double alpha) {
		return lerp(previousY, y, alpha) + getRadius();
	}

	public void applyJelly(double normalX, double normalY, double strength) {
		double compression = Math.min(0.24, 0.08 + (strength * 0.03));
		double axisX = Math.abs(normalX);
		double axisY = Math.abs(normalY);
		double nextJellyX = (-compression * axisX) + (compression * 0.85 * axisY);
		double nextJellyY = (-compression * axisY) + (compression * 0.85 * axisX);
		jellyX = preferLargerMagnitude(jellyX, nextJellyX);
		jellyY = preferLargerMagnitude(jellyY, nextJellyY);
	}

	public void relaxJelly() {
		jellyX = decay(jellyX);
		jellyY = decay(jellyY);
	}

	private double lerp(double start, double end, double alpha) {
		return start + ((end - start) * alpha);
	}

	private double decay(double value) {
		double next = value * JELLY_DECAY;
		if (Math.abs(next) < JELLY_EPSILON) {
			return 0;
		}
		return next;
	}

	private double preferLargerMagnitude(double current, double next) {
		if (Math.abs(next) > Math.abs(current)) {
			return next;
		}
		return current;
	}
}
