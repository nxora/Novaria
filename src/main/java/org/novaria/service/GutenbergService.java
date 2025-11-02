package org.novaria.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.novaria.Model.Author;
import org.novaria.Model.Book;
import org.novaria.Model.Library;
import org.novaria.Model.User;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicInteger;

public class GutenbergService {
    private final HttpClient httpClient;
    private final Gson gson;
    private final Library library;
    private static final AtomicInteger idGenerator = new AtomicInteger(100000);

    public GutenbergService(Library library) {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
        this.library = library;
    }

    public void importBooksFromApi(String searchTerm) {
        try {
            String url = "https://gutendex.com/books?search=" + URLEncoder.encode(searchTerm, "UTF-8");
            System.out.println("🔍 Fetching books from: " + url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject root = gson.fromJson(response.body(), JsonObject.class);
                JsonArray results = root.getAsJsonArray("results");

                System.out.println("📚 Found " + (results != null ? results.size() : 0) + " books");

                if (results != null) {
                    for (JsonElement element : results) {
                        JsonObject bookJson = element.getAsJsonObject();

                        int gutenbergId = bookJson.get("id").getAsInt();
                        String title = bookJson.get("title").getAsString();

                        JsonArray authorsArray = bookJson.getAsJsonArray("authors");
                        String authorName = authorsArray.size() > 0
                                ? authorsArray.get(0).getAsJsonObject().get("name").getAsString()
                                : "Unknown";

                        JsonObject formats = bookJson.getAsJsonObject("formats");
                        String coverUrl = formats.has("image/jpeg")
                                ? formats.get("image/jpeg").getAsString()
                                : null;

                        String textUrl = null;
                        if (formats.has("text/plain")) {
                            textUrl = formats.get("text/plain").getAsString();
                        } else if (formats.has("text/html")) {
                            textUrl = formats.get("text/html").getAsString();
                        }

                        Author author = new Author(gutenbergId, authorName, 0, "", "", User.Gender.OTHER);

                        Book book = new Book(
                                title,
                                author,
                                "Unknown",
                                "",
                                gutenbergId
                        );

                        book.setCoverImageUrl(coverUrl);
                        book.setTextUrl(textUrl);

                        library.addBook(book);
                        System.out.println("✅ Imported: " + title);
                    }
                }

                System.out.println("📖 Total books in library: " + library.getBooks().size());
            } else {
                System.out.println("❌ API returned status: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("❌ Error importing books: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
