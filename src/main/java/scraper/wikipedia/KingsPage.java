package scraper.wikipedia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import database.Database;
import entity.Character;
import entity.Reign;
import entity.type.YearRange;
import scraper.Page;

public class KingsPage extends Base {
	public KingsPage() { super("/wiki/Vua_Vi%E1%BB%87t_Nam"); }

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
				getUrl()
			));
		}
		return database;
	}

	private static String withoutRefs(Element element) {
		return element.text().replaceAll("\\[[^\\]]+\\]", "");
	}

	@Override
	public Collection<Page> getForks() {
		List<Page> forks = new ArrayList<>();

		Pattern p = Pattern.compile("(\\d+)( ?TCN)?");

		Elements tables = document.select("div.mw-parser-output > table");
		for (Element table: tables) {

			// Get texts from first row of each table.
			List<String> columns = table
				.selectFirst("tr")
				.select("th")
				.stream()
				.map(cell -> cell.ownText())
				.collect(Collectors.toList());

			// Check if the first cell from which is a specific word
			// (this means the table contains infomation about kings).
			if (!(columns.size() > 0 && columns.get(0).equals("Chân dung"))) continue;

			// Get rows starting from the 2nd one.
			Elements rows = table.select("tr");
			rows.remove(0);

			for (var row: rows) {
				Elements cells = row.select("td");

				// Prepare fields to be parsed into.
				String forkUrl = null;
				String name = null;
				Set<String> aliases = new HashSet<>();
				YearRange crowned = null;

				// Iterate through each cell of the row.
				for (int index = 0; index < columns.size(); ++index) {
					var value = withoutRefs(cells.get(index));
					if (value.equals("không có") || value.equals("không rõ")) continue;
					switch (columns.get(index)) {
					case "Vua":
					case "Chúa":
					case "Hoàng đế":
					case "Thủ lĩnh":
					case "Tước hiệu":
					case "Tiết độ sứ":
						forkUrl = cells.get(index).selectFirst("a").attr("href");
						name = value;
						break;
					case "Miếu hiệu":
					case "Thụy hiệu":
					case "Niên hiệu":
					case "Tên húy":
					case "Tôn hiệu":
					case "Tôn hiệu hoặc Thụy hiệu":
						for (var commaPart: value.split(", ")) {
							boolean endPrth = commaPart.charAt(commaPart.length() - 1) == ')';
							for (var prthPart: commaPart.split("\\) ")) {
								if (endPrth && prthPart.charAt(prthPart.length() - 1) != ')')
									prthPart += ')';
								aliases.add(prthPart);
							}
						}
						break;
					case "Trị vì":
						// Check if the reign title cell spans 3 columns.
						if (cells.size() - columns.size() != 2) break;

						// Check if texts match regex.
						var endValue = withoutRefs(cells.get(index + 2));
						Matcher mBegin = p.matcher(value);
						Matcher mEnd   = p.matcher(endValue);
						Integer begin = null;
						Integer end = null;

						// Process regex results.
						if (mBegin.find()) {
							begin = Integer.parseInt(mBegin.group(1));
							if (mBegin.group(2) != null) begin = -begin;
						}
						if (mEnd.find() && !endValue.contains("năm")) {
							end = Integer.parseInt(mEnd.group(1));
							if (mEnd.group(2) != null) {
								end = -end;
								if (begin != null && begin > end) begin = -begin;
							}
						}

						crowned = new YearRange(begin, end);
						break;
					case "Chân dung":
					case "Thế thứ":
					default:
						break;
					}
				}

				// We are certain that each of the field was parsed from the table.
				assert(forkUrl != null);
				assert(name != null);
				var preParsed = new Character(name, null, null, crowned, getUrl());
				preParsed.getAliases().addAll(aliases);

				// Create the fork page
				forks.add(new KingPage(forkUrl, preParsed));
			}
		}

		return forks;
	}
}
