package org.novaria.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiTest {

        public static void main(String[] args) {
            try {
                String query = "sherlock holmes";
                String urlString = "https://gutendex.com/books?search=" + query.replace(" ", "%20");
                URL url = new URL(urlString);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray results = jsonResponse.getAsJsonArray("results");

                System.out.println("Found: " +results.size() +" books");
                for (JsonElement element : results){
                    JsonObject book = element.getAsJsonObject();
                    String title = book.get("title").getAsString();
                    String language = book.getAsJsonArray("languages").get(0).getAsString();
                    int downloadCount = book.get("download_count").getAsInt();

                    JsonObject formats = book.getAsJsonObject("formats");
                    String htmlLink = formats.has("text/html") ? formats.get("text/html").getAsString() : "N/A";
                    String image = formats.has("image/jpeg") ? formats.get("image/jpeg").getAsString() : "N/A";

                    JsonArray authors = book.getAsJsonArray("authors");
                    String authorName = !authors.isEmpty() ? authors.get(0).getAsJsonObject().get("name").getAsString() : "Unknown";

                    JsonArray bookshelf = book.getAsJsonArray("bookshelves");
                    String category = !bookshelf.isEmpty() ? bookshelf.get(0).getAsString(): "Uncategorized";

                    System.out.println("───────────────");
                    System.out.println("📖 Title: " + title);
                    System.out.println("👤 Author: " + authorName);
                    System.out.println("🌍 Language: " + language);
                    System.out.println( category);
                    System.out.println("⬇ Downloads: " + downloadCount);
                    System.out.println("🖼 Cover: " + image);
                    System.out.println("🔗 Link: " + htmlLink);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

