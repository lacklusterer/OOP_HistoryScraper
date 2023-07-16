package com.cations.oop.project.operations.scraper.wikipedia;

import com.cations.oop.project.operations.scraper.BaseScrapper;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiPersonScraper extends BaseScrapper {
    protected JsonObject getInfo(Element infoBox, String name) {
        // JsonObject to hold the data
        JsonObject personInfo = new JsonObject();
        Elements rows = infoBox.select("tr");

        personInfo.addProperty("Name", name);

        // Iterate over infobox's rows
        for (Element row : rows) {
            Element headerElement = row.selectFirst("th");
            Element valueElement = row.selectFirst("td");

            if (headerElement != null && valueElement != null) {
                String header = headerElement.text();
                String value = valueElement.text();

                personInfo.addProperty(header, value);
            }
        }

        return personInfo;
    }
}
