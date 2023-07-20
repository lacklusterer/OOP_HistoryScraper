package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;

import entity.Character;
import entity.Event;
import entity.Festival;
import entity.Reign;
import entity.Relic;

/**
 * Used for storing infomation for all types of entites.
 */
public class Database {
	private final static Gson gson = new Gson();

	private final Set<Character> characters = new HashSet<>();
	private final Set<Event> events = new HashSet<>();
	private final Set<Festival> festivals = new HashSet<>();
	private final Set<Reign> reigns = new HashSet<>();
	private final Set<Relic> relics = new HashSet<>();

	public Set<Character> getCharacters() { return characters; }
	public Set<Event> getEvents() { return events; }
	public Set<Festival> getFestivals() { return festivals; }
	public Set<Reign> getReigns() { return reigns; }
	public Set<Relic> getRelics() { return relics; }

	public void merge(Database other) {
		if (other == null) return;

		characters.addAll(other.characters);
		events.addAll(other.events);
		festivals.addAll(other.festivals);
		reigns.addAll(other.reigns);
		relics.addAll(other.relics);
	}

	public static Database read(String fileName) {
		try {
			var reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
			return gson.fromJson(reader, Database.class);
		}
		catch (FileNotFoundException exc) {}
		return null;
	}
	public void write(String fileName) {
		try {
			var writer = new OutputStreamWriter(new FileOutputStream(fileName, false), StandardCharsets.UTF_8);
			writer.write(gson.toJson(this));
			writer.close();
		}
		catch (IOException exc) { return; }
	}
}
