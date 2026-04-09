package ballball.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ballball.physics.BallFactory;
import ballball.physics.PhysicsEngine;

public class BallMouseAdapter extends MouseAdapter {

	private final PhysicsEngine engine;
	private final BallFactory factory;

	public BallMouseAdapter(PhysicsEngine engine, BallFactory factory) {
		this.engine = engine;
		this.factory = factory;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		engine.addBall(factory.createAt(event.getX(), event.getY()));
	}
}
