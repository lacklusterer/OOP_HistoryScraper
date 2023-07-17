package entity;

public class Event extends Entity {
	private Integer year;

	public Event(
		String name,
		Integer year,
		String source
	) {
		super(name, source);
		this.year = year;
	}

	public Integer getYear() { return year; }
}
