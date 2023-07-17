package entity.type;

public class Date {
	private int month;
	private int day;
	private boolean moon = true;

	public Date(
		int month,
		int day
	) {
		this.month = month;
		this.day = day;
	}

	public Date(
		int month,
		int day,
		boolean moon
	) {
		this(month, day);
		this.moon = moon;
	}

	public int getMonth() { return month; }
	public int getDay() { return day; }
	public boolean isMoon() { return moon; }
}
