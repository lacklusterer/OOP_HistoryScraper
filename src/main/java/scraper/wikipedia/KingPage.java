package scraper.wikipedia;

import database.Database;
import entity.Character;
import scraper.Page;

public class KingPage extends Page {
	/**
	 * Contains infomation parsed from the parent page.
	 */
	private Character preParsed;

	public KingPage(String url, Character preParsed) {
		super(url);
		this.preParsed = preParsed;
	}

	@Override
	public Database getResult() {
		var database = new Database();
		database.getCharacters().add(preParsed);

		// TODO: add more info

		return database;
	}
}
