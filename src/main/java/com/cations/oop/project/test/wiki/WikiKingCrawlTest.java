package com.cations.oop.project.test.wiki;

import com.cations.oop.project.crawler.WikiKingCrawler;

public class WikiKingCrawlTest {
    public static void main(String[] args) {
        // Obtain king list from wiki
        WikiKingCrawler wikiKingCrawler = new WikiKingCrawler();
        wikiKingCrawler.setUrl("https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam");

        wikiKingCrawler.crawl();
    }
}
