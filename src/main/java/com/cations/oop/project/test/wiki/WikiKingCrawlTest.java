package com.cations.oop.project.test.wiki;

import com.cations.oop.project.operations.crawler.wikipedia.WikiKingCrawler;

public class WikiKingCrawlTest {
    public static void main(String[] args) {
        // Obtain king list from wiki
        WikiKingCrawler wikiKingCrawler = new WikiKingCrawler();
        wikiKingCrawler.crawl("https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam");
    }
}
