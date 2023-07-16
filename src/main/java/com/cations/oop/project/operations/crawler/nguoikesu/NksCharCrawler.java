package com.cations.oop.project.operations.crawler.nguoikesu;

import com.cations.oop.project.operations.crawler.BaseCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NksCharCrawler extends BaseCrawler {
    @Override
    protected void findLinks(Document document) throws IOException {
        Elements blogItems = document.select("div.com-content-category-blog__item");

        try {
            // Specify the file path where you want to save the data
            String filePath = "out/nks/test.csv";

            // Create a FileWriter and BufferedWriter to write to the file
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the CSV header
            bufferedWriter.write("Name,Info");
            bufferedWriter.newLine();

            for (Element blogItem : blogItems) {
                // Extract title
                Element titleElement = blogItem.selectFirst("h2 a");
                String title = titleElement.text();
                System.out.println("Title: " + title);

                // Extract info
                String info = blogItem.selectFirst("p").text();
                System.out.println("p: " + info);

                System.out.println();

                // Write the data to the CSV file
                bufferedWriter.write("\"" + title + "\"" + "," + "\"" + info + "\"");
                bufferedWriter.newLine();
            }

            // Close the resources
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Data saved successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
