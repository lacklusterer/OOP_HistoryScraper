package entity;

public class Reign extends Entity {
	private final Integer begin;
	private final Integer end;

	public Reign(
		final String name,
		final Integer begin,
		final Integer end,
		final String source
	) {
		super(name, source);
		this.begin = begin;
		this.end = end;
	}

	public Integer getBegin() { return begin; }
	public Integer getEnd() { return end; }
}
