package org.novaria.controller;

import org.novaria.Model.Book;
import org.novaria.Model.Library;

import java.util.ArrayList;
import java.util.List;

public class BookController {

private final Library library;

    public BookController(Library library) {
        this.library = library;
    }
    public List<Book> getAllBooks() {
        // return all books in the library
        return new ArrayList<>(library.getBooks());
    }

    public Book getBookById(int id) {
        return library.getBooks().stream()
                .filter(book -> book.getBookId() == id)
                .findFirst()
                .orElse(null);
    }



    public List<Book> searchBooks(String keyword) {
        // search books using the library.searchBook() method
        return library.searchBooks(keyword);
    }
    public boolean addBook(Book book) {
        // add a new book to the library (if it doesn’t already exist)
        if(book == null ){
            return false;
        }
        if (!library.getBooks().contains(book)) {
            library.getBooks().add(book);
            System.out.println("Book added successfully!");
            return true;
        } else {
            System.out.println("Book already exists in library.");
        }
        return false;
    }
}
