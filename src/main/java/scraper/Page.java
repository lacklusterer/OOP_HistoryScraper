package scraper;

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
