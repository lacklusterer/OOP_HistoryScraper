package com.cations.oop.project.operations.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class BaseScrapper {
    public void scrape(String url, String saveFolder) {
        try {
            System.out.println("Scraping: " + url);
            Document document = Jsoup.connect(url).get();
            processDoc(document, saveFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void processDoc(Document document, String savePath);
}
