package com.cations.oop.project.operations.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseCrawler {

    Set<String> existingItems;
    String filePath;

    public BaseCrawler() {
        // create a HashSet to keep track of data
        this.existingItems  = new HashSet<>();
    }

    public void crawl(String url, String saveFile) {
        while (true) {
            try {
                System.out.println("Connecting...");
                Document document = Jsoup.connect(url).get();
                System.out.println("Connected to \"" + url + "\"!\n");

                process(document, saveFile);

                return; // breaks look if successfully connects
            } catch (SocketTimeoutException e) {
                System.err.println("Connection timed out while connecting to \"" + url + "\"! Retrying...");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    protected abstract void process(Document document, String saveFile) throws IOException;

    public int getCollectedCount() {
        return existingItems.size();
    }
}