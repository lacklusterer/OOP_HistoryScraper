package com.cations.oop.project.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;

public class WikiKingCrawler extends BaseCrawler {
    private String outputFile;

    @Override
    protected void processDocument(Document document) throws IOException {
        Elements tables = document.select("table:not(:first-child)"); // Select all tables on the page

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        BufferedWriter linkWriter = new BufferedWriter(new FileWriter("out/wiki/wikiUrlPaths/kings.txt"));

        int tableCount = 0;
        int totalKing = 0;
        for (Element table : tables) {

            Element tableNameElement = table.previousElementSibling();

            if (tableNameElement != null) {
                tableCount++;
                System.out.println("Found a table! Table count: " + tableCount);
                String tableName = tableNameElement.text();
                writer.write("##########################\n" + tableCount + ". " + tableName + "\n");
                writer.newLine();
            }

            Elements rows = table.select("tr");
            int rowCount = 0;
            for (Element row : rows) {
                Elements columns = row.select("td");
                if (columns.size() >= 3) {
                    rowCount++;
                    totalKing++;
                    String kingColumn = columns.get(1).text();
                    String rulingYearColumn = columns.get(columns.size() - 1).text();
                    String relativeURL = columns.get(1).select("a").attr("href");

                    writer.write("King num: " + rowCount);
                    writer.newLine();
                    writer.write("King column: " + kingColumn);
                    writer.newLine();
                    writer.write("Ruling year column: " + rulingYearColumn);
                    writer.newLine();
                    writer.write("Hyperlink link: " + relativeURL + "\n");
                    writer.newLine();

                    if (relativeURL.length() >= 6) {
                        String decodedURL = URLDecoder.decode(relativeURL.substring(6), "UTF-8");
                        linkWriter.write(decodedURL);
                        linkWriter.newLine();
                    }

                    System.out.println("Added a king, king count: " + rowCount + ", king so far: " + totalKing);
                }
            }
        }
        linkWriter.close();
        writer.close();

        System.out.println("Total: " + totalKing + " kings");
        System.out.println("Links extracted and saved to " + outputFile);
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
}
