package com.cations.oop.project.operations.crawler.nguoikesu;

import com.cations.oop.project.operations.crawler.BaseCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class NksCharCrawler extends BaseCrawler {
    // create a HashSet to keep track of data
    Set<String> existingItems = new HashSet<>();

    @Override
    protected void process(Document document) throws IOException {
        // get blog__items
        Elements blogItems = document.select("div.com-content-category-blog__item");

        String filePath = "out/nks/test.csv";

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
            bufferedWriter.write("Name,Info");
            bufferedWriter.newLine();
        }

        // iterate over blogItems and extract data for each blogItem
        for (Element blogItem : blogItems) {
            // extract title
            Element titleElement = blogItem.selectFirst("h2 a");
            String title = titleElement.text();
            System.out.println("Title: " + title);

            // check if the item already exists
            if (existingItems.contains(title)) {
                System.out.println(title + " already exists!\n");
                continue;
            }

            // extract info
            String info = blogItem.selectFirst("p").text();
            System.out.println("Info: " + info);

            // write data to the CSV file
            bufferedWriter.write(title+ "," + "\"" + info + "\"");
            bufferedWriter.newLine();

            System.out.println("Data for " + title + " saved successfully.\n" );
        }

        // add the item's title to the existingItems Set
        for (Element blogItem : blogItems) {
            Element titleElement = blogItem.selectFirst("h2 a");
            String title = titleElement.text();
            existingItems.add(title);
        }

        bufferedWriter.close();
        fileWriter.close();
        System.out.println("Collected data: " + getCollectedCount() + "\n");
    }

    public int getCollectedCount() {
        return existingItems.size();
    }
}
