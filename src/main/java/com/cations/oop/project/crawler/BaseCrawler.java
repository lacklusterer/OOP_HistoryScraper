package com.cations.oop.project.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public abstract class BaseCrawler {

    public void crawl(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            processDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void processDocument(Document document);

    protected String getText(Element element, String selector) {
        Element selectedElement = element.selectFirst(selector);
        return (selectedElement != null) ? selectedElement.text() : "";
    }

    protected Elements getElements(Document document, String selector) {
        return document.select(selector);
    }
}