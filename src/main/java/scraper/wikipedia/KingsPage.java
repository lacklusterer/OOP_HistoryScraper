package scraper.wikipedia;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import database.Database;
import entity.Reign;
import entity.type.YearRange;
import scraper.Page;

public class KingsPage extends Page {
	public KingsPage() { super("https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam"); }

	/**
	 * This method only scraps <code>Reign</code>s.
	 */
	@Override
	public Database getResult() {
		var database = new Database();
		Elements reignHeaders = document.select("div.mw-parser-output > h3 > span.mw-headline");
		reignHeaders.addAll(document.select("div.mw-parser-output > table h3 > span.mw-headline"));

		Pattern p = Pattern.compile("(\\d+)[–-](\\d+)( TCN)?");

		for (Element header: reignHeaders) {
			// Get list of names (no text in parenthesis)
			List<String> names = new ArrayList<>();
			for (var part: header.text().split("và"))
				names.add(part.replaceAll(" \\([^\\)]*\\)", ""));

			// Get list of year ranges
			Matcher m = p.matcher(header.text());
			List<YearRange> ranges = new ArrayList<>();
			while (m.find()) {
				int begin = Integer.parseInt(m.group(1));
				int end = Integer.parseInt(m.group(2));
				if (m.group(3) != null) {
					begin = -begin;
					end = -end;
				}
				ranges.add(new YearRange(begin, end));
			}

			// Add to database
			database.getReigns().add(new Reign(
				header.text().replaceAll(" \\([^\\)]*\\)", ""),
				YearRange.merge(ranges),
				url
			));
		}
		return database;
	}
}
