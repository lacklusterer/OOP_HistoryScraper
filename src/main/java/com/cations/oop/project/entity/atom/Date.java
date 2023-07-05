package com.cations.oop.project.entity.atom;

import java.time.Month;

public class Date extends Atom {
	private boolean moon;
	private int month;
	private int day;

	public Date(
		String source,
		boolean moon,
		int month,
		int day
	) {
		super(source);
		this.moon = moon;
		this.month = month;
		if (day < 1 || Month.of(month).maxLength() < day)
			throw new RuntimeException("Invalid day of month");
		this.day = day;
	}
}
