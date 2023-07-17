package entity;

public class Reign extends Entity {
	private Integer begin;
	private Integer end;

	public Reign(
		String name,
		Integer begin,
		Integer end,
		String source
	) {
		super(name, source);
		this.begin = begin;
		this.end = end;
	}

	public Integer getBegin() { return begin; }
	public Integer getEnd() { return end; }
}
