package com.cations.oop.project.operations.scraper.wikipedia;

import com.cations.oop.project.operations.scraper.BaseScrapper;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiPersonScraper extends BaseScrapper {

    protected JsonObject getInfo(Element infoBox) {
        Elements rows = infoBox.select("tr");

        // Create a JsonObject to hold the data
        JsonObject dataObject = new JsonObject();

        // Get the name from the infobox header
        Element header = infoBox.selectFirst(".infobox-above");
        String name = header.selectFirst(".fn").text();
        dataObject.addProperty("Name", name);

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
