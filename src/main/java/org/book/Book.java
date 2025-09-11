package org.book;

public class Book {
    private long number;
    private String title;
    private String author;
    private String genre;
    private String subGenre;
    private String publisher;
    private long timesBorrowed;

    public Book(long number, String title, String author, String genre, String subGenre, String publisher, long timesBorrowed) {
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

    public long getNumber() {
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

    public long getTimesBorrowed() {
        return timesBorrowed;
    }

    @Override
    public String toString() {
        return "Book{" +
                "number=" + number +
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
