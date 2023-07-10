package com.cations.oop.project.operations.scraper;

import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

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

    protected void processDoc(Document document, String savePath) {
        String name = Objects.requireNonNull(document.selectFirst("#firstHeading")).text();

        // Check if the file already exists
        String filePath = "out/wiki/" + savePath + "/" + name + ".json";
        File existingFile = new File(filePath);
        if (existingFile.exists()) {
            System.out.println("File already exists: " + filePath);
            return;
        }

        System.out.println("Generating json ...");

        // Get infobox
        Element infoBox = document.selectFirst(".infobox");
        if (infoBox != null) {

            Gson gson = new Gson();

            // Get info from infobox
            JsonObject jsonObject = getInfo(infoBox, name);

            // Write the JSON data to file
            try (FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(jsonObject, writer);
                System.out.println("Data saved to file: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Info box not found on the page.");
        }
    }

    protected abstract JsonObject getInfo(Element infoBox, String name);
}
