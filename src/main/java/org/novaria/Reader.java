package org.novaria;

public class Reader {
    private Book currentBook;
    private User currentUser;
    private int currentPage;
    private String[] pages;  // For simplicity, split content by pages later

    public Reader(User user) {
        this.currentUser = user;
    }

    // Open a book for reading
    public void openBook(Book book) {
        this.currentBook = book;
        this.currentPage = 0;
        this.pages = book.getDescription().split("(?<=\\G.{500})");
        // splits every 500 chars for demo purposes

        currentUser.addToHistory(book);

        System.out.println("Opened: " + book.getTitle());
        displayPage();
    }

    // Display the current page
    public void displayPage() {
        if (pages == null || pages.length == 0) {
            System.out.println("No content available for this book.");
            return;
        }
        System.out.println("Page " + (currentPage + 1) + "/" + pages.length);
        System.out.println(pages[currentPage]);
    }

    // Go to next page
    public void nextPage() {
        if (currentBook == null) {
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

    // Go to previous page
    public void previousPage() {
        if (currentBook == null) {
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

    // Close the book
    public void closeBook() {
        if (currentBook != null) {
            System.out.println("Closed: " + currentBook.getTitle());
            currentBook = null;
            pages = null;
            currentPage = 0;
        } else {
            System.out.println("No book is currently open.");
        }
    }
}
