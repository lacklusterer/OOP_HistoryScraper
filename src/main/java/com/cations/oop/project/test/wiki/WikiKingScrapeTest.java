package com.cations.oop.project.test.wiki;

import com.cations.oop.project.operations.scraper.wikipedia.WikiPersonScraper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WikiKingScrapeTest {
    public static void main(String[] args) {
        WikiPersonScraper scraper = new WikiPersonScraper();

        String filePath = "out/wiki/wikiUrlPaths/kings.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String url;
            // int count = 0;
            while ((url = reader.readLine()) != null /*&& count <= 10*/) {
                scraper.scrape(url, "out/wiki/wikiScrappedKing/");
                // count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
