package ballball.physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ballball.config.AppConfig;
import ballball.model.Ball;
import ballball.model.WorldBounds;

public class CollisionSystem {

	private final int cellSize;
	private final double restitution;
	private final double minBounceSpeed;
	private final double maxBallSpeedCap;
	private final HashMap<Long, List<Ball>> buckets = new HashMap<>();

	public CollisionSystem(AppConfig config) {
		this.cellSize = config.getGridCellSize();
		this.restitution = config.getRestitution();
		this.minBounceSpeed = config.getMinBounceSpeed();
		this.maxBallSpeedCap = config.getMaxBallSpeedCap();
	}

	public void resolve(List<Ball> balls, WorldBounds bounds) {
		if (balls.size() <= 1) {
			return;
		}

		buckets.clear();
		for (Ball ball : balls) {
			int cellX = cell(ball.getX() + ball.getRadius());
			int cellY = cell(ball.getY() + ball.getRadius());
			for (int y = cellY - 1; y <= cellY + 1; y++) {
				for (int x = cellX - 1; x <= cellX + 1; x++) {
					resolveBucketCollisions(ball, bucketKey(x, y), bounds);
				}
			}
			buckets.computeIfAbsent(bucketKey(cellX, cellY), key -> new ArrayList<>()).add(ball);
		}
	}

	private void resolveBucketCollisions(Ball current, long key, WorldBounds bounds) {
		List<Ball> bucket = buckets.get(key);
		if (bucket == null) {
			return;
		}
		for (Ball other : bucket) {
			resolvePair(current, other, bounds);
		}
	}

	public void resolvePair(Ball first, Ball second, WorldBounds bounds) {
		double centerX1 = first.getX() + first.getRadius();
		double centerY1 = first.getY() + first.getRadius();
		double centerX2 = second.getX() + second.getRadius();
		double centerY2 = second.getY() + second.getRadius();
		double dx = centerX2 - centerX1;
		double dy = centerY2 - centerY1;
		double distSq = (dx * dx) + (dy * dy);
		double radiusSum = first.getRadius() + second.getRadius();
		if (distSq > radiusSum * radiusSum) {
			return;
		}

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

		resolveOverlap(first, second, normalX, normalY, radiusSum - dist, bounds);

		double relativeVelocityX = second.getSpeedX() - first.getSpeedX();
		double relativeVelocityY = second.getSpeedY() - first.getSpeedY();
		double velocityAlongNormal = (relativeVelocityX * normalX) + (relativeVelocityY * normalY);
		if (velocityAlongNormal > -minBounceSpeed) {
			velocityAlongNormal = -minBounceSpeed;
		}

		double impulse = -((1.0 + restitution) * velocityAlongNormal) / 2.0;
		double impulseX = impulse * normalX;
		double impulseY = impulse * normalY;

		first.setSpeedX(limitSpeed(first.getSpeedX() - impulseX));
		first.setSpeedY(limitSpeed(first.getSpeedY() - impulseY));
		second.setSpeedX(limitSpeed(second.getSpeedX() + impulseX));
		second.setSpeedY(limitSpeed(second.getSpeedY() + impulseY));

		double jellyStrength = Math.max(minBounceSpeed, Math.abs(velocityAlongNormal));
		first.applyJelly(normalX, normalY, jellyStrength);
		second.applyJelly(normalX, normalY, jellyStrength);
	}

	private void resolveOverlap(Ball first, Ball second, double normalX, double normalY, double overlap,
			WorldBounds bounds) {
		if (overlap <= 0) {
			return;
		}
		double separation = (overlap / 2.0) + 0.5;
		moveBall(first, -normalX * separation, -normalY * separation, bounds);
		moveBall(second, normalX * separation, normalY * separation, bounds);
	}

	private void moveBall(Ball ball, double offsetX, double offsetY, WorldBounds bounds) {
		double nextX = ball.getX() + offsetX;
		double nextY = ball.getY() + offsetY;
		double maxX = Math.max(0, bounds.getWidth() - ball.getSize());
		double maxY = Math.max(0, bounds.getHeight() - ball.getSize());
		ball.setX(clamp(nextX, 0, maxX));
		ball.setY(clamp(nextY, 0, maxY));
	}

	public double limitSpeed(double value) {
		return clamp(value, -maxBallSpeedCap, maxBallSpeedCap);
	}

	private int cell(double position) {
		return (int) Math.floor(position / cellSize);
	}

	private long bucketKey(int x, int y) {
		return (((long) x) << 32) ^ (y & 0xffffffffL);
	}

	private double clamp(double value, double min, double max) {
		if (value < min) {
			return min;
		}
		if (value > max) {
			return max;
		}
		return value;
	}
}
