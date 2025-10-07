//package org.novaria;
//
//import java.util.Observable;
//
//public class Librarian {
//    final int librarianId;
//    String name;
//    int age;
//    int phoneNo;
//    Gender gender;
//
//
//    public Librarian(final int librarianId, String name, int age, int phoneNo,  Gender gender) {
//        this.librarianId = librarianId;
//        this.name = name;
//        this.age = age;
//        this.phoneNo = phoneNo;
//        this.gender = gender;
//    }
//
//
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public int getPhoneNo() {
//        return phoneNo;
//    }
//
//    public void setPhoneNo(int phoneNo) {
//        this.phoneNo = phoneNo;
//    }
//
//    public int getLibrarianId() {
//        return librarianId;
//    }
//
//    public Gender getGender() {
//        return gender;
//    }
//
//    public void setGender(Gender gender) {
//        this.gender = gender;
//    }
//
//    public void addBookTolibrary(Book book, Library library){
//        if (library.getBooksList().contains(book)){
//            System.out.println("We already have this book in stock");
//            System.out.println("But thanks for the thought");
//        }else {
//            library.addBook(book);
//        }
//    }
//
//    public void removeBookFromLibrary(Book book, Library library){
//        if (library.getBooksList().contains(book)){
//            library.removeBook(book);
//        }else {
//            System.out.println("We already have this book in stock");
//            System.out.println("But thanks for the thought");
//
//        }
//    }
//
//    @Override
//    public String toString(){
//        return "Librarian{" +
//                "Id: " + librarianId +
//                "Name: '" + name + '\'' +
//                ", Age: " + age +
//                ", phone No: " + phoneNo +
//                ", Gender: " + gender +
//                '}';
//    }
//}
