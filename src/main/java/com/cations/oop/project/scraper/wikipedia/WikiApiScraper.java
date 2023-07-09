package com.cations.oop.project.scraper.wikipedia;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class WikiApiScraper {

    public void getJson(String urlPath) throws UnsupportedEncodingException {
        String decodedKingName = URLDecoder.decode(urlPath, "UTF-8");
        String filePath = "out/wiki/wikiJson/kings/" + decodedKingName + ".json";
        File file = new File(filePath);

        if (file.exists()) {
            System.out.println("File already exists: " + filePath);
            return; // Skip making the HTTP request
        }

        try {
            String apiUrl = "https://vi.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&titles=";

            // Complete url
            URL url = new URL(apiUrl + urlPath);

            // Create connection object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Get the response code
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                // Save the response to a file
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                writer.write(response.toString());
                writer.close();

                System.out.println("Response saved to: " + filePath);
            } else {
                System.out.println("HTTP GET request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
