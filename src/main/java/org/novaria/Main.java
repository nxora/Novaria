package org.novaria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

         Author author1 = new Author(1, "J.K Rowling", 58, 103312843, User.Gender.MALE);
        Author author2 = new Author(0, "Nicholas Sparks", 57, 103312843, User.Gender.MALE);


        Book books = new Book("Revenge", author1, Book.Category.ADVENTURE, "book about revenge",   1);
        Book book1 = new Book("LoveLust", author2, Book.Category.FANTASY,"tale of love",    2);
        Book book3 = new Book("ShimmeringPast", author2, Book.Category.MYSTERY,"tale of mysteries",    3);

        List<Book> allBooks = new ArrayList<>(Arrays.asList(book1, books));
         Library mainLibrary = new Library("Novaria",allBooks);

        User user = new User(1, "Mycah", 21,User.Gender.MALE, mainLibrary);
        User user1 = new User(2, "Mariah", 21, User.Gender.FEMALE, mainLibrary);

        Reader reader = new Reader(user);

//        Add books to library
         System.out.println(mainLibrary.addBook(book3));
        System.out.println(user.saveToLibrary(books));

//        Save them to user library
        System.out.println(user.setSavedbookList(allBooks));

//        Open one with Reader and flip pages
        reader.openBook(book3);
        reader.closeBook();
        System.out.println(user.getBookhistory());
    }
}



