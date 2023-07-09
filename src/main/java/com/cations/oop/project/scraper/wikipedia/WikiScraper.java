package com.cations.oop.project.scraper.wikipedia;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiScraper extends BaseScrapper{

    @Override
    protected void processDoc(Document document) {
        System.out.println(document.title());

        // Get data from info box
        Element infoBox = document.selectFirst(".infobox");

        // Output found data - currently into console
        if (infoBox != null) {
            Elements rows = infoBox.select("tr");
            for (Element row : rows) {
                Element label = row.selectFirst("th");
                Element value = row.selectFirst("td");

                if (label != null && value != null) {
                    String labelText = label.text();
                    String valueText = value.text();

                    System.out.println(labelText + ": " + valueText);
                }
            }
        } else {
            System.out.println("Info box not found on the page.");
        }
    }
}
