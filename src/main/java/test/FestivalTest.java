package test;

import entity.type.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FestivalTest {
	public static void main(String[] args) throws IOException {
		String url = "https://vi.wikipedia.org/wiki/C%C3%A1c_ng%C3%A0y_l%E1%BB%85_%E1%BB%9F_Vi%E1%BB%87t_Nam";
		Document document = Jsoup.connect(url).get();

		boolean firstTableProcessed = false; // Flag to check if the first table is processed
		Pattern p = Pattern.compile("(\\d+) tháng (\\d+)");

		Elements tables = document.select("table.wikitable > tbody");

		for (var table : tables) {
			Elements rows = table.select("tr");

			// Start from index 1 to skip the first row
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

				String festivalName = column2.text();

				// Deal with special months name
				date = date.replace("Giêng", "1").replace("Chạp", "12");

				process(date, festivalName, p);
			}

			if (!firstTableProcessed) {
				firstTableProcessed = true; // Set the flag to true after processing the first table
			}
		}
	}

	public static void process(String date, String festivalName, Pattern p) {
		String combined = (date + "|" + festivalName);
		System.out.println("Combined: " + combined);
		Matcher m = p.matcher(date);

		while (m.find()) {
			int day = Integer.parseInt(m.group(1));
			int month = Integer.parseInt(m.group(2));
			var dDate = new Date(month, day);
			System.out.println(dDate.getDay() + "|" + dDate.getMonth());
		}
	}
}
