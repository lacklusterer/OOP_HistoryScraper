package com.cations.oop.project.test.nks;

import com.cations.oop.project.operations.crawler.nguoikesu.NksEraCrawler;

public class EraCrawlerTest {
    public static void main(String[] args) {
        NksEraCrawler crawler = new NksEraCrawler();
        crawler.crawl("https://nguoikesu.com/dong-lich-su");
    }
}
