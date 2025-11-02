package org.novaria.view_ui;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.novaria.Model.Book;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ReaderViewController {

    @FXML
    private Label bookTitleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label pageNumberLabel;

    @FXML
    private TextArea contentArea;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    @FXML
    private Button bookmarkButton;

    private static final String BASE_URL = "http://localhost:4567";
    private final Gson gson = new Gson();
    private Book currentBook;

    @FXML
    public void initialize() {
        setupButtons();
    }

    public void setBook(Book book) {
        this.currentBook = book;
        bookTitleLabel.setText(book.getTitle());
        authorLabel.setText("by " + book.getAuthor().getName());
        loadCurrentPage();
    }

    private void setupButtons() {
        prevButton.setOnAction(e -> previousPage());
        nextButton.setOnAction(e -> nextPage());
        bookmarkButton.setOnAction(e -> bookmarkPage());
        backButton.setOnAction(e -> goBackToHome());
    }

    private void loadCurrentPage() {
        try {
            String json = getJsonFromUrl(BASE_URL + "/reader/progress");
            Map<String, Object> progress = gson.fromJson(json, Map.class);

            int currentPage = ((Double) progress.get("currentPage")).intValue();
            int totalPages = ((Double) progress.get("totalPages")).intValue();

            pageNumberLabel.setText("Page " + currentPage + " of " + totalPages);

            // Load page content (you'll need to implement this endpoint)
            // For now, just show a placeholder
            contentArea.setText("Page content will be loaded here...\n\nBook: " + currentBook.getTitle());

        } catch (Exception e) {
            System.err.println("Failed to load page: " + e.getMessage());
        }
    }

    private void nextPage() {
        try {
            getJsonFromUrl(BASE_URL + "/reader/next");
            loadCurrentPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void previousPage() {
        try {
            getJsonFromUrl(BASE_URL + "/reader/prev");
            loadCurrentPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bookmarkPage() {
        try {
            String response = getJsonFromUrl(BASE_URL + "/reader/bookmark");
            System.out.println("Bookmark saved: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goBackToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage.fxml"));
            Parent homeView = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(homeView);
            stage.setScene(scene);
            stage.setTitle("Novaria - Library");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getJsonFromUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(urlString.contains("/reader/") && !urlString.contains("/progress") ? "POST" : "GET");
        conn.connect();
        try (InputStream input = conn.getInputStream()) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}