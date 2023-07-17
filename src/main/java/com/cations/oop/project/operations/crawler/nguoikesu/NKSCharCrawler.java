package com.cations.oop.project.operations.crawler.nguoikesu;

import com.cations.oop.project.operations.crawler.BaseCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

public class NKSCharCrawler extends BaseCrawler {

    public NKSCharCrawler(String filePath) {
        super(filePath);
    }

    @Override
    protected String process(Document document, HashSet<String> existingItems) {

        StringBuilder stringBuilder = new StringBuilder();

        // get blog__items
        Elements blogItems = document.select("div.com-content-category-blog__item");

        // iterate over blogItems and extract data for each blogItem
        for (Element blogItem : blogItems) {
            Element titleElement = blogItem.selectFirst("h2 a");

            // extract title
            String title = titleElement.text();
            System.out.println("Title: " + title);

            // check if the item already exists
            if (existingItems.contains("\"" + title + "\"")) {
                System.out.println(title + " already exists!\n");
                continue;
            } else {
                existingItems.add("\"" + title + "\"");
            }

            // extract info
            String info = "No info";
            try {
                info = blogItem.selectFirst("p").text();
                System.out.println("Info: " + info);
            } catch (NullPointerException e) {
                System.out.println("Info is empty!");
            }

            // deal with double quote in info
            info = info.replace("\"", "\"\"");

            // extract url
            String itemUrl = "https://nguoikesu.com" + titleElement.attr("href");
            System.out.println("URL: " + itemUrl);

            // write data to the CSV file
            String result = "\"" + title + "\",\"" + info + "\",\"" + itemUrl + "\"";

            stringBuilder.append(result).append("\n");

            System.out.println("Data for " + title + " saved successfully.\n");
        }

        System.out.println("Collected data: " + getCollectedCount() + "\n");

        return stringBuilder.toString();
    }
}
