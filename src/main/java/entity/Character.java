package entity;

import java.util.HashSet;
import java.util.Set;

import entity.type.YearRange;

public class Character extends Entity {
	private final Set<String> aliases = new HashSet<>();
	private final String father;
	private final Set<String> children = new HashSet<>();
	private final YearRange life;
	private final YearRange crowned;

	public Character(
		final String name,
		final String father,
		final YearRange life,
		final YearRange crowned,
		final String source
	) {
		super(name, source);
		this.father = father;
		this.crowned = crowned;
		this.life = life;
	}

	public Set<String> getAliases() { return aliases; }
	public String getFather() { return father; }
	public Set<String> getChildren() { return children; }
	public YearRange getLife() { return life; }
	public YearRange getCrowned() { return crowned; }
}
