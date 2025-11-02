package org.novaria.controller;

import org.novaria.Model.Book;
import org.novaria.Model.Library;
import org.novaria.Model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {
   private final Library library;
   private final List<User> users;
    private final Map<Integer, User> userMap = new HashMap<>();

    public UserController(Library library, List<User> users) {
        this.library = library;
        this.users = users;
    }

    public List<User> getAllUsers(){
         return library.getUsers();
    }


    public void addUser(User user) {
        if (user == null) return;
        if (userMap.containsKey(user.getUserId())) {
            System.out.println(user.getName() + " already exists!");
            return;
        }
        users.add(user);
        userMap.put(user.getUserId(), user);
        System.out.println("Welcome, " + user.getName() + "!");
    }

    public User getUserById(int id) {
        return userMap.get(id);
    }

    public List<Book> getUserSavedBooks(User user){
        return user.getSavedbookList();
    }
    public void removeUser(User user) {
        if (users.remove(user)) {
            System.out.println(user.getName() + " has been removed.");
        } else {
            System.out.println("User not found!");
        }
    }

    public void viewProfile(User user){
        user.viewProfileSummary();
    }
    void fetchUserDataFromApi(String endpoint){

    }
    void syncUserLibrary(User user){}

}
