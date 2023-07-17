package com.cations.oop.project.operations.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
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

//                process(document, saveFile);
                operate(document, saveFile);

                return; // breaks look if successfully connects
            } catch (SocketTimeoutException e) {
                System.err.println("Connection timed out while connecting to \"" + url + "\"! Retrying...");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    protected abstract String process(Document document, String saveFile, File file) throws IOException;

    public int getCollectedCount() {
        return existingItems.size();
    }

    protected void operate(Document document, String saveFile) throws IOException {
        // keep track of existing data
        String filePath = "out/nks/" + saveFile;
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

        String result = process(document, saveFile, file);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();
        fileWriter.close();
    }
}