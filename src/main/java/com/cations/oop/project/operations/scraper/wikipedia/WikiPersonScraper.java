package com.cations.oop.project.operations.scraper.wikipedia;

import com.cations.oop.project.operations.scraper.BaseScrapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class WikiPersonScraper extends BaseScrapper {

    @Override
    protected JsonObject getInfo(Element infoBox, String name) {
            Elements rows = infoBox.select("tr");

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
            return dataObject;
    }
}
