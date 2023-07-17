package com.cations.oop.project.operations.crawler.nguoikesu;

import com.cations.oop.project.operations.crawler.BaseCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class NKSCharCrawler extends BaseCrawler {
    // create a HashSet to keep track of data
    Set<String> existingItems = new HashSet<>();

    @Override
    protected void process(Document document, String saveFile) throws IOException {
        String filePath = "out/nks/" + saveFile;

        // keep track of existing data
        File file = new File(filePath);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            // skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // add title to existing items
                existingItems.add(parts[0]);
            }
            reader.close();
        }

        // FileWriter and BufferedWriter
        FileWriter fileWriter = new FileWriter(filePath, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // write CSV header if file empty
        if (file.length() == 0) {
            bufferedWriter.write("Name,Info,URL");
            bufferedWriter.newLine();
        }

        // get blog__items
        Elements blogItems = document.select("div.com-content-category-blog__item");

        // iterate over blogItems and extract data for each blogItem
        for (Element blogItem : blogItems) {
            Element titleElement = blogItem.selectFirst("h2 a");

            // extract title
            String title = titleElement.text();
            System.out.println("Title: " + title);

            // check if the item already exists
            if (existingItems.contains("\"" + title + "\"")) {
                System.out.println(title + " already exists!\n");
                continue;
            } else {
                existingItems.add("\"" + title + "\"");
            }

            // extract info
            String info = "No info";
            try {
                info = blogItem.selectFirst("p").text();
                System.out.println("Info: " + info);
            } catch (NullPointerException e) {
                System.out.println("Info is empty!");
            }

            // deal with double quote in info
            info = info.replace("\"", "\"\"");

            // extract url
            String itemUrl = "https://nguoikesu.com" + titleElement.attr("href");
            System.out.println("URL: " + itemUrl);

            // write data to the CSV file
            bufferedWriter.write("\"" + title + "\",\"" + info + "\",\"" + itemUrl + "\"");
            bufferedWriter.newLine();

            System.out.println("Data for " + title + " saved successfully.\n");
        }

        bufferedWriter.close();
        fileWriter.close();
        System.out.println("Collected data: " + getCollectedCount() + "\n");
    }
}