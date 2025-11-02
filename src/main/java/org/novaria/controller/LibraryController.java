package org.novaria.controller;

import org.novaria.Model.Book;
import org.novaria.Model.Library;
import org.novaria.Model.User;

import java.util.List;

public class LibraryController {
    private Library library;

    public String getLibraryName(){
        return library.getName();
    }

    public List<Book> listAllBooks(){
        return library.getBooks();
    }

    public List<User> listAllUsers(){
       return library.getUsers();
    }

    public void addBookToLibrary(Book book){
        if (book == null){
            System.out.println("Invalid");
        }
        if (library.getBooks().contains(book)){
            System.out.println(book.getTitle() + " exists! ");
        }
        library.addBook(book);
    }

    public void removeBookFromLibrary(Book book){
        if (book == null){
            System.out.println("Invalid");
        }
        if (!library.getBooks().contains(book)){
            System.out.println(book.getTitle() + " doesn't exists!");
        }
        library.removeBook(book);
    }

    public List<Book> searchBooks(String keyword){
        return library.searchBooks(keyword);
    }

    public void displayLibrarySummary(){
        System.out.println(library.getName() + " contains " + library.getBooks().size() + " books, and " + library.getUsers().size() + " users.");
    }

    void fetchLibraryDataFromApi(String endpoint){}

    void syncLibraryWithServer(){}

}
