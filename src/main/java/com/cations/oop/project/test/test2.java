package com.cations.oop.project.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class test2 {

    public static void main(String[] args) {
        String url = "https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam";
        String outputFile = "output.txt";

        try {
            Document document = Jsoup.connect(url).get();
            Elements tables = document.select("table:not(:first-child)"); // Select all tables on the page

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            for (Element table : tables) {

                Element tableNameElement = table.previousElementSibling();

                if (tableNameElement != null) {
                    String tableName = tableNameElement.text();
                    writer.write("##########################\n" + tableName + "\n");
                    writer.newLine();
                }

                Elements rows = table.select("tr");
                for (Element row : rows) {
                    Elements columns = row.select("td");
                    if (columns.size() >= 3) {
                        String kingColumn = columns.get(1).text();
                        String rulingYearColumn = columns.get(columns.size() - 1).text();
                        String hyperlinkLink = columns.get(1).select("a").attr("href");

                        writer.write("King column: " + kingColumn);
                        writer.newLine();
                        writer.write("Ruling year column: " + rulingYearColumn);
                        writer.newLine();
                        writer.write("Hyperlink link: " + hyperlinkLink + "\n");
                        writer.newLine();
                    }
                }
            }
            writer.close();

            System.out.println("Links extracted and saved to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
