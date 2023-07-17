package entity;

import java.util.HashSet;
import java.util.Set;

import entity.type.Date;

public class Festival extends Entity {
	private Date date;
	private Set<String> characters = new HashSet<>();
	private Set<String> events = new HashSet<>();
	private Set<String> relics = new HashSet<>();

	public Festival(
		String name,
		Date date,
		String source
	) {
		super(name, source);
		this.date = date;
	}

	public Date getDate() { return date; }
	public Set<String> getCharacters() { return characters; }
	public Set<String> getEvents() { return events; }
	public Set<String> getRelics() { return relics; }
}
