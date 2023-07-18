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
		var url = baseUrl + navButtons.get(4).selectFirst("a").attr("href");
		forks.add(new ListingPage(url, RelicPage.class, "li.list-group-item"));

		return forks;
	}
}
