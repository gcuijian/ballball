package ballball.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ballball.model.SimulationState;
import ballball.render.BallRenderer;

public class BallPanel extends JPanel {

	private static final long serialVersionUID = 8330425786435304407L;

	private final SimulationState state;
	private final BallRenderer renderer = new BallRenderer();
	private double interpolationAlpha;

	public BallPanel(SimulationState state) {
		this.state = state;
	}

	public void setInterpolationAlpha(double interpolationAlpha) {
		this.interpolationAlpha = interpolationAlpha;
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D graphics2d = (Graphics2D) graphics.create();
		renderer.render(graphics2d, state.getBalls(), interpolationAlpha);
		graphics2d.dispose();
	}
}
