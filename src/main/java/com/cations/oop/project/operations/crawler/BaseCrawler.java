package com.cations.oop.project.operations.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class BaseCrawler {
    public void crawl(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            System.out.println("Connected to \"" + url + "\"!");
            process(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void process(Document document) throws IOException;
}