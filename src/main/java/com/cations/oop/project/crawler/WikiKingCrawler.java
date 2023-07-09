package com.cations.oop.project.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class WikiKingCrawler extends BaseCrawler {

    @Override
    protected void processDocument(Document document) throws IOException {
        Elements tables = document.select("table:not(:first-child)"); // Select all tables on the page

        BufferedWriter linkWriter = new BufferedWriter(new FileWriter("out/wiki/wikiUrlPaths/kings.txt"));

        int totalKing = 0;
        for (Element table : tables) {
            Elements rows = table.select("tr");
            for (Element row : rows) {

                Elements columns = row.select("td");

                if (columns.size() >= 3) {
                    String relativeURL = columns.get(1).select("a").attr("href");

                    if (relativeURL.startsWith("/wiki/")){
                        totalKing++;
                        String decodedURL = URLDecoder.decode(relativeURL, StandardCharsets.UTF_8);
                        linkWriter.write("https://vi.wikipedia.org" + decodedURL);
                        linkWriter.newLine();
                    }

                    System.out.println("Added a king, total: " + totalKing);
                }
            }
        }
        linkWriter.close();

        System.out.println("Total: " + totalKing + " kings");
    }
}