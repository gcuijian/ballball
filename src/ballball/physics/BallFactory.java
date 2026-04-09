package ballball.physics;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import ballball.config.AppConfig;
import ballball.model.Ball;

public class BallFactory {

	private static final Color[] COLORS = {
			Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY,
			Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA,
			Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW
	};

	private final AppConfig config;

	public BallFactory(AppConfig config) {
		this.config = config;
	}

	public Ball createAt(double clickX, double clickY) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int size = random.nextInt(config.getMinBallSize(), config.getMaxBallSize() + 1);
		double speedX = 0;
		double speedY = 0;
		while (Math.abs(speedX) < 0.01 && Math.abs(speedY) < 0.01) {
			speedX = randomSpeed(random);
			speedY = randomSpeed(random);
		}
		Color color = COLORS[random.nextInt(COLORS.length)];
		double x = clickX - (size / 2.0);
		double y = clickY - size;
		return new Ball(x, y, size, color, speedX, speedY);
	}

	private double randomSpeed(ThreadLocalRandom random) {
		double speed = random.nextDouble(config.getMinBallSpeed(), config.getMaxBallSpeed());
		return random.nextBoolean() ? speed : -speed;
	}
}
