package com.cations.oop.project.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class BaseCrawler {
    protected Document document;
    protected String url;

    public void crawl() {
        try {
            document = Jsoup.connect(url).get();
            processDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void processDocument(Document document) throws IOException;

    public void setUrl(String url) {
        this.url = url;
    }
}