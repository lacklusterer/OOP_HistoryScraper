package com.cations.oop.project.scraper.wikipedia;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class BaseScrapper {
    public void scrape(String url) {
        try {
            System.out.println("Scraping: " + url);
            Document document = Jsoup.connect(url).get();
            processDoc(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void processDoc(Document document);
}
