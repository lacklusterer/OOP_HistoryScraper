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
		String location,
		Date date,
		String source
	) {
		super(name, source);
		this.date = date;
	}

	public void addCharacter(String character) { characters.add(character); }
	public void addEvent(String location) { events.add(location); }
	public void addRelic(String location) { relics.add(location); }

	public Date getDate() { return date; }
	public Set<String> getCharacters() { return characters; }
	public Set<String> getEvents() { return events; }
	public Set<String> getRelics() { return relics; }
}
