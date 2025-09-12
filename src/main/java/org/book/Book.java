package org.book;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Book {
    private long bookID;
    private String title;
    private String author;
    private String genre;
    private String subGenre;
    private String publisher;
    private long timesBorrowed;
    private static long IDCount;

    @JsonCreator
    public Book(
            @JsonProperty("bookID") Long bookID,
            @JsonProperty("title") String title,
            @JsonProperty("author") String author,
            @JsonProperty("genre") String genre,
            @JsonProperty("subGenre") String subGenre,
            @JsonProperty("publisher") String publisher,
            @JsonProperty("timesBorrowed") long timesBorrowed
    ) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.subGenre = subGenre;
        this.publisher = publisher;
        this.timesBorrowed = timesBorrowed;
    }

    public Book(long bookID, String title, String author, String genre, String subGenre, String publisher, long timesBorrowed) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.subGenre = subGenre;
        this.publisher = publisher;
        this.timesBorrowed = timesBorrowed;

        IDCount++;
    }

    public static void setIDCount(long IDCount) {
        Book.IDCount = IDCount;
    }

    public static long getIDCount() {
        return IDCount;
    }

    public void setTimesBorrowed(int timesBorrowed) {
        this.timesBorrowed = timesBorrowed;
    }

    public long getBookID() {
        return bookID;
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

    public long getTimesBorrowed() {
        return timesBorrowed;
    }

    @Override
    public String toString() {
        return "Book{" +
                "number=" + bookID +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", subGenre='" + subGenre + '\'' +
                ", publisher='" + publisher + '\'' +
                ", timesBorrowed=" + timesBorrowed +
                '}';
    }
}


// Add properties of number, title, author, genre, subGenre, publisher, timesBorrowed FINISHED
