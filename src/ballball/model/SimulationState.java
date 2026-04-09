package ballball.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulationState {

	private final List<Ball> balls = new ArrayList<>();

	public List<Ball> getBalls() {
		return Collections.unmodifiableList(balls);
	}

	public void addBall(Ball ball) {
		balls.add(ball);
	}
}
