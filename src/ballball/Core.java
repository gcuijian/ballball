package ballball;

import javax.swing.SwingUtilities;

import ballball.config.AppConfig;
import ballball.config.ConfigLoader;
import ballball.model.SimulationState;
import ballball.physics.BallFactory;
import ballball.physics.PhysicsEngine;
import ballball.ui.MainFrame;

public final class Core {

	private Core() {
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			AppConfig config = ConfigLoader.load();
			SimulationState state = new SimulationState();
			BallFactory factory = new BallFactory(config);
			PhysicsEngine engine = new PhysicsEngine(state, config);
			MainFrame frame = new MainFrame(state, engine, factory, config);
			frame.showWindow();
		});
	}
}
