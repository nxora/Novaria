package org.novaria;

import java.util.ArrayList;
import java.util.List;

public class User {
  private final int userId ;
  private String Name;

    Gender gender;
    private List<Book> savedbookList = new ArrayList<>();
    private Library library;
    private List<Book> bookhistory = new ArrayList<>();


    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public User(final int userId, String name, int age, Gender gender, Library library) {
        this.userId = userId;
        Name = name;

        this.gender = gender;
        this.library = library;
    }

    public boolean setSavedbookList(List<Book> savedbookList) {
        if (savedbookList == null || savedbookList.isEmpty()) {
            System.out.println("No books to add.");
            return false;
        }
        this.savedbookList = savedbookList;
        System.out.println(savedbookList.size() + " books have been added to your library");
        return true;
    }
    public List<Book> getSavedbookList() {
        return savedbookList;
    }

    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Library getLibrary() {
        return library;
    }
    public void setLibrary(Library library) {
        this.library = library;
    }


    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public void joinLibrary() {
        if (library == null) {
            System.out.println("No library selected.");
            return;
        }
        if (library.registerUser(this)) {
            System.out.println(Name + " successfully joined " + library.getName());
        } else {
            System.out.println("Registration failed or user already exists.");
        }
    }

    public List<Book> getBookhistory() {
        return bookhistory;
    }

    public void setBookhistory(List<Book> bookhistory) {
        this.bookhistory = bookhistory;
    }
    public List<Book> getMyLibraryBooks(){
            return savedbookList;
        }

    public boolean saveToLibrary(Book book){
        if (!savedbookList.contains(book)) {
            savedbookList.add(book);
            return true;
        }
         return false;
    }

    public void removeFromLibrary(Book book){
        if (savedbookList.contains(book)){
            savedbookList.remove(book);
            System.out.println(book.getTitle() + ", successfully removed.");
        }else {
            System.out.println("Doesn't exists in your Saved Library");
        }
    }

    public void addToHistory(Book book) {
        if (!bookhistory.contains(book)) {
            bookhistory.add(book);
        }
    }
    public List<Book> listReadingHistory(){
        return bookhistory;
    }

    boolean hasBook(Book book){
        if (savedbookList.contains(book)){
            return  true;
        }
        return false;
    }
    //    public void createGroup(String name, Group group){
//        String regex = "^[a-zA-Z0-9]+$";
//        if (group.getGroupList().contains(group)){
//            System.out.println(this.getName() + " already exists.");
//            group.setAdmin(this);
//        }else if (!name.matches(regex)){
//            System.out.println(name + ", must strictly consists of alphabets or digits");
//        }
//    }

    @Override
    public String toString() {
        return "User{" +
                "Id: "+ userId +
                ", Name: '" + Name + '\'' +
                 ", Gender: " + gender +
                ", Books Borrowed: " + savedbookList.size() +
                '}';
    }

}
