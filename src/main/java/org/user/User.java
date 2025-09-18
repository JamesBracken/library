package org.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.library.Library;
import java.time.LocalDate;

public class User {
    private static long IDCount;

    private long userID;
    private String name;
    private boolean isAdmin;
    private LocalDate dateOfBirth;

    public User() {

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

    public static void setIDCount(long IDCount) {
        User.IDCount = IDCount;
    }

    public static long getIDCount() {
        return IDCount;
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

    public void returnBook(Library library, long bookID) {
        library.handleBookReturnRequest(bookID, this.userID);
    }
}
