package com.cations.oop.project.test.wiki;

import com.cations.oop.project.scraper.wikipedia.WikiScraper;

public class WikiScrapeTest2 {
    public static void main(String[] args) {
        WikiScraper scraper = new WikiScraper();
        scraper.scrape("https://vi.wikipedia.org/wiki/Kiều_Công_Tiễn");
    }
}
