package entity;

import entity.type.YearRange;

public class Reign extends Entity {
	private final YearRange exist;

	public Reign(
		final String name,
		final YearRange exist,
		final String source
	) {
		super(name, source);
		this.exist = exist;
	}

	public YearRange getExist() { return exist; }
}
