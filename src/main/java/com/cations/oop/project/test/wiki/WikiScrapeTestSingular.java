package com.cations.oop.project.test.wiki;

import com.cations.oop.project.operations.scraper.wikipedia.WikiPersonScraper;

public class WikiScrapeTestSingular {
    public static void main(String[] args) {
        WikiPersonScraper scraper = new WikiPersonScraper();
        scraper.scrape("https://en.wikipedia.org/wiki/Albert_Einstein", "test");
    }
}
