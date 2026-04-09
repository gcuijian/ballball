package ballball.physics;

import ballball.config.AppConfig;
import ballball.model.Ball;
import ballball.model.SimulationState;
import ballball.model.WorldBounds;

public class PhysicsEngine {

	private final SimulationState state;
	private final MotionSystem motionSystem = new MotionSystem();
	private final CollisionSystem collisionSystem;
	private final double fixedStepSeconds;

	private WorldBounds bounds = WorldBounds.EMPTY;

	public PhysicsEngine(SimulationState state, AppConfig config) {
		this.state = state;
		this.collisionSystem = new CollisionSystem(config);
		this.fixedStepSeconds = config.getFixedStepSeconds();
	}

	public void setBounds(WorldBounds bounds) {
		this.bounds = bounds;
	}

	public WorldBounds getBounds() {
		return bounds;
	}

	public double getFixedStepSeconds() {
		return fixedStepSeconds;
	}

	public void step() {
		if (!bounds.isValid()) {
			return;
		}
		for (Ball ball : state.getBalls()) {
			ball.beginStep();
			motionSystem.advance(ball, bounds, fixedStepSeconds);
		}
		collisionSystem.resolve(state.getBalls(), bounds);
	}

	public void addBall(Ball ball) {
		state.addBall(ball);
	}
}
