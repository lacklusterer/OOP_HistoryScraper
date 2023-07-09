package com.cations.oop.project.test.wiki;

import com.cations.oop.project.scraper.wikipedia.WikiScraper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WikiKingScrapeTest {
    public static void main(String[] args) {
        WikiScraper scraper = new WikiScraper();

        String filePath = "out/wiki/wikiUrlPaths/kings.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String url;
            // int count = 0;
            while ((url = reader.readLine()) != null /*&& count <= 10*/) {
                scraper.scrape(url);
                // count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
