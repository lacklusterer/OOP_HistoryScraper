package entity;

import java.util.HashSet;
import java.util.Set;

import entity.type.Date;

public class Festival extends Entity {
	private final Date date;
	private final Set<String> characters = new HashSet<>();
	private final Set<String> events = new HashSet<>();
	private final Set<String> relics = new HashSet<>();

	public Festival(
		final String name,
		final Date date,
		final String source
	) {
		super(name, source);
		this.date = date;
	}

	public Date getDate() { return date; }
	public Set<String> getCharacters() { return characters; }
	public Set<String> getEvents() { return events; }
	public Set<String> getRelics() { return relics; }
}
