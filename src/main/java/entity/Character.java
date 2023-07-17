package entity;

import java.util.HashSet;
import java.util.Set;

public class Character extends Entity {
	private final Set<String> aliases = new HashSet<>();
	private final String father;
	private final Set<String> children = new HashSet<>();
	private final Integer crowned;
	private final Integer birth;
	private final Integer death;

	public Character(
		final String name,
		final String father,
		final Integer crowned,
		final Integer birth,
		final Integer death,
		final String source
	) {
		super(name, source);
		this.father = father;
		this.crowned = crowned;
		this.birth = birth;
		this.death = death;
	}

	public Set<String> getAliases() { return aliases; }
	public String getFather() { return father; }
	public Set<String> getChildren() { return children; }
	public Integer getCrowned() { return crowned; }
	public Integer getBirth() { return birth; }
	public Integer getDeath() { return death; }
}
