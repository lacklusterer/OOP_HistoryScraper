package com.cations.oop.project.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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

    protected String getText(Element element, String selector) {
        Element selectedElement = element.selectFirst(selector);
        return (selectedElement != null) ? selectedElement.text() : "";
    }

    public void setUrl(String url) {
        this.url = url;
    }
}