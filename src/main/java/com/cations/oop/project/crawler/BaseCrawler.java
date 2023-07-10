package com.cations.oop.project.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class BaseCrawler {
    public void crawl(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            findLinks(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void findLinks(Document document) throws IOException;
}