package scraper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import database.Database;

/**
 * The webpage scraper/crawler.
 */
public abstract class Page {
	protected final String url;
	protected Document document;

	/**
	 * @param url The URL of the webpage to downloaded.
	 */
	public Page(String url) {
		this.url = url;
	}

	/**
	 * Attempts to Load the target URL into the <code>document</code> variable in
	 * the order:
	 * <ol>
	 * 	<li>Parse the html cache (with Jsoup) if it exists.</li>
	 * 	<li>Download the page from the internet (with Jsoup).</li>
	 * 	<li>Write the cache to a file on the disk.</li>
	 */
	public void load() {
		// Get cache file path
		String baseUrl = null;
		String cachePath = null;
		try {
			URL parsedUrl = new URL(url);
			baseUrl = parsedUrl.getProtocol() + "://" + parsedUrl.getHost();
			cachePath = Paths
				.get(
					"/run/user/1000/oop_project",
					parsedUrl.getHost(),
					parsedUrl.getPath())
				.toString() + ".html";
		}
		catch (MalformedURLException exception) {}

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
		return url.equals(otherPage.url);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(url);
	}
}
