package com.cations.oop.project.test.nks;

import com.cations.oop.project.operations.crawler.nguoikesu.NKSCharCrawler;
import com.cations.oop.project.operations.crawler.nguoikesu.NKSSiteCrawler;

public class NKSCrawlerTest {
    public static void main(String[] args) {
//        NKSCharCrawler crawler = new NKSCharCrawler("out/nks/nhanvat.csv");
//        int collected = 0;
//        while (collected <= 10) {
//            crawler.crawl("https://nguoikesu.com/nhan-vat?start=" + collected);
//            collected += 10;

        NKSSiteCrawler crawler = new NKSSiteCrawler("out/nks/ditich2.csv");
        int collected = 0;
        while (collected <= 10) {
            crawler.crawl("https://nguoikesu.com/di-tich-lich-su?start=" + collected);
            collected += 10;
        }
    }
}
