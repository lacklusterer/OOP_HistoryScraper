package entity.type;

import java.util.Collection;
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

	public static YearRange merge(Collection<YearRange> ranges) {
		Integer minBegin = null;
		Integer maxEnd = null;
		for (var r: ranges) {
			if (r.begin == null || r.end == null) throw new NullPointerException("Range element can not be null");
			if (minBegin == null) {
				minBegin = r.begin;
				maxEnd = r.end;
				continue;
			}
			assert(maxEnd != null);
			minBegin = Math.min(minBegin, r.begin);
			maxEnd   = Math.max(maxEnd,   r.end);
		}
		return new YearRange(minBegin, maxEnd);
	}

	@Override
	public int hashCode() {
		// We expect the begin to always be fewer than end
		return Objects.hashCode(begin) + Objects.hashCode(end);
	}
}
