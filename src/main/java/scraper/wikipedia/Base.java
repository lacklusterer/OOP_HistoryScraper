package scraper.wikipedia;

import scraper.Page;

public abstract class Base extends Page {
	protected final static String baseUrl = "https://vi.wikipedia.org";

	protected Base(String url) { super(url); }
}
