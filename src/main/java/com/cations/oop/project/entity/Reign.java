package com.cations.oop.project.entity;

import java.util.HashSet;
import java.util.Set;
import com.cations.oop.project.entity.atom.Year;

public class Reign extends Entity {
	private Set<Year> begin = new HashSet<>();
	private Set<Year> end = new HashSet<>();

	public Reign(String name) { super(name); }

	public void addBegin(Year year) { begin.add(year); }
	public void addEnd(Year year) { end.add(year); }
}
