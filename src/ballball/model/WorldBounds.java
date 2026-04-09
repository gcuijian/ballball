package ballball.model;

public class WorldBounds {

	public static final WorldBounds EMPTY = new WorldBounds(0, 0);

	private final int width;
	private final int height;

	public WorldBounds(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isValid() {
		return width > 0 && height > 0;
	}
}
