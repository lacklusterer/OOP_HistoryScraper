package com.cations.oop.project.operations.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.SocketTimeoutException;
import java.util.HashSet;

public abstract class BaseCrawler {
    HashSet<String> existingItems;
    String filePath;

    public BaseCrawler(String filePath) {
        this.existingItems  = new HashSet<>();
        this.filePath = filePath;
    }

    public void crawl(String url) {
        while (true) {
            try {
                System.out.println("Connecting...");
                Document document = Jsoup.connect(url).get();
                System.out.println("Connected to \"" + url + "\"!\n");

                operate(document, filePath, existingItems);

                return; // breaks look if successfully connects
            } catch (SocketTimeoutException e) {
                System.err.println("Connection timed out while connecting to \"" + url + "\"! Retrying...");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    protected abstract String process(Document document, HashSet<String> existingItems) throws IOException;

    public int getCollectedCount() {
        return existingItems.size();
    }

    protected void operate(Document document, String filePath, HashSet<String> existingItems) throws IOException {
        // keep track of existing data
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            // skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // add title to existing items
                existingItems.add(parts[0]);
            }
            reader.close();
        }

        // FileWriter and BufferedWriter
        FileWriter fileWriter = new FileWriter(filePath, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // write CSV header if file empty
        if (file.length() == 0) {
            bufferedWriter.write("Name,Info,URL");
            bufferedWriter.newLine();
        }

        String result = process(document, existingItems);

        bufferedWriter.write(result);

        bufferedWriter.close();
        fileWriter.close();
    }
}