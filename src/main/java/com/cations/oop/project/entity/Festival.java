package com.cations.oop.project.entity;

import java.util.HashSet;
import java.util.Set;
import com.cations.oop.project.entity.atom.Date;
import com.cations.oop.project.entity.atom.Location;
import com.google.gson.JsonElement;

public class Festival extends Entity {
	private Set<Location> locations = new HashSet<>();
	private Set<Date> dates = new HashSet<>();
	private transient Character character;
	private transient Event event;
	private transient Relic relic;

	public Festival(
		String name,
		Character character,
		Event event,
		Relic relic
	) {
		super(name);
		this.character = character;
		this.event = event;
		this.relic = relic;
	}

	public void addLocation(Location location) { locations.add(location); }
	public void addDate(Date date) { dates.add(date); }

	@Override
	public String toJson() {
		JsonElement elem = gson.toJsonTree(this);
		elem.getAsJsonObject().addProperty("character", Entity.getId(character));
		elem.getAsJsonObject().addProperty("event", Entity.getId(event));
		elem.getAsJsonObject().addProperty("relic", Entity.getId(relic));
		return gson.toJson(elem);
	}
}
