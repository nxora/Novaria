package org.novaria;

public class Author {
    private final int authorId;
    private String name;
    private int age;
    private int phoneNo;
    private String email;
    User.Gender Gender;
    public User.Gender getGender() {
        return Gender;
    }

    public void setGender(User.Gender gender) {
        Gender = gender;
    }

    public Author(final int authorId, String name, int age, int phoneNo, User.Gender gender) {
        this.name = name;
        this.age = age;
        this.phoneNo = phoneNo;
        Gender = gender;
        this.authorId = authorId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "Author{" +
                "Id: " + authorId +
                "Name: '" + name + '\'' +
                ", Age: " + age +
                ", Gender: " + Gender +
                '}';
    }

}
