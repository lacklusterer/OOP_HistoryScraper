package com.cations.oop.project.entity;

import java.util.HashSet;
import java.util.Set;
import com.cations.oop.project.entity.atom.Year;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class Event extends Entity {
	private Set<Year> years = new HashSet<Year>();
	private transient Set<Character> characters = new HashSet<>(); // Id

	public Event(String name) { super(name); }

	public void addYear(Year year) { years.add(year); }
	public void addCharacter(Character character) {
		assert(character != null);
		characters.add(character);
		character.addEvent(this);
	}

	@Override
	public String toJson() {
		var characterIds = new JsonArray();
		for (var character: characters) characterIds.add(Entity.getId(character));

		JsonElement elem = gson.toJsonTree(this);
		elem.getAsJsonObject().add("characters", characterIds);
		return gson.toJson(elem);
	}
}
