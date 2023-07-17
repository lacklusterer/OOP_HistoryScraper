package database;

import java.util.HashSet;
import java.util.Set;

import entity.Character;
import entity.Event;
import entity.Festival;
import entity.Reign;
import entity.Relic;

public class Database {
	Set<Character> characters = new HashSet<>();
	Set<Event> events = new HashSet<>();
	Set<Festival> festivals = new HashSet<>();
	Set<Reign> reigns = new HashSet<>();
	Set<Relic> relics = new HashSet<>();

	public Set<Character> getCharacters() { return characters; }
	public Set<Event> getEvents() { return events; }
	public Set<Festival> getFestivals() { return festivals; }
	public Set<Reign> getReigns() { return reigns; }
	public Set<Relic> getRelics() { return relics; }
}
