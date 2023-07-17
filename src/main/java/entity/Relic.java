package entity;

public class Relic extends Entity {
	private String location;
	private Integer found;

	public Relic(
		String name,
		String location,
		Integer found,
		String source
	) {
		super(name, source);
		this.location = location;
		this.found = found;
	}

	public String getLocation() { return location; }
	public Integer getFound() { return found; }
}
