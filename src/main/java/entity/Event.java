package entity;

import java.util.HashSet;
import java.util.Set;

public class Event extends Entity {
	private final Integer year;
	private final Set<String> characters = new HashSet<>();

	public Event(
		final String name,
		final Integer year,
		final String source
	) {
		super(name, source);
		this.year = year;
	}

	public Integer getYear() { return year; }
	public Set<String> getCharacters() { return characters; }
}
