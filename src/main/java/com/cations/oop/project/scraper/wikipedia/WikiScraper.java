package com.cations.oop.project.scraper.wikipedia;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiScraper {
    public void scrape(String urlPath) {
        String url = "https://vi.wikipedia.org/wiki/" + urlPath; // Replace with the desired Wikipedia page URL

        try {
            Document doc = Jsoup.connect(url).get();
            Element infoBox = doc.selectFirst(".infobox"); // Assuming the info box has a class named "infobox"

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
