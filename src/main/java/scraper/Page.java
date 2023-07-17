package scraper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.jsoup.nodes.Document;

import database.Database;

public abstract class Page {
	private String url;
	private Document document;

	public Page(String url) {
		this.url = url;
	}

	public void load() {
		// Try to download page
		try {
			document = Jsoup.connect(url).get();
		}
		catch (IOException exception) {}
	}

	public boolean isLoaded() { return document != null; }
	public String getUrl() { return url; }
	public Document getDocument() { return document; }

	public Database getResult() { return null; }
	public Collection<Page> getForks() { return List.of(); }

	@Override
	public boolean equals(Object other) {
		var otherPage = (Page)other;
		return url.equals(otherPage.url);
	}
}
