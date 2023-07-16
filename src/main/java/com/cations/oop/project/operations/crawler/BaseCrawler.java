package com.cations.oop.project.operations.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;

public abstract class BaseCrawler {
    public void crawl(String url) {
        while (true) {
            try {
                System.out.println("Connecting...");
                Document document = Jsoup.connect(url).get();
                System.out.println("Connected to \"" + url + "\"!\n");
                process(document);
                return; // breaks look if successfully connects
            } catch (SocketTimeoutException e) {
                System.err.println("Connection timed out while connecting to \"" + url + "\"! Retrying...");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    protected abstract void process(Document document) throws IOException;
}