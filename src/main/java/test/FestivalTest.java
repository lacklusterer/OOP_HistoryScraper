package test;

import entity.Festival;
import entity.type.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FestivalTest {
	public static void main(String[] args) throws IOException {
		String url = "https://vi.wikipedia.org/wiki/C%C3%A1c_ng%C3%A0y_l%E1%BB%85_%E1%BB%9F_Vi%E1%BB%87t_Nam";
		Document document = Jsoup.connect(url).get();

		boolean firstTableProcessed = false; // Flag to check if the first table is processed
		Pattern p = Pattern.compile("(\\d+) tháng (\\d+)");

		Elements tables = document.select("table.wikitable");

		for (var table : tables) {
			Elements rows = table.select("tbody > tr");

			// Iterates through the rows, skip first one
			for (int i = 1; i < rows.size(); i++) {
				Elements columns = rows.get(i).select("td");
				Element column1 = columns.get(0);
				Element column2 = columns.get(1);

				// Swap the values of the first table's columns because  it is messed up
				if (!firstTableProcessed) {
					Element temp = column1;
					column1 = column2;
					column2 = temp;
				}

				String date = column1.text();

				List<String> festivalNames = new ArrayList<>();
				for (var line : column2.html().split("<br>| / ")) {
					festivalNames.add(Jsoup.parse(line).text().replaceAll("\\[\\d+\\]", ""));
				}

				// Deal with special months name
				date = date.replace("Giêng", "1").replace("Chạp", "12");

				process(date, p, festivalNames);
			}

			if (!firstTableProcessed) {
				firstTableProcessed = true; // Set the flag to true after processing the first table
			}
		}
	}

	public static void process(String date, Pattern p, List<String> festivalNames) {
		System.out.println(date + "\n" + festivalNames);

		Matcher m = p.matcher(date);
		if (m.find()) {
			int day = Integer.parseInt(m.group(1));
			int month = Integer.parseInt(m.group(2));
			var festivalDate = new Date(month, day);

			for (var festivalName : festivalNames) {
				Festival festival = new Festival(festivalName, festivalDate, "My ass");
				System.out.println("Name: " + festival.getName());
				System.out.println("Date: " + festival.getDate().getDay() + "-" + festival.getDate().getMonth());
			}
		}
	}
}
