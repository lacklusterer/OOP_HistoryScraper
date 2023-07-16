package com.cations.oop.project.test.wiki;

import com.cations.oop.project.operations.scraper.wikipedia.WikiPersonScraper;

public class WikiScrapeTestSingular {
    public static void main(String[] args) {
        String url = "https://vi.wikipedia.org/wiki/Nh%C3%A0_L%C3%BD";

        WikiPersonScraper scraper = new WikiPersonScraper();
        scraper.scrape(url, "test");
    }
}
