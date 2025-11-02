package org.novaria.Model;

import java.util.Optional;

public class Reader {
    private Book currentBook;
    private final User currentUser;
    private int currentPage;
    private String[] pages; // split content by pages
    private int bookmarkedPage = -1;

    public Reader(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() { return currentUser; }
    public Optional<Book> getCurrentBook() { return Optional.ofNullable(currentBook); }
    public int getCurrentPage() { return currentPage; }
    public int getBookmarkedPage() { return bookmarkedPage; }
    public int getTotalPages() { return pages != null ? pages.length : 0; }

    public boolean openBook(Book book) {
        if (book == null) return false;
        this.currentBook = book;
        this.currentPage = 0;
        this.pages = book.getDescription().split("(?<=\\G.{500})"); // split every 500 chars
        currentUser.addToHistory(book);
        return true;
    }

    public boolean goToPage(int pageNumber) {
        if (pages == null || pageNumber < 1 || pageNumber > pages.length) return false;
        this.currentPage = pageNumber - 1;
        return true;
    }

    public boolean nextPage() {
        if (currentBook == null || pages == null) return false;
        if (currentPage < pages.length - 1) {
            currentPage++;
            return true;
        }
        return false; // already at last page
    }

    public boolean previousPage() {
        if (currentBook == null || pages == null) return false;
        if (currentPage > 0) {
            currentPage--;
            return true;
        }
        return false; // already at first page
    }

    public void closeBook() {
        currentBook = null;
        pages = null;
        currentPage = 0;
        bookmarkedPage = -1;
    }

    public boolean bookmarkPage() {
        if (currentBook == null || pages == null) return false;
        bookmarkedPage = currentPage;
        return true;
    }

    public boolean resumeReading() {
        if (currentBook == null || pages == null || bookmarkedPage < 0 || bookmarkedPage >= pages.length)
            return false;
        currentPage = bookmarkedPage;
        return true;
    }

    public double getProgressPercentage() {
        if (pages == null || pages.length == 0) return 0.0;
        return ((currentPage + 1) * 100.0) / pages.length;
    }

    public Optional<String> getCurrentPageContent() {
        if (pages == null || pages.length == 0) return Optional.empty();
        return Optional.of(pages[currentPage]);
    }
}
