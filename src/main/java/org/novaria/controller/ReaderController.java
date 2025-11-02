package org.novaria.controller;

import org.novaria.Model.Book;
import org.novaria.Model.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ReaderController {
    private final User currentUser;
    private Book currentBook;
    private String[] pages;

    public int getCurrentPage() {
        return currentPage;
    }

    private int currentPage;
    private int bookmarkedPage = -1;

    public ReaderController(User currentUser) {
        this.currentUser = currentUser;
    }

    public void openBook(Book book) {
        if (book == null) {
            System.out.println("Invalid book.");
            return;
        }

        this.currentBook = book;
        this.currentPage = 0;

        try {
            // Fetch remote text
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(book.getTextUrl()))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String text = response.body();

            // Page chunk text every 1200 chars
            this.pages = text.split("(?<=\\G.{1200})");

        } catch (Exception e) {
            System.out.println("Failed to fetch text: " + e.getMessage());
            this.pages = new String[]{"No content available for this book."};
        }

        currentUser.addToHistory(book);

        System.out.println("Opened: " + book.getTitle());
        displayPage();
    }


    public String[] getPages() {
        return pages;
    }



    public void displayPage() {
        if (pages == null || pages.length == 0) {
            System.out.println("No content available for this book.");
            return;
        }

        System.out.println("Page " + (currentPage + 1) + "/" + pages.length);
        System.out.println(pages[currentPage]);
    }

    public void nextPage() {
        if (!ensureBookOpen()) {
            System.out.println("No book is open.");
            return;
        }

        if (currentPage < pages.length - 1) {
            currentPage++;
            displayPage();
        } else {
            System.out.println("You’ve reached the end of the book!");
        }
    }

    public void previousPage() {
        if (!ensureBookOpen()) {
            System.out.println("No book is open.");
            return;
        }

        if (currentPage > 0) {
            currentPage--;
            displayPage();
        } else {
            System.out.println("Already at the beginning!");
        }
    }

    public boolean goToPage(int pageNumber) {
        if (pages == null) {
            System.out.println("No book is open.");
            return false;
        }

        if (pageNumber < 1 || pageNumber > pages.length) {
            System.out.println("Enter a valid page number.");
            return false;
        }

        currentPage = pageNumber - 1;
        displayPage();
        return true;
    }

    public void bookmarkPage() {
        if (!ensureBookOpen()) {
            System.out.println("No book is open to bookmark.");
            return;
        }

        bookmarkedPage = currentPage;
        System.out.println("Bookmarked page " + (currentPage + 1) + " of " + currentBook.getTitle());
    }

    public void resumeReading() {
        if (!ensureBookOpen()) {
            System.out.println("No book open to resume.");
            return;
        }

        if (bookmarkedPage < 0 || bookmarkedPage >= pages.length) {
            System.out.println("No valid bookmark found.");
            return;
        }

        currentPage = bookmarkedPage;
        System.out.println("Resumed " + currentBook.getTitle() + " at page " + (currentPage + 1));
        displayPage();
    }

    public void closeBook() {
        if (ensureBookOpen()){
            System.out.println("Closed: " + currentBook.getTitle());
            currentBook = null;
            pages = null;
            currentPage = 0;
            bookmarkedPage = -1;
        } else {
            System.out.println("No book is currently open.");
        }
    }
    public void showProgress() {

        if (!ensureBookOpen() || pages == null) {
            System.out.println("No book open to track progress.");
            return;
        }

        int percentage = (currentPage + 1) * 100 / pages.length;
        System.out.println("Progress: " + (currentPage + 1) + " / " + pages.length + " pages (" + percentage + "%)");
    }
    private boolean ensureBookOpen() {
        if (currentBook == null) {
            System.out.println("No book is open.");
            return false;
        }
        return true;
    }

}
