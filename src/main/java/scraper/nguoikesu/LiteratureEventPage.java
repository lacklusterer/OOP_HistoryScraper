package scraper.nguoikesu;

import database.Database;
import entity.Event;

public class LiteratureEventPage extends Base {
	public LiteratureEventPage(String path) { super(path); }

	@Override
	public Database getResult() {
		var database = new Database();

		// Page header as event name
		String name = document.selectFirst(".page-header > h1").text();

		database.getEvents().add(new Event(name, null, getUrl()));

		return database;
	}
}
