package scraper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import database.Database;

/**
 * The webpage scraper/crawler. <br>
 * We assume that during the crawling process, the links only contain the path
 * to another endpoint of the domain (for example: <code>"/nhan-vat/tran-van-a"
 * </code>).
 */
public abstract class Page {
	private static String cacheDir = ".cache";
	private final String baseUrl;
	/**
	 * The path can contain query string (such as <code>"/some/path?param=value"
	 * </code>).
	 */
	private final String path;
	protected Document document;

	public static void setCacheDir(String cacheDir) { Page.cacheDir = cacheDir; }

	/**
	 * @param baseUrl The domain of the website.
	 * @param path The path to a page of the domain.
	 */
	public Page(String baseUrl, String path) {
		this.baseUrl = baseUrl;
		this.path = path;
	}

	/**
	 * This is used for the home (root) page of the domain.
	 * @param baseUrl The domain of the website.
	 */
	public Page(String baseUrl) { this(baseUrl, ""); }

	protected String getUrl() { return baseUrl + path; }

	/**
	 * Attempts to Load the target URL into the <code>document</code> variable in
	 * the order:
	 * <ol>
	 * 	<li>Parse the html cache (with Jsoup) if it exists.</li>
	 * 	<li>Download the page from the internet (with Jsoup).</li>
	 * 	<li>Write the cache to a file on the disk.</li>
	 */
	public void load() {
		var url = getUrl();

		// Get cache file path
		String cachePath = Paths
			.get(
				Page.cacheDir,
				baseUrl.split("/")[2],
				path)
			.toString() + ".html";

		// Attemp to read from cache if it exists
		File cache = new File(cachePath);
		if (cache.exists() && !cache.isDirectory()) {
			try {
				document = Jsoup.parse(cache, "UTF-8", baseUrl);
				return;
			}
			catch (IOException exception) {}
		}

		// Try to download page
		try {
			document = Jsoup.connect(url).get();
		}
		catch (IOException exception) {}

		// Write to cache
		if (document == null) return;
		cache.getParentFile().mkdirs();
		try {
			PrintWriter out = new PrintWriter(cachePath, "UTF-8");
			out.write(document.outerHtml());
			out.close();
		}
		catch (Exception e) {}
	}

	public boolean isLoaded() { return document != null; }

	/**
	 * The scraper should read some text elements from the <code>document</code>
	 * variable and parse the raw text into entity the database can understand.
	 *
	 * @return The collection for all parsed entity of this page.
	 */
	public Database getResult() { return null; }
	/**
	 * Like the <code>getResults()</code> method, except this one looks for link
	 * elements (typically the <code>a</code> tag and read the <code>href</code>
	 * attribute).
	 *
	 * <p>Each fork should be a <code>page</code> object of a derived class. <br>
	 * For example, <code>HomePage::getForks()</code> should contain
	 * <code>new CharacterPage(someUrl)</code>.
	 * </p>
	 *
	 * @return A list (collection) of other pages refered from this page.
	 */
	public Collection<Page> getForks() { return List.of(); }

	@Override
	public boolean equals(Object other) {
		var otherPage = (Page)other;
		return baseUrl.equals(otherPage.baseUrl) && path.equals(otherPage.path);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getUrl());
	}
}
