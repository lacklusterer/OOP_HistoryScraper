package com.cations.oop.project.entity;

import java.util.HashSet;
import java.util.Set;
import com.cations.oop.project.entity.atom.Year;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class Character extends Entity {
	private Set<String> aliases = new HashSet<>();
	private transient Character father;
	private transient Set<Character> children = new HashSet<>();
	private transient Set<Event> events = new HashSet<>();
	private Set<Year> crowned = new HashSet<>();
	private Set<Year> birth = new HashSet<>();
	private Set<Year> death = new HashSet<>();

	public Character(String name) { super(name); }

	public void addChild(Character child) {
		assert(Entity.getId(child) != null);
		if (child.father != null)
			throw new RuntimeException("Character has a father");
		child.father = this;
		children.add(child);
	}
	public void addYearCrowned(Year year) { crowned.add(year); }
	public void addYearBirth(Year year) { birth.add(year); }
	public void addYearDeath(Year year) { death.add(year); }
	public void addAlias(String alias) {
		if (!name.equals(alias))
			aliases.add(alias);
	}
	void addEvent(Event event) { events.add(event); }

	public Set<Event> getEvents() { return events; }

	@Override
	public String toJson() {
		var childIds = new JsonArray();
		for (var child: children) childIds.add(Entity.getId(child));
		var eventIds = new JsonArray();
		for (var event: events) eventIds.add(Entity.getId(event));

		JsonElement elem = gson.toJsonTree(this);
		elem.getAsJsonObject().addProperty("father", Entity.getId(father));
		elem.getAsJsonObject().add("children", childIds);
		elem.getAsJsonObject().add("events", eventIds);
		return gson.toJson(elem);
	}
}
