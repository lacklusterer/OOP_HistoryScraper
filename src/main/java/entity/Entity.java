package entity;

import java.util.Objects;

/**
 * Base entity to be parsed into by scrappers.
 * Contains a name, and equals() will be check through this field.
 */
public abstract class Entity {
	private final String name;
	private final String source;

	protected Entity(final String name, final String source) {
		this.name = name;
		this.source = source;
	}

	public String getName() { return name; }
	public String getSource() { return source; }

	@Override
	public boolean equals(Object other) {
		var otherEntity = (Entity)other;
		return name.equals(otherEntity.name) &&
			source.equals(otherEntity.source);
	}

	@Override
	public int hashCode() {
		// Naive approach as name and source have different structures.
		return Objects.hashCode(name) + Objects.hashCode(source);
	}
}
