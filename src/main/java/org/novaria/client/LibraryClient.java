package org.novaria.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.novaria.Model.Book;
import org.novaria.Model.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class LibraryClient {
    private final String baseUrl;
    private final HttpClient client;
    private final Gson gson;

    public LibraryClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    // Get library summary
    public String getLibrarySummary() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/library"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Get all books
    public List<Book> getAllBooks() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/library/books"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Type listType = new TypeToken<List<Book>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }

    // Get all users
    public List<User> getAllUsers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/library/users"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Type listType = new TypeToken<List<User>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }

    // Get books by category
    public List<Book> getBooksByCategory(String category) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/library/books/category/" + category))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<Book>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }

    // Get books by author
    public List<Book> getBooksByAuthor(String author) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/library/books/author/" + author))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<Book>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }
}
