package com.cations.oop.project.scraper.wikipedia;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class WikiScraper extends BaseScrapper {

    @Override
    protected void processDoc(Document document) {
        String name = Objects.requireNonNull(document.selectFirst("#firstHeading")).text();

        // Check if the file already exists
        String filePath = "out/wiki/wikiScrappedKing/" + name + ".json";
        File existingFile = new File(filePath);
        if (existingFile.exists()) {
            System.out.println("File already exists: " + filePath);
            return;
        }

        System.out.println("Generating...");
        // Get data from info box
        Element infoBox = document.selectFirst(".infobox");

        // Output found data into json
        if (infoBox != null) {
            Elements rows = infoBox.select("tr");

            // Create a Gson instance
            Gson gson = new Gson();

            // Create a JsonObject to hold the data
            JsonObject dataObject = new JsonObject();

            // Add name to the data JsonObject
            dataObject.addProperty("Tên ", name);

            // Iterate over the infobox's rows
            for (Element row : rows) {
                Element label = row.selectFirst("th");
                Element value = row.selectFirst("td");

                if (label != null && value != null) {
                    dataObject.addProperty(label.text(), value.text());
                }
            }

            // Write the JSON data to file
            try (FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(dataObject, writer);
                System.out.println("Data saved to file: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Info box not found on the page.");
        }
    }
}
