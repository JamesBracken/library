package org.example.user;

import java.time.LocalDate;

public class User {

    private int userId;
    private String name;
    private boolean isAdmin;
    private LocalDate dateOfBirth;

    public User(int userId, String name, boolean isAdmin, LocalDate dateOfBirth) {
        this.userId = userId;
        this.name = name;
        this.isAdmin = isAdmin;
        this.dateOfBirth = dateOfBirth;
    }

    public int getUserId() {
        return userId;
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

}



// Add properties of userId, name, isAdmin, dateOfBirth
// Add method borrowBook
// Add method returnBook