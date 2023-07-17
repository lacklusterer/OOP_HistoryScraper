package scraper;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import database.Database;

public class Manager {
	private final Database result = new Database();

	public Manager(Page rootPage) {
		Set<Page> parsed = new HashSet<>();
		Queue<Page> pages = new ArrayDeque<>();
		pages.add(rootPage);

		while (pages.size() > 0) {
			var page = pages.remove();
			if (!parsed.contains(page)) {
				parsed.add(page);
			} else continue;
			if (!page.isLoaded()) continue;

			result.merge(page.getResult());
			pages.addAll(page.getForks());
		}
	}

	public Database getResult() { return result; }
}
