import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.Database;
import scraper.Manager;

public class App {
	public static void main(String[] argsArr) {
		List<String> args = Arrays.asList(argsArr);
		final var databasePath = "database.json";

		if (args.size() == 0 || args.get(0).equals("scrape")) {
			// Create database instance.
			var database = Database.read(databasePath);
			if (database == null) database = new Database();

			// Scrape pages & merge results into big database.
			database.merge(new Manager(new scraper.nguoikesu.HomePage()).getResult());
			database.merge(new Manager(new scraper.wikipedia.KingsPage()).getResult());
			database.merge(new Manager(new scraper.wikipedia.FestivalsPage()).getResult());

			// Write database to disk
			database.write(databasePath);
		}

		if (args.get(0).equals("search")) {
			// Generate query string
			String query = String.join(" ", args.subList(1, args.size()));

			// Create database instance.
			var database = Database.read(databasePath);
			if (database == null) {
				System.err.println("Database does not exist!");
				return;
			}

			// Generate cache for searching
			database.generateCache();

			// For pretty print
			Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.create();

			// Search items
			var results = database.search(query);
			System.out.println("Found " + results.size() + " item(s):");
			for (var entity: database.search(query)) {
				System.out.println();
				System.out.println(entity.getClass().getName());
				System.out.println(gson.toJson(entity));
			}
		}
	}
}
