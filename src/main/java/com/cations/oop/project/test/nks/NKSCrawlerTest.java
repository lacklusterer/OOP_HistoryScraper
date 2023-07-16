package com.cations.oop.project.test.nks;

import com.cations.oop.project.operations.crawler.nguoikesu.NKSCharCrawler;

public class NKSCrawlerTest {
    public static void main(String[] args) {
        NKSCharCrawler crawler = new NKSCharCrawler();
        int collected = 0;
        while (collected <= 5) {
            crawler.crawl("https://nguoikesu.com/nhan-vat?start=" + collected);
            collected += 5;
        }
    }
}
