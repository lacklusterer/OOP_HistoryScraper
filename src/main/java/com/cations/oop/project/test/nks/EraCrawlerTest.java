package com.cations.oop.project.test.nks;

import com.cations.oop.project.operations.crawler.nguoikesu.NksCharCrawler;

public class EraCrawlerTest {
    public static void main(String[] args) {
        NksCharCrawler crawler = new NksCharCrawler();
        crawler.crawl("https://nguoikesu.com/nhan-vat?start=1450");
    }
}
