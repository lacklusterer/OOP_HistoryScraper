package scraper.nguoikesu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import database.Database;
import entity.Event;

public class MilitaryEventPage extends Base {
	public MilitaryEventPage(String path) { super(path); }

	@Override
	public Database getResult() {
		var database = new Database();

		// Page header as event name
		String name = document.selectFirst(".page-header > h1").text();

		// Parse year
		Integer year = null;
		Pattern p = Pattern.compile("(năm )?(\\d+)");
		Matcher m = p.matcher(name);
		if (m.find()) year = Integer.parseInt(m.group(2));

		// Create & add event to database
		var event = new Event(name, year, getUrl());
		database.getEvents().add(event);

		// Find the leaders row and add them as characters
		boolean found = false;
		for (Element row: document.select(".infobox > table table[cellspacing=\"2\"][cellpadding=\"2\"] > tbody > tr")) {
			if (found) {
				for (Element character: row.select("a")) event.getCharacters().add(character.text());
				break;
			}
			if (row.text().equals("Chỉ huy")) found = true;
		}

		return database;
	}
}
