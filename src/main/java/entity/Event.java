package entity;

public class Event extends Entity {
	private final Integer year;

	public Event(
		final String name,
		final Integer year,
		final String source
	) {
		super(name, source);
		this.year = year;
	}

	public Integer getYear() { return year; }
}
