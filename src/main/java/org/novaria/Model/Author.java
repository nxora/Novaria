package org.novaria.Model;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private final int authorId;
    private String name;
    private int age;
    private String email;
    private String phoneNo;
    private User.Gender gender;
    private final List<Book> works = new ArrayList<>();

    public Author(int authorId, String name, int age, String email, String phoneNo, User.Gender gender) {
        this.authorId = authorId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
    }

    public int getAuthorId() { return authorId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    public User.Gender getGender() { return gender; }
    public void setGender(User.Gender gender) { this.gender = gender; }

    public List<Book> getWorks() { return new ArrayList<>(works); }
    public void addWork(Book book) { if (!works.contains(book)) works.add(book); }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + authorId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
