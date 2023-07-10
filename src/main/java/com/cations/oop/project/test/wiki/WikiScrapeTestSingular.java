package com.cations.oop.project.test.wiki;

import com.cations.oop.project.operations.scraper.wikipedia.WikiPersonScraper;

public class WikiScrapeTestSingular {
    public static void main(String[] args) {
        WikiPersonScraper scraper = new WikiPersonScraper();
        scraper.scrape("https://vi.wikipedia.org/wiki/L%C3%BD_Th%C3%A1i_T%E1%BB%95", "test");
    }
}
