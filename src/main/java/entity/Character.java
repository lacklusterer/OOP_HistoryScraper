package entity;

import java.util.HashSet;
import java.util.Set;

public class Character extends Entity {
	private Set<String> aliases = new HashSet<>();
	private String father;
	private Set<String> children = new HashSet<>();
	private Integer crowned;
	private Integer birth;
	private Integer death;

	public Character(
		String name,
		String father,
		Integer crowned,
		Integer birth,
		Integer death,
		String source
	) {
		super(name, source);
		this.father = father;
		this.crowned = crowned;
		this.death = death;
	}

	public void addChild(String child) { children.add(child); }
	public void addAlias(String alias) { aliases.add(alias); }

	public Set<String> getAliases() { return aliases; }
	public String getFather() { return father; }
	public Set<String> getChildren() { return children; }
	public Integer getCrowned() { return crowned; }
	public Integer getBirth() { return birth; }
	public Integer getDeath() { return death; }
}
