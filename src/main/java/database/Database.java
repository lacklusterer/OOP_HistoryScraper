package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import entity.Character;
import entity.Entity;
import entity.Event;
import entity.Festival;
import entity.Reign;
import entity.Relic;

/**
 * Used for storing infomation for all types of entites.
 */
public class Database {
	private final static Gson gson = new Gson();

	private transient final Map<String, Set<Entity>> wordCache = new HashMap<>();
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

	/**
	 * From: https://www.baeldung.com/java-remove-accents-from-text
	 */
	private static List<String> getWords(String input) {
		return Arrays.asList(Normalizer
			.normalize(input, Normalizer.Form.NFKD)
			.replaceAll("\\p{M}", "")
			.replaceAll("[\\(\\)]+", "")
			.replaceAll("[\\-–\\.,]+", " ")
			.toLowerCase()
			.split(" ")
		)
			.stream()
			.filter(s -> s.length() != 0)
			.collect(Collectors.toList());
	}

	private void cacheEntity(String word, Entity entity) {
		if (!wordCache.containsKey(word))
			wordCache.put(word, new HashSet<>());
		wordCache.get(word).add(entity);
	}
	private void cacheEntity(Entity entity) {
		for (var word: getWords(entity.getName())) cacheEntity(word, entity);
	}
	public void generateCache() {
		wordCache.clear();

		// Cache from names of entities
		for (var e: characters) cacheEntity(e);
		for (var e: events) cacheEntity(e);
		for (var e: festivals) cacheEntity(e);
		for (var e: reigns) cacheEntity(e);
		for (var e: relics) cacheEntity(e);

		// More info from character
		for (var c: characters) {
			List<String> extraWords = new ArrayList<>();
			for (var alias: c.getAliases()) extraWords.addAll(getWords(alias));
			if (c.getFather() != null) extraWords.addAll(getWords(c.getFather()));
			for (var word: extraWords) cacheEntity(word, c);
		}

		// More on events
		for (var e: events) {
			List<String> extraWords = new ArrayList<>();
			for (var c: e.getCharacters()) extraWords.addAll(getWords(c));
			for (var word: extraWords) cacheEntity(word, e);
		}
	}

	public Set<Entity> search(String query) {
		boolean added = false;
		Set<Entity> results = new HashSet<>();

		for (var word: getWords(query)) {
			// Get all entity for this word in the query
			Set<Entity> thisWord = new HashSet<>();
			for (var key: wordCache.keySet())
				if (key.contains(word))
					thisWord.addAll(wordCache.get(key));

			// Perform set collision for each word
			if (added) results.retainAll(thisWord);
			else {
				results.addAll(thisWord);
				added = true;
			}
		}

		return results;
	}
}
