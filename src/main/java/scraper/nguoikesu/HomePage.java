package scraper.nguoikesu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.select.Elements;

import scraper.Page;

public class HomePage extends Base {
	public HomePage() { super(baseUrl); }

	@Override
	public Collection<Page> getForks() {
		List<Page> forks = new ArrayList<>();

		// Get buttons from the top navigation bar.
		Elements navButtons = document.select("#mainnav ul.mod-menu > li.nav-item");
		assert(navButtons.size() == 9); // 9 buttons

		// Crawl on specific nav buttons.
		// TODO

		return forks;
	}
}
