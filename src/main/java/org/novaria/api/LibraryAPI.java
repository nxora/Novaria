package org.novaria.api;

import static spark.Spark.*;
import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class LibraryAPI {

    public static void main(String[] args) {

        port(8080);
        Gson gson = new Gson();

        get("/api/books", (req, res) -> {
            res.type("application/json");
            String query = req.queryParams("q");
            if (query == null || query.isEmpty()) query = "sherlock holmes";

            String urlString = "https://gutendex.com/books?search=" + URLEncoder.encode(query, "UTF-8");
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

            List<Map<String, Object>> booksList = new ArrayList<>();

            for (JsonElement element : results) {
                JsonObject book = element.getAsJsonObject();

                String title = book.get("title").getAsString();

                JsonArray authors = book.getAsJsonArray("authors");
                String authorName = !authors.isEmpty() ? authors.get(0).getAsJsonObject().get("name").getAsString() : "Unknown";

                JsonArray bookshelves = book.getAsJsonArray("bookshelves");
                String category = !bookshelves.isEmpty() ? bookshelves.get(0).getAsString() : "Uncategorized";

                JsonArray languages = book.getAsJsonArray("languages");
                String language = !languages.isEmpty() ? languages.get(0).getAsString() : "Unknown";

                int downloadCount = book.get("download_count").getAsInt();

                JsonObject formats = book.getAsJsonObject("formats");
                String image = formats.has("image/jpeg") ? formats.get("image/jpeg").getAsString() : "N/A";
                String htmlLink = formats.has("text/html") ? formats.get("text/html").getAsString() : "N/A";


                Map<String, Object> bookData = new HashMap<>();
                bookData.put("title", title);
                bookData.put("author", authorName);
                bookData.put("language", language);
                bookData.put("category", category);
                bookData.put("downloads", downloadCount);
                bookData.put("cover", image);
                bookData.put("link", htmlLink);

                booksList.add(bookData);
            }

            return gson.toJson(booksList);
        });
    }
}
