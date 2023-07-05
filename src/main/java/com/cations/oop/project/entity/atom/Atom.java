package com.cations.oop.project.entity.atom;

/**
 * Separate a number of fields into atom to handle conflicting sources.
 */
public class Atom {
	private String source; // url to the page mentioning this data

	public Atom(String source) { this.source = source; }
}
