package org.novaria.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.novaria.Model.Book;
import org.novaria.Model.Author;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class AuthorClient {

    private final HttpClient client;
    private final Gson gson;
    private final String baseUrl;

    public AuthorClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    // Fetch all books
    public List<Book> getAllBooks() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/books"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type type = new TypeToken<List<Book>>() {}.getType();
        return gson.fromJson(response.body(), type);
    }

    // Extract unique authors from all books
    public List<Author> getAllAuthors() throws IOException, InterruptedException {
        List<Book> books = getAllBooks();
        List<Author> authors = new ArrayList<>();
        for (Book book : books) {
            boolean exists = authors.stream().anyMatch(a -> a.getName().equalsIgnoreCase(book.getAuthor().getName()));
            if (!exists) {
                authors.add(book.getAuthor());
            }
        }
        return authors;
    }
}
