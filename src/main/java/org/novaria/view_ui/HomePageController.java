package org.novaria.view_ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.novaria.Model.Book;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HomePageController {

    @FXML
    private HBox featuredContainer;

    @FXML
    private VBox categoryContainer;

    @FXML
    private TextField searchField;

    private static final String BASE_URL = "http://localhost:4567";
    private final Gson gson = new Gson();

    @FXML
    public void initialize() {
        loadFeaturedBooks();
        loadCategoryBooks("MYSTERY");
        setupSearchListener();
    }

    // 📚 Load 5 featured books into the top scroll bar
    private void loadFeaturedBooks() {
        try {
            String json = getJsonFromUrl(BASE_URL + "/books");
            List<Book> books = gson.fromJson(json, new TypeToken<List<Book>>(){}.getType());

            featuredContainer.getChildren().clear();
            for (int i = 0; i < Math.min(5, books.size()); i++) {
                Book book = books.get(i);
                ImageView img = createBookImage(book);
                featuredContainer.getChildren().add(img);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🧭 Load books by category
    private void loadCategoryBooks(String category) {
        try {
            String json = getJsonFromUrl(BASE_URL + "/library/books/category/" + category);
            List<Book> books = gson.fromJson(json, new TypeToken<List<Book>>(){}.getType());

            HBox bookRow = new HBox(15);
            for (Book book : books) {
                ImageView img = createBookImage(book);
                bookRow.getChildren().add(img);
            }

            categoryContainer.getChildren().add(bookRow);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔍 Set up live search listener
    private void setupSearchListener() {
        searchField.setOnAction(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                searchBooks(keyword);
            }
        });
    }

    // 📖 Search books by keyword
    private void searchBooks(String keyword) {
        try {
            String json = getJsonFromUrl(BASE_URL + "/books/search/" + keyword);
            List<Book> results = gson.fromJson(json, new TypeToken<List<Book>>(){}.getType());

            featuredContainer.getChildren().clear();
            for (Book book : results) {
                ImageView img = createBookImage(book);
                featuredContainer.getChildren().add(img);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🧠 Helper — fetch JSON from API
    private String getJsonFromUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        try (InputStream input = conn.getInputStream()) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    // 🧠 Helper — create a clickable book cover ImageView
    private ImageView createBookImage(Book book) {
        String imagePath = book.getCoverImageUrl();
        String fallback = String.valueOf(getClass().getResource("/media/icons/cover.jpg"));

        ImageView img = new ImageView();
        img.setFitWidth(120);
        img.setFitHeight(180);
        img.setPreserveRatio(true);
        img.setSmooth(true);

        // Add hover effect styling
        img.setStyle("-fx-cursor: hand;");

        // Start with fallback
        Image fallbackImage = new Image(fallback);
        img.setImage(fallbackImage);

        // If we have a remote URL, try loading it
        if (imagePath != null && !imagePath.isBlank() &&
                (imagePath.startsWith("http://") || imagePath.startsWith("https://"))) {

            System.out.println("📥 Loading cover for: " + book.getTitle());

            // Load in background thread
            new Thread(() -> {
                try {
                    Image coverImage = new Image(imagePath, true);

                    Platform.runLater(() -> {
                        if (!coverImage.isError()) {
                            img.setImage(coverImage);
                            System.out.println("✅ Cover loaded for: " + book.getTitle());
                        }
                    });

                } catch (Exception e) {
                    System.out.println("⚠ Failed loading cover for: " + book.getTitle());
                }
            }).start();
        }

        // ✅ Make clicking open the Reader View
        img.setOnMouseClicked(e -> {
            System.out.println("📖 Opening book: " + book.getTitle());
            openReaderView(book);
        });

        // Add hover effect
        img.setOnMouseEntered(e -> img.setOpacity(0.7));
        img.setOnMouseExited(e -> img.setOpacity(1.0));

        return img;
    }

     // 📖 Open the Reader View with the selected book
    private void openReaderView(Book book) {
        try {
            // First, tell the server to open this book using POST
            String response = postToUrl(BASE_URL + "/reader/open/" + book.getBookId());
            System.out.println("Server response: " + response);

            // Load the Reader View FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReaderView.fxml"));
            Parent readerView = loader.load();

            // Get the controller and pass the book data
            ReaderViewController controller = loader.getController();
            controller.setBook(book);

            // Get current stage and switch scene
            Stage stage = (Stage) featuredContainer.getScene().getWindow();
            Scene scene = new Scene(readerView);
            stage.setScene(scene);
            stage.setTitle("Reading: " + book.getTitle());

        } catch (Exception e) {
            System.err.println("❌ Failed to open reader view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Add this new helper method for POST requests
    private String postToUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            try (InputStream input = conn.getInputStream()) {
                return new String(input.readAllBytes(), StandardCharsets.UTF_8);
            }
        } else {
            throw new IOException("Server returned: " + responseCode);
        }
    }
}