package org.novaria;

import java.util.ArrayList;
import java.util.List;

public class Library {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<Book> booksList;
    private List<User> users;
    private String name;

    public Library(String name, List<Book> booksList) {
        this.name = name;
        this.booksList = booksList != null ? booksList : new ArrayList<>();
        this.users = new ArrayList<>();
    }


    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
    }
    public List<Book> getBooksList() {
        return booksList;
    }

    public boolean addBook(Book book) {
        if (book == null) return false;
        if (booksList.contains(book)) {
            System.out.println("This book is already in the library!");
            return false;
        }
             booksList.add(book);
            System.out.println(book.getTitle() + " was added successfully!");
         return true;
    }

    public boolean removeBook(Book book) {
        if (book == null){
            System.out.println("Enter a book");
            return false;
    }else if (booksList.contains(book)) {
            booksList.remove(book);
            System.out.println(book.getTitle() + " has been successfully removed");
            return true;
        } else {
            System.out.println(book.getTitle() + " doesn't exists");
        }
        return false;
    }

    public List<Book> searchBook(String keyword) {
        List<Book> results = new ArrayList<>();
        for (Book book : booksList) {
            if (book.getTitle().toLowerCase().contains(keyword)
                    || book.getAuthor().getName().toLowerCase().contains(keyword)
                    || book.getCategory().toString().toLowerCase().contains(keyword)) {
                results.add(book);
            }
        }
        if (results.isEmpty()) {
            System.out.println("No results found for: " + keyword);
        }
        return results;
    }


    public boolean registerUser(User user){
        if (users.contains(user)){
            System.out.print(user + " already exists");
            return false;
        } else if (user == null) {
            System.out.print(" You need to register");
            return false;
        }
        users.add(user);
        return true;
    }

    public List<Book> listAvailableBooks() {
        return booksList;
    }
    public boolean isBookAvailable(Book book) {
        return booksList.contains(book);
    }

    }
