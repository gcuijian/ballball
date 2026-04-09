package ballball.ui;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import ballball.config.AppConfig;
import ballball.model.SimulationState;
import ballball.model.WorldBounds;
import ballball.physics.BallFactory;
import ballball.physics.PhysicsEngine;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 4633674402338209613L;

	private final BallPanel panel;
	private final PhysicsEngine engine;
	private final AppConfig config;
	private long previousTickNanos = System.nanoTime();
	private double accumulatorSeconds;

	public MainFrame(SimulationState state, PhysicsEngine engine, BallFactory factory, AppConfig config) {
		this.engine = engine;
		this.config = config;
		this.panel = new BallPanel(state);

		setSize(config.getWindowWidth(), config.getWindowHeight());
		setTitle(config.getWindowTitle());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		addMouseListener(new BallMouseAdapter(engine, factory));
		add(panel);
		startLoop();
	}

	public void showWindow() {
		setVisible(true);
	}

	private void startLoop() {
		Timer timer = new Timer(config.getFrameDelayMillis(), event -> tick());
		timer.setCoalesce(true);
		timer.start();
	}

	private void tick() {
		engine.setBounds(new WorldBounds(panel.getWidth(), panel.getHeight()));
		if (!engine.getBounds().isValid()) {
			return;
		}

		long now = System.nanoTime();
		double frameSeconds = (now - previousTickNanos) / 1_000_000_000.0;
		previousTickNanos = now;
		accumulatorSeconds += Math.min(frameSeconds, engine.getFixedStepSeconds() * config.getMaxSubSteps());

		int steps = 0;
		while (accumulatorSeconds >= engine.getFixedStepSeconds() && steps < config.getMaxSubSteps()) {
			engine.step();
			accumulatorSeconds -= engine.getFixedStepSeconds();
			steps++;
		}

		double alpha = accumulatorSeconds / engine.getFixedStepSeconds();
		panel.setInterpolationAlpha(Math.max(0.0, Math.min(1.0, alpha)));
		panel.repaint();
	}
}
