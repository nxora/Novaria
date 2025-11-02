package org.novaria;

import org.novaria.Model.*;
import org.novaria.client.LibraryClient;
import org.novaria.client.UserClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackendTest {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Author author0 = new Author(0, "Rick Riordan", 61, "rickriordan@yahoo.com","103312843", User.Gender.MALE);
        Author author1 = new Author(1, "J. K. Rowling", 60, "jkrowling@gmail.com","103317403", User.Gender.FEMALE);
        Author author2 = new Author(2, "George R. R. Martin", 65,"georgemartin@duckduckgo.com", "103317403", User.Gender.MALE);


        Book books = new Book("Percy Jackson And The Olympians", author0, "ADVENTURE", "Mythological Tale", 1);
        Book book1 = new Book("Harry Potter", author1, "FANTASY", "tale of young wizards and his friends", 2);
        Book book3 = new Book("A Song of Fire and Ice", author0, "FICTION", "Fictional tale of power and betrayalLorem ipsum dolor sit amet, consectetur adipisicing elit. Doloremque, repudiandae aperiam. Ab nemo error mollitia aut ratione, cum incidunt eveniet!", 3);

        List<Book> allBooks = new ArrayList<>(Arrays.asList(book1, books));
        Library mainLibrary = new Library("Novaria");

//Used your name Hehe!!
        User user1 = new User(2, "Chat", 52, User.Gender.OTHER, author0.getEmail(), "password", mainLibrary);

        String baseUrl = "http://localhost:4567";

        UserClient userClient = new UserClient(baseUrl);
        LibraryClient libraryClient = new LibraryClient(baseUrl);

        try {
            // ===== 1️⃣ Add a new user =====
//            User newUser = new User(101, "Dave", ,"dave@example.com");
            User newUser = new User(1, "ChatGpt", 5, User.Gender.OTHER, author2.getEmail(),  "password", mainLibrary);
            boolean added = userClient.addUser(newUser);
            System.out.println("User added: " + added);

            // ===== 2️⃣ Fetch all users =====
            List<User> allUsers = libraryClient.getAllUsers();
            System.out.println("\nAll Users:");
            allUsers.forEach(u -> System.out.println(u.getUserId() + " - " + u.getName()));

            // ===== 3️⃣ Get library summary =====
            String summary = libraryClient.getLibrarySummary();
            System.out.println("\nLibrary Summary: " + summary);

            // ===== 4️⃣ Fetch all books =====
            List<Book> bulkBooks = libraryClient.getAllBooks();
            System.out.println("\nAll Books:");
            bulkBooks.forEach(b -> System.out.println(b.getTitle() + " by " + b.getAuthor()));

            // ===== 5️⃣ Fetch books by category =====
            String category = "Science";
            List<Book> scienceBooks = libraryClient.getBooksByCategory(category);
            System.out.println("\nBooks in category '" + category + "':");
            scienceBooks.forEach(b -> System.out.println(b.getTitle()));

            // ===== 6️⃣ Fetch user's saved books =====
            int userId = 101;
            List<Book> savedBooks = userClient.getUserSavedBooks(userId);
            System.out.println("\nUser " + userId + " saved books:");
            savedBooks.forEach(b -> System.out.println(b.getTitle()));

            // ===== 7️⃣ Fetch user's reading history =====
            List<Book> history = userClient.getUserHistory(userId);
            System.out.println("\nUser " + userId + " reading history:");
            history.forEach(b -> System.out.println(b.getTitle()));

            // ===== 8️⃣ Delete user =====
            boolean deleted = userClient.deleteUser(userId);
            System.out.println("\nUser deleted: " + deleted);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
//        user.joinLibrary();
//        user.saveToLibrary(book1);
////
////        Acceptance tests (Reader):
//        Reader reader = new Reader(user);
//        reader.openBook(book1);
//        reader.nextPage();
//        reader.bookmarkPage();
//        reader.resumeReading();
//        reader.showProgress();
//        reader.closeBook();
////
////        user.viewProfileSummary();
////        author1.printBio();
//
////        Acceptance tests (Library):
//        System.out.println(mainLibrary.findBookById(999));
//        System.out.println(mainLibrary.listBooksByCategory(Book.Category.FICTION));
//        System.out.println(books.getBookId());
//
////        Acceptance tests:
//        author0.setWorks(allBooks);
//        System.out.println(author0.getWorks());
//
////        Acceptance tests (User):
//        user.saveToLibrary(book3);
//        System.out.println(user.hasBook(book3));
//        user.viewProfileSummary();
//
////        Acceptance tests: (Author)
//        System.out.println(author0.getWorks());
//        author0.setWorksFromLibrary(mainLibrary);


//        UserController userController = new UserController(mainLibrary, new ArrayList<>());
//        userController.addUser(user);
//        userController.viewProfile(user);
//        System.out.println("Total users: " + userController.getAllUsers().size());

//        Library library = new Library("Novaria", null);
//        GutenbergService service = new GutenbergService(library);
//
//        service.importBooksFromApi("sherlock");
//        System.out.println("Library size after import: "+ library.getBooksList());

}

}



