package scraper.nguoikesu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import scraper.Page;

public class HomePage extends Base {
	public HomePage() { super(); }

	@Override
	public Collection<Page> getForks() {
		List<Page> forks = new ArrayList<>();

		// Get buttons from the top navigation bar.
		Elements navButtons = document.select("#mainnav ul.mod-menu > li.nav-item");
		assert(navButtons.size() == 9); // 9 buttons

		// Crawl on specific nav buttons.
		forks.add(new ListingPage(
			navButtons.get(2).select("a").get(1).attr("href"),
			MilitaryEventPage.class, "div[itemprop=\"blogPost\"]"));
		forks.add(new ListingPage(
			navButtons.get(2).select("a").get(2).attr("href"),
			LiteratureEventPage.class, "div[itemprop=\"blogPost\"]"));
		forks.add(new ListingPage(
			navButtons.get(3).selectFirst("a").attr("href"),
			CharacterPage.class, "div[itemprop=\"blogPost\"]"));
		forks.add(new ListingPage(
			navButtons.get(4).selectFirst("a").attr("href"),
			RelicPage.class, "li.list-group-item"));

		// Crawl on eras on the side bar.
		// The subpages on the listing page for eras contains inconsistent types
		// (both Character and Event).
		for (Element reignButton: document.select("#Mod88 ul.mod-articlescategories li"))
			forks.add(new ReignPage(reignButton.selectFirst("a").attr("href")));

		return forks;
	}
}
