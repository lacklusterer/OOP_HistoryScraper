package scraper.nguoikesu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import database.Database;
import entity.Character;
import entity.type.YearRange;

public class CharacterPage extends Base {
	public CharacterPage(String path) { super(path); }

	private final static Integer parseYear(String input) {
		if (input == null) return null;
		if (input.equals("?")) return null;
		return Integer.parseInt(input);
	}

	@Override
	public Database getResult() {
		var database = new Database();

		Pattern yearRegex = Pattern.compile("(\\d+ )?(tháng \\d+,? )?(năm )?(\\d+|\\?)");

		String name = document.selectFirst(".page-header > h2").text();

		// Parse birth and death year from short bio.
		String birth = null, death = null;
		Element shortBioElement = document.selectFirst(".com-content-article__body p");
		if (shortBioElement != null) {
			var shortBio = shortBioElement.ownText();
			Matcher m = yearRegex.matcher(shortBio);
			if (m.find()) birth = m.group(4);
			if (m.find()) death = m.group(4);
		}

		// Add parsed item to container and return it.
		database.getCharacters().add(new Character(
			name,
			null,
			new YearRange(parseYear(birth), parseYear(death)),
			null,
			getUrl()
		));

		return database;
	}
}
