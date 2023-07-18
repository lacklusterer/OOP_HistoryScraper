package scraper.wikipedia;

import scraper.Page;

/**
 * Abstract package-visible base to provide a base url.
 */
abstract class Base extends Page {
	protected final static String baseUrl = "https://vi.wikipedia.org";

	protected Base(String url) { super(url); }
}
