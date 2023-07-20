import java.util.Arrays;
import java.util.List;

import database.Database;
import scraper.Manager;

public class App {
	public static void main(String[] argsArr) {
		List<String> args = Arrays.asList(argsArr);

		if (args.size() == 0 || args.get(0).equals("scrape")) {
			final var databasePath = "database.json";

			// Create database instance.
			var database = Database.read(databasePath);
			if (database == null) database = new Database();

			// Scrape pages & merge results into big database.
			database.merge(new Manager(new scraper.nguoikesu.HomePage()).getResult());
			database.merge(new Manager(new scraper.wikipedia.KingsPage()).getResult());

			// Write database to disk
			database.write(databasePath);
		}
	}
}
