package org.example.book;

public class Book {
    private int number;
    private String title;
    private String author;
    private String genre;
    private String subGenre;
    private String publisher;
    private int timesBorrowed;

    public Book(int number, String title, String author, String genre, String subGenre, String publisher, int timesBorrowed) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.subGenre = subGenre;
        this.publisher = publisher;
        this.timesBorrowed = timesBorrowed;
    }

    public void setTimesBorrowed(int timesBorrowed) {
        this.timesBorrowed = timesBorrowed;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getSubGenre() {
        return subGenre;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getTimesBorrowed() {
        return timesBorrowed;
    }


}


// Add properties of number, title, author, genre, subGenre, publisher, timesBorrowed
