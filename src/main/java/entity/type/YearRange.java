package entity.type;

import java.util.Objects;

public class YearRange {
	private final Integer begin;
	private final Integer end;

	public YearRange(Integer begin, Integer end) {
		this.begin = begin;
		this.end = end;
	}

	public Integer getBegin() { return begin; }
	public Integer getEnd() { return end; }

	@Override
	public int hashCode() {
		// We expect the begin to always be fewer than end
		return Objects.hashCode(begin) + Objects.hashCode(end);
	}
}
