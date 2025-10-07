package org.novaria;

public class Book {
    String title;
    Author author;
    Category category;
    String description;
     final int bookId;
     private boolean available = true;

    public enum Category {
        FANTASY, WAR, MYSTERY, ROMANCE, SCIENCE_FICTION, BIOGRAPHY, HISTORY, ADVENTURE
    }

    public Book(String title, Author author, Category category, String description, final int bookId) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.bookId = bookId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailableOffline(User user) {
        return user.getMyLibraryBooks().contains(this) ? true : false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book other = (Book) o;
        return this.title.equalsIgnoreCase(other.title)
                && this.author.getName().equalsIgnoreCase(other.author.getName());
    }

    @Override
    public int hashCode() {
        return (title + author.getName()).toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "Book{" +
                "Id: " + bookId +
                ", Title: '" + title + '\'' +
                ", author: " + author.getName() +
                ", category: " + category +
                '}';
    }


}
