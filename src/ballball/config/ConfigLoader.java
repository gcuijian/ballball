package ballball.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class ConfigLoader {

	private static final Path CONFIG_PATH = Paths.get("config", "ballball.properties");

	private ConfigLoader() {
	}

	public static AppConfig load() {
		Properties properties = new Properties();
		if (Files.exists(CONFIG_PATH)) {
			try (InputStream inputStream = Files.newInputStream(CONFIG_PATH)) {
				properties.load(inputStream);
			} catch (IOException exception) {
				throw new IllegalStateException("Failed to load config: " + CONFIG_PATH, exception);
			}
		}

		return new AppConfig(
				intProperty(properties, "window.width", 800),
				intProperty(properties, "window.height", 600),
				properties.getProperty("window.title", "ballball"),
				intProperty(properties, "loop.frameDelayMillis", 16),
				doubleProperty(properties, "loop.fixedStepSeconds", 0.016),
				intProperty(properties, "loop.maxSubSteps", 5),
				intProperty(properties, "ball.size.min", 8),
				intProperty(properties, "ball.size.max", 60),
				doubleProperty(properties, "ball.speed.min", 1.5),
				doubleProperty(properties, "ball.speed.max", 10.0),
				intProperty(properties, "physics.gridCellSize", 64),
				doubleProperty(properties, "physics.restitution", 1.05),
				doubleProperty(properties, "physics.minBounceSpeed", 1.2),
				doubleProperty(properties, "physics.maxBallSpeedCap", 14.0));
	}

	private static int intProperty(Properties properties, String key, int defaultValue) {
		return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
	}

	private static double doubleProperty(Properties properties, String key, double defaultValue) {
		return Double.parseDouble(properties.getProperty(key, String.valueOf(defaultValue)));
	}
}
