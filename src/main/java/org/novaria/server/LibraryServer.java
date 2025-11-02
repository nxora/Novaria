package org.novaria.server;

import com.google.gson.Gson;
import org.novaria.Model.Book;
import org.novaria.Model.Library;
 import org.novaria.Model.User;
import org.novaria.controller.BookController;
import org.novaria.controller.ReaderController;
import org.novaria.controller.UserController;
import org.novaria.service.GutenbergService;

 import java.util.List;
import java.util.Map;
import java.util.Optional;

import static spark.Spark.*;

public class LibraryServer {
    private static User user;

    public static void main(String[] args) {
        port(4567); // You can change the port if needed

        Library library = new Library("Novaria");
        Gson gson = new Gson();

        GutenbergService service = new GutenbergService(library);
        service.importBooksFromApi("sherlock");


        BookController bookController = new BookController(library);
        UserController userController = new UserController(library,library.getUsers());
        user = new User(1, "Demo User", 20, User.Gender.OTHER, "demo@gmail.com","1234", library);
         library.registerUser(user);

        ReaderController reader = new ReaderController(user);

        ///BOOK ENDPOINTS

        // ✅ GET all books
        get("/books", (req, res) -> {
            res.type("application/json");
            List<Book> books = library.getBooks();
            return gson.toJson(books);
        });

        // ✅ GET a single book by ID
        get("/books/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Book book = bookController.getBookById(id);
            res.type("application/json");

            if (book == null) {
                res.status(404);
                return gson.toJson("Book not found");
            }
            return gson.toJson(book);
        });

        get("/books/search/:keyword", (req, res) -> {
            String keyword = req.params(":keyword");
            List<Book> results = bookController.searchBooks(keyword.toLowerCase());
            res.type("application/json");
            return gson.toJson(results);
        });

        // ✅ POST - Add a new book manually
        post("/books", (req, res) -> {
            Book book = gson.fromJson(req.body(), Book.class);
            res.type("application/json");

            if (book == null) {
                res.status(400);
                return gson.toJson("Invalid book data");
            }

//            ; // Add through controller
//            res.status(201);
//            return gson.toJson("Book added successfully");

            boolean added = bookController.addBook(book);
            if (added) {
                res.status(201);
                return gson.toJson(Map.of(
                        "status", "success",
                        "message", "Book added successfully",
                        "book", book
                ));
            } else {
                res.status(400);
                return gson.toJson(Map.of(
                        "status", "fail",
                        "message", "Book already exists",
                        "book", book
                ));
            }
        });

        get("/books/:id/cover", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Book book = bookController.getBookById(id);
            res.type("application/json");

            if (book == null) {
                res.status(404);
                return gson.toJson("Book not found");
            }

            return gson.toJson(Map.of(
                    "id", book.getBookId(),
                    "title", book.getTitle(),
                    "coverImage", book.getCoverImageUrl()// Make sure your Book class has this field
            ));
        });

///        USER ENDPOINTS

        post("/users", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            res.type("application/json");

            if (user == null) {
                res.status(400);
                return gson.toJson("Invalid user data");
            }

            userController.addUser(user);
            res.status(201);
            return gson.toJson("User added successfully");
        });

        get("/users/:id/books", (req, res) -> {
            int userId = Integer.parseInt(req.params(":id"));
            Optional<User> userOpt = library.getUserById(userId);
            res.type("application/json");

            if (userOpt.isEmpty()) {
                res.status(404);
                return gson.toJson("User not found");
            }

            return gson.toJson(userController.getUserSavedBooks(userOpt.get()));
        });

        // Get a user’s reading history
        get("/users/:id/history", (req, res) -> {
            int userId = Integer.parseInt(req.params(":id"));
            Optional<User> userOpt = library.getUserById(userId);
            res.type("application/json");

            if (userOpt.isEmpty()) {
                res.status(404);
                return gson.toJson("User not found");
            }

            return gson.toJson(userOpt.get().getBookhistory());
        });

        delete("/users/:id", (req, res) -> {
            int userId = Integer.parseInt(req.params(":id"));
            Optional<User> userOpt = library.getUserById(userId);
            res.type("application/json");

            if (userOpt.isEmpty()) {
                res.status(404);
                return gson.toJson("User not found");
            }

            userController.removeUser(userOpt.get()); // Properly remove via controller
            res.status(200);
            return gson.toJson("User deleted successfully");
        });

        ///        LIBRARY ENDPOINTS
        get("/library", (req, res) -> {
            res.type("application/json");
            return gson.toJson("Library Name: " + library.getName() +
                    ", Total Books: " + library.getBooks().size() +
                    ", Total Users: " + library.getUsers().size());
        });

        get("/library/books", (req, res) -> {
            res.type("application/json");
            return gson.toJson(library.getBooks());
        });

        get("/library/users", (req, res) -> {
            res.type("application/json");
            return gson.toJson(library.getUsers());
        });

        get("/library/books/category/:category", (req, res) -> {
            String category = req.params(":category");
            List<Book> sameCategory = library.listBooksByCategory(category);
            res.type("application/json");
            return gson.toJson(sameCategory);
        });

        // List books by author
        get("/library/books/author/:author", (req, res) -> {
            String authorName = req.params(":author");
            List<Book> sameAuthor = library.listBooksByAuthor(authorName);
            res.type("application/json");
            return gson.toJson(sameAuthor);
        });

        ///        READER ENDPOINTS
        // --- Open a book by ID
        post("/reader/open/:bookId", (req, res) -> {
            int bookId = Integer.parseInt(req.params(":bookId"));
            var bookOpt = library.getBookById(bookId);
            res.type("application/json");
            if (bookOpt.isEmpty()) {
                res.status(404);
                return gson.toJson("Book not found");
            }
            reader.openBook(bookOpt.get());
            return gson.toJson("Opened: " + bookOpt.get().getTitle());
        });

// --- Next page
        post("/reader/next", (req, res) -> {
            reader.nextPage();
            res.type("application/json");
            return gson.toJson("Moved to next page");
        });

// --- Previous page
        post("/reader/prev", (req, res) -> {
            reader.previousPage();
            res.type("application/json");
            return gson.toJson("Moved to previous page");
        });

// --- Bookmark current page
        post("/reader/bookmark", (req, res) -> {
            reader.bookmarkPage();
            res.type("application/json");
            return gson.toJson("Page bookmarked");
        });

// --- Resume reading from bookmark
        post("/reader/resume", (req, res) -> {
            reader.resumeReading();
            res.type("application/json");
            return gson.toJson("Resumed reading from bookmark");
        });

// --- Check progress
        get("/reader/progress", (req, res) -> {
            res.type("application/json");
            // return current page / total pages
            int current = reader.getCurrentPage() + 1; // +1 to make it human-readable
            int total = reader.getPages() != null ? reader.getPages().length : 0;
            int percent = total > 0 ? (current * 100 / total) : 0;

            var progress = new java.util.HashMap<String, Object>();
            progress.put("currentPage", current);
            progress.put("totalPages", total);
            progress.put("percentage", percent);

            return gson.toJson(progress);
        });


        // 🐞 DEBUG - View raw book object
        get("/debug/book/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Book book = bookController.getBookById(id);
            res.type("application/json");

            if (book == null) {
                res.status(404);
                return gson.toJson("Book not found");
            }

            // Return the full object as JSON — everything inside the Book class
            return gson.toJson(book);
        });

        System.out.println("🚀 Library API running on http://localhost:4567");
    }
}
