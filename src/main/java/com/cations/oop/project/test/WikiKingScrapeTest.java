package com.cations.oop.project.test;

import com.cations.oop.project.scraper.wikipedia.WikiApiScraper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WikiKingScrapeTest {
    public static void main(String[] args) {
        WikiApiScraper scraper = new WikiApiScraper();

        String filePath = "out/wiki/wikiUrlPaths/kings.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String king;
            int count = 0;
            while ((king = reader.readLine()) != null && count <= 5) {
                scraper.getJson(king);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
