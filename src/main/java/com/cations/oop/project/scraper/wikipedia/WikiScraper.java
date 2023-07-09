package com.cations.oop.project.scraper.wikipedia;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class WikiScraper extends BaseScrapper{

    @Override
    protected void processDoc(Document document) {
        String name = Objects.requireNonNull(document.selectFirst("#firstHeading")).text();
        System.out.println(name);

        // Get data from info box
        Element infoBox = document.selectFirst(".infobox");

        // Output found data - currently into console
        if (infoBox != null) {
            Elements rows = infoBox.select("tr");

            // Create a Gson instance
            Gson gson = new Gson();

            // Create a JsonArray to hold the rows
            JsonArray jsonArray = new JsonArray();

            // Iterate over the rows
            for (Element row : rows) {
                Element label = row.selectFirst("th");
                Element value = row.selectFirst("td");

                if (label != null && value != null) {
                    String labelText = label.text();
                    String valueText = value.text();

                    // Create a JsonObject for each row
                    JsonObject rowObject = new JsonObject();
                    rowObject.addProperty("label", labelText);
                    rowObject.addProperty("value", valueText);

                    // Add the row JsonObject to the JsonArray
                    jsonArray.add(rowObject);
                }
            }

            // Write the JSON data to a file
            try (FileWriter writer = new FileWriter("out/wiki/wikiScrappedKing/output.json")) {
                gson.toJson(jsonArray, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Info box not found on the page.");
        }
    }
}
