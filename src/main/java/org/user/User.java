package org.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.book.Book;
import org.library.Library;
import org.utils.JsonWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class User {
    private static long IDCount;

    private long userID;
    private String name;
    private boolean isAdmin;
    private LocalDate dateOfBirth;

    public static void setIDCount(long IDCount) {
        User.IDCount = IDCount;
    }

    public User() {

    }

    public static long getIDCount() {
        return IDCount;
    }

    @JsonCreator
    public User(
            @JsonProperty("userID") Long userID,
            @JsonProperty("name") String name,
            @JsonProperty("admin") boolean isAdmin,
            @JsonProperty("dateOfBirth") LocalDate dateOfBirth
    ) {
        this.userID = userID;
        this.name = name;
        this.isAdmin = isAdmin;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String name, boolean isAdmin, LocalDate dateOfBirth) {
        this.userID = IDCount;
        this.name = name;
        this.isAdmin = isAdmin;
        this.dateOfBirth = dateOfBirth;

        IDCount++;
    }

    public long getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

//    public void getUsers(Book book, Library library) {
//        library.handleBookLoanRequest(book, userID);
//    }

    @Override
    public String toString() {
        return "User{" +
                ", userID=" + userID +
                ", name='" + name + '\'' +
                ", isAdmin=" + isAdmin +
                ", dateOfBirth=" + dateOfBirth +
                ", IDCount=" + IDCount +
                '}';
    }

    public void borrowBook(Library library,long bookID) {
        library.handleBookLoanRequest(bookID, this.userID);
    }

}

// Add properties of userId, name, isAdmin, dateOfBirth FINISHED
// Add method borrowBook
// Add method returnBook