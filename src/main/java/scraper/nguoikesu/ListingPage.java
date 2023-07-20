package scraper.nguoikesu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.nodes.Element;

import scraper.Page;

public class ListingPage extends Base {
	private Class<? extends Page> subPageClass;
	private String subPageSelector;

	public ListingPage(
		String path,
		Class<? extends Page> subPageClass,
		String subPageSelector
	) {
		super(path);
		this.subPageClass = subPageClass;
		this.subPageSelector = subPageSelector;
	}

	@Override
	public Collection<Page> getForks() {
		List<Page> forks = new ArrayList<>();

		// Next page of this listing webpage.
		Element nextButton = document.selectFirst("a.page-link[aria-label=\"Đi tới tiếp tục trang\"]");
		if (nextButton != null) {
			forks.add(new ListingPage(
				nextButton.attr("href"),
				subPageClass,
				subPageSelector
			));
		}

		// Subpage for each item
		for (Element element: document.select(subPageSelector)) {
			Element link = element.selectFirst("a");
			try {
				forks.add(subPageClass
					.getDeclaredConstructor(String.class)
					.newInstance(link.attr("href"))
				);
			}
			catch (Exception exc) {
				System.out.println("warn: generic class exception");
			}
		}

		return forks;
	}
}
