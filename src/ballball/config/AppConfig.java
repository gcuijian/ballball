package ballball.config;

public class AppConfig {

	private final int windowWidth;
	private final int windowHeight;
	private final String windowTitle;
	private final int frameDelayMillis;
	private final double fixedStepSeconds;
	private final int maxSubSteps;
	private final int minBallSize;
	private final int maxBallSize;
	private final double minBallSpeed;
	private final double maxBallSpeed;
	private final int gridCellSize;
	private final double restitution;
	private final double minBounceSpeed;
	private final double maxBallSpeedCap;

	public AppConfig(int windowWidth, int windowHeight, String windowTitle, int frameDelayMillis,
			double fixedStepSeconds, int maxSubSteps, int minBallSize, int maxBallSize, double minBallSpeed,
			double maxBallSpeed, int gridCellSize, double restitution, double minBounceSpeed,
			double maxBallSpeedCap) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.windowTitle = windowTitle;
		this.frameDelayMillis = frameDelayMillis;
		this.fixedStepSeconds = fixedStepSeconds;
		this.maxSubSteps = maxSubSteps;
		this.minBallSize = minBallSize;
		this.maxBallSize = maxBallSize;
		this.minBallSpeed = minBallSpeed;
		this.maxBallSpeed = maxBallSpeed;
		this.gridCellSize = gridCellSize;
		this.restitution = restitution;
		this.minBounceSpeed = minBounceSpeed;
		this.maxBallSpeedCap = maxBallSpeedCap;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public String getWindowTitle() {
		return windowTitle;
	}

	public int getFrameDelayMillis() {
		return frameDelayMillis;
	}

	public double getFixedStepSeconds() {
		return fixedStepSeconds;
	}

	public int getMaxSubSteps() {
		return maxSubSteps;
	}

	public int getMinBallSize() {
		return minBallSize;
	}

	public int getMaxBallSize() {
		return maxBallSize;
	}

	public double getMinBallSpeed() {
		return minBallSpeed;
	}

	public double getMaxBallSpeed() {
		return maxBallSpeed;
	}

	public int getGridCellSize() {
		return gridCellSize;
	}

	public double getRestitution() {
		return restitution;
	}

	public double getMinBounceSpeed() {
		return minBounceSpeed;
	}

	public double getMaxBallSpeedCap() {
		return maxBallSpeedCap;
	}
}
