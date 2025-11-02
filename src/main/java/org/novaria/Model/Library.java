package org.novaria.Model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Library {
    private final String name;
    private final List<Book> books = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final AtomicInteger bookIdCounter = new AtomicInteger(1);

    public Library(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public List<Book> getBooks() { return Collections.unmodifiableList(books); }
    public List<User> getUsers() { return Collections.unmodifiableList(users); }

    public boolean addBook(Book book) {
         if (!books.contains(book)) {
            books.add(book);
            return true;
        }
        return false; // Already exists
    }

    public boolean removeBookById(int bookId) {
        return books.removeIf(b -> b.getBookId() == bookId);
    }

    public Optional<Book> getBookById(int bookId) {
        return books.stream().filter(b -> b.getBookId() == bookId).findFirst();
    }

    public boolean registerUser(User user) {
         if (user == null || users.contains(user)) return false;
        users.add(user);
        return true;
    }

    public Optional<User> getUserById(int userId) {
        return users.stream().filter(u -> u.getUserId() == userId).findFirst();
    }

    public List<Book> searchBooks(String keyword) {
        String key = keyword.toLowerCase();
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(key) ||
                    book.getAuthor().getName().toLowerCase().contains(key) ||
                    book.getCategory().toLowerCase().contains(key)) {
                results.add(book);
            }
        }
        return results;
    }

    public List<Book> listBooksByCategory(String category) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) if (book.getCategory().equalsIgnoreCase(category)) results.add(book);
        return results;
    }

    public List<Book> listBooksByAuthor(String authorName) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) if (book.getAuthor().getName().equalsIgnoreCase(authorName)) results.add(book);
        return results;
    }

    public void removeBook(Book book) {
        books.removeIf(books::contains);
    }
}
