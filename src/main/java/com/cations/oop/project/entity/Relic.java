package com.cations.oop.project.entity;

import java.time.Year;

public class Relic extends Entity {
	private String location;
	private Year found;

	public Relic(String name, String location, Year found) {
		super(name);
		this.location = location;
		this.found = found;
	}
}
