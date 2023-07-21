package entity;

/**
 * Is actually "Di tích"
 */
public class Relic extends Entity {
	private final String location;
	private final Integer found;

	public Relic(
		final String name,
		final String location,
		final Integer found,
		final String source
	) {
		super(name, source);
		this.location = location;
		this.found = found;
	}

	public String getLocation() { return location; }
	public Integer getFound() { return found; }
}
