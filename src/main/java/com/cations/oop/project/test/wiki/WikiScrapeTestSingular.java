package com.cations.oop.project.test.wiki;

import com.cations.oop.project.operations.scraper.wikipedia.WikiPersonScraper;

public class WikiScrapeTestSingular {
    public static void main(String[] args) {
        WikiPersonScraper scraper = new WikiPersonScraper();
        scraper.scrape("https://vi.wikipedia.org/wiki/Th%E1%BB%9Di_k%E1%BB%B3_B%E1%BA%AFc_thu%E1%BB%99c_l%E1%BA%A7n_th%E1%BB%A9_hai", "era");
    }
}
