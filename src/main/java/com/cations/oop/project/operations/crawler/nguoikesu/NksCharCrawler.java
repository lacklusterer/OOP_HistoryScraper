package com.cations.oop.project.operations.crawler.nguoikesu;

import com.cations.oop.project.operations.crawler.BaseCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NksCharCrawler extends BaseCrawler {
    @Override
    protected void findLinks(Document document) throws IOException {
        Elements blogItems = document.select("div.com-content-category-blog__item");

        for (Element blogItem : blogItems) {
            // Extract title
            Element titleElement = blogItem.selectFirst("h2 a");
            String title = titleElement.text();
            System.out.println("Title: " + title);

            // Extract link
            String postUrl = titleElement.attr("href");
            System.out.println("URL: " + postUrl);

            // Extract info
            String info = blogItem.selectFirst("p").text();
            System.out.println("p: " + info);

            System.out.println();
        }
    }
}
