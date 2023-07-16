package com.cations.oop.project.operations.crawler.nguoikesu;

import com.cations.oop.project.operations.crawler.BaseCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NksEraCrawler extends BaseCrawler {
    @Override
    protected void findLinks(Document document) throws IOException {
        Element moduleCt = document.selectFirst("div.module-ct");
        Elements links = moduleCt.select("a[href^=/dong-lich-su/]");

        for (Element link : links) {
            String href = link.attr("href");
            System.out.println(href);
        }
    }
}
