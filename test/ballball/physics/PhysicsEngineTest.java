package ballball.physics;

import java.awt.Color;

import ballball.config.AppConfig;
import ballball.model.Ball;
import ballball.model.SimulationState;
import ballball.model.WorldBounds;

public final class PhysicsEngineTest {

	private PhysicsEngineTest() {
	}

	public static void main(String[] args) {
		testWallBounce();
		testCollisionBounce();
		testSpeedCap();
		System.out.println("All physics tests passed.");
	}

	private static void testWallBounce() {
		MotionSystem motionSystem = new MotionSystem();
		Ball ball = new Ball(1, 20, 20, Color.RED, -5, 0);
		ball.beginStep();
		motionSystem.advance(ball, new WorldBounds(120, 120), 0.016);
		assertTrue(ball.getX() >= 0, "ball should stay inside left wall");
		assertTrue(ball.getSpeedX() > 0, "ball should bounce on left wall");
	}

	private static void testCollisionBounce() {
		AppConfig config = defaultConfig();
		CollisionSystem collisionSystem = new CollisionSystem(config);
		Ball first = new Ball(20, 20, 20, Color.RED, 4, 0);
		Ball second = new Ball(36, 20, 20, Color.BLUE, -4, 0);
		collisionSystem.resolvePair(first, second, new WorldBounds(200, 200));
		assertTrue(first.getSpeedX() < 0, "first ball should rebound left");
		assertTrue(second.getSpeedX() > 0, "second ball should rebound right");
		assertTrue(second.getX() > first.getX(), "balls should be separated after collision");
	}

	private static void testSpeedCap() {
		AppConfig config = defaultConfig();
		CollisionSystem collisionSystem = new CollisionSystem(config);
		double limited = collisionSystem.limitSpeed(99.0);
		assertEquals(config.getMaxBallSpeedCap(), limited, 0.0001, "speed should be capped");
	}

	private static AppConfig defaultConfig() {
		return new AppConfig(800, 600, "ballball", 16, 0.016, 5, 8, 60, 1.5, 10.0, 64, 1.05, 1.2, 14.0);
	}

	private static void assertTrue(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}

	private static void assertEquals(double expected, double actual, double tolerance, String message) {
		if (Math.abs(expected - actual) > tolerance) {
			throw new AssertionError(message + ": expected=" + expected + ", actual=" + actual);
		}
	}
}
