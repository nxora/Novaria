package org.novaria.Model;

public class Book {
    private String title;
    private Author author;
    private String category;
    private String description;
    private final int bookId;
    private String textUrl;
     private String coverImageUrl;

    public Book(String title, Author author, String category, String description, final int bookId) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.bookId = bookId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTextUrl() {
        return textUrl;
    }

    public void setTextUrl(String textUrl) {
        this.textUrl = textUrl;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String shortDescription() {
        if (description == null || description.isEmpty()) {
            return "No description";
        }
        return description.length() > 60
                ? description.substring(0, 57) + "..."
                : description;
    }

    public boolean matches(String keyword){
                if (this.getTitle().toLowerCase().contains(keyword)
                        || this.getAuthor().getName().toLowerCase().contains(keyword)
                        || this.getCategory().toString().toLowerCase().contains(keyword)) {
                    return true;
                }
            return false;
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
