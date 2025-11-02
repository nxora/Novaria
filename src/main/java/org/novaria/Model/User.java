package org.novaria.Model;

 import java.util.ArrayList;
 import java.util.List;
import org.bson.Document;

public class User {
  private final int userId ;
  private String name;
  private int age;
  private String email;
  private String password;
  private String favGenre;
  private String profilePic;

    Gender gender;
    private List<Book> savedbookList = new ArrayList<>();
    private Library library;
    private List<Book> bookhistory = new ArrayList<>();


    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public User(final int userId, String name, int age, Gender gender,String email,String password, Library library) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.password = password;
        this.library = library;
        this.email = email;
        this.favGenre = favGenre;
        this.profilePic = profilePic;
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

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isAvailableOffline(Book book) {
        return this.getMyLibraryBooks().contains(book);
    }

    public int getAge() {
        return age;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFavGenre() {
        return favGenre;
    }
    public String getProfilePic() {
        return profilePic;
    }
    public void joinLibrary() {
        if (library == null) {
            System.out.println("No library selected.");
            return;
        }
        if (library.registerUser(this)) {
            System.out.println(name + " successfully joined " + library.getName());
        } else {
            System.out.println("Registration failed or user already exists.");
        }
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFavGenre(String favGenre) {
        this.favGenre = favGenre;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
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

    public void leaveLibrary(){
        library.getUsers().remove(this);
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

    public boolean hasBook(Book book){
        if (savedbookList.contains(book)){
            return  true;
        }
        return false;
    }

    public Book viewLibraryBooks(){
        for (Book book: savedbookList){
            return book;
        }
        System.out.println("Save books to view saved books");
        return null;
    }

    public List<Book> searchMyBook(String keyword) {
        List<Book> results = new ArrayList<>();
        for (Book book : savedbookList) {
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

    public void clearSavedBooks(){
        this.savedbookList.clear();
    }

    public void viewProfileSummary() {
        System.out.println("=== Profile Summary ===");
        System.out.println("Name: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("Library: " + (library != null ? library.getName() : "Not joined"));
        System.out.println("Saved Books: " + savedbookList.size());
        System.out.println("Books Read (History): " + bookhistory.size());
    }

    @Override
    public String toString() {
        return "User{" +
                "Id: "+ userId +
                ", Name: '" + name + '\'' +
                 ", Gender: " + gender +
                ", Books Borrowed: " + savedbookList.size() +
                '}';
    }


    public Document toDocument() {
        return new Document("userId", userId)
                .append("name", name)
                .append("age", age)
                .append("email", email)
                .append("password", password)
                .append("favGenre", favGenre)
                .append("gender", gender.toString())
                .append("profilePic", profilePic);
        // you can add savedbookList later
    }

    public static User fromDocument(Document doc) {
        int id = doc.getInteger("userId");
        String name = doc.getString("name");
        int age = doc.getInteger("age");
        String email = doc.getString("email");
        String password = doc.getString("password");
        String favGenre = doc.getString("favGenre");
        String profilePic = doc.getString("profilePic");
        Gender gender = Gender.valueOf(doc.getString("gender"));

        Library lib = null; // can be loaded later

        return new User(id, name, age, gender,email , password, lib);
    }


}
