package com.cations.oop.project.entity;

import com.google.gson.Gson;

/**
 * Base entity to be parsed into by scrappers.
 * Contains a name, and equals() will be check through this field.
 */
public abstract class Entity {
	protected final static Gson gson = new Gson();
	private static Integer count = 0;
	private int id;
	protected String name;

	protected Entity(String name) {
		this.id = ++count;
		this.name = name;
	}

	final static Integer getId(Entity entity) {
		if (entity == null) return null;
		return entity.id;
	}

	public String toJson() { return gson.toJson(this); }
	public Entity fromJson(String data) {
		// TODO
		return null;
	}
}
