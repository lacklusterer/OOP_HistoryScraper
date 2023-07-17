package scraper;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import database.Database;

/**
 * Handles the forking pages from a root (home) page and groups results.
 *
 * <p>A BFS algorithm is used to traverse the pages. Skip a page if it fails to
 * load.
 */
public class Manager {
	private final Database result = new Database();

	/**
	 * @param rootPage Usually the top page of a domain.
	 */
	public Manager(Page rootPage) {
		Set<Page> parsed = new HashSet<>();
		Queue<Page> pages = new ArrayDeque<>();
		pages.add(rootPage);

		while (pages.size() > 0) {
			var page = pages.remove();
			page.load();
			if (!parsed.contains(page)) {
				parsed.add(page);
			} else continue;
			if (!page.isLoaded()) continue;

			result.merge(page.getResult());
			pages.addAll(page.getForks());
		}
	}

	/**
	 * @return The merged results from each page found from the root page.
	 */
	public Database getResult() { return result; }
}
