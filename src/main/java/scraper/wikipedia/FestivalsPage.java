package scraper.wikipedia;

import database.Database;
import entity.Festival;
import entity.type.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FestivalsPage extends Base {
	public FestivalsPage() { super("/wiki/C%C3%A1c_ng%C3%A0y_l%E1%BB%85_%E1%BB%9F_Vi%E1%BB%87t_Nam"); }

	@Override
	public Database getResult() {
		var database = new Database();
		int currentTable = 0;
		Elements tables = document.select("table.wikitable > tbody");

		Pattern p = Pattern.compile("(\\d+) tháng (\\d+)");

		for (var table : tables) {
			currentTable++;
			Elements rows = table.select("tr");

			// Iterates through the rows, skip first one
			for (int i = 1; i < rows.size(); i++) {
				Elements columns = rows.get(i).select("td");

				Element column1 = columns.get(0);
				Element column2 = columns.get(1);

				// Swap the values of the first table's columns because it is messed up
				if (currentTable == 1) {
					Element temp = column1;
					column1 = column2;
					column2 = temp;
				}

				// Deal with special month names
				String date = column1.text().replace("Giêng", "1").replace("Chạp", "12");

				// Add each line in the second column as a festival name to a list
				List<String> festivalNames = new ArrayList<>();
				for (var line : column2.html().split("<br>| / ")) {
					festivalNames.add(Jsoup.parse(line).text().replaceAll("\\[\\d+\\]", ""));
				}

				// Create festival from scraped data and add to database
				Matcher m = p.matcher(date);
				if (m.find()) {
					int day = Integer.parseInt(m.group(1));
					int month = Integer.parseInt(m.group(2));
					var festivalDate = new Date(month, day, (currentTable % 2 == 0));

					for (var festivalName : festivalNames) {
						Festival festival = new Festival(festivalName, festivalDate, getUrl());
						database.getFestivals().add(festival);
					}
				}
			}
		}
		return database;
	}
}