package org.library;

import org.book.Book;
import org.user.User;
import org.utils.JsonWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Library {
    private final Path USER_FILE_PATH = Paths.get("data", "users.json");

    public Path getUSER_FILE_PATH() {
        return USER_FILE_PATH;
    }

    private Set<Book> books = new HashSet<>();
    private Set<Book> availableBooks = new HashSet<>();
    private Map<User, Set<Book>> borrowedBooks = new HashMap<>();
    private Set<User> users = new HashSet<>();

    //May not need this or might have to change it

    public Library() {
    }


    public Set<Book> getBooks() {
        return books;
    }

    public Set<Book> getAvailableBooks() {
        return availableBooks;
    }

    public Map<User, Set<Book>> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void handleBookLoanRequest(Book book, long userID) {
        // Fill up later
        System.out.println("handleBookLoanRequest UNFINISHED, POPULATE CODE");
    }

    public Set<User> getUsers() {
        return users;
    }

    public void registerUser(User user) {
        users.add(user);
        borrowedBooks.put(user, new HashSet<>());
        JsonWriter.appendToJsonFile(user, USER_FILE_PATH, User[].class);
    }


    @Override
    public String toString() {
        return "Library{" +
                "books=" + books +
                ", availableBooks=" + availableBooks +
                ", borrowedBooks=" + borrowedBooks +
                ", users=" + users +
                '}';
    }

    public void initializeIDCount(List<User> users) {
        long maxID = users.stream().mapToLong(User::getUserID).max().orElse(-1);
        User.setIDCount(maxID + 1);
    }
}


// Add properties of availableBooks, borrowedBooks < k, v > < user, Set<book>>, books FINISHED
// Add method registerUser FINISHED
// Find a way to make write users to a JSON file
// Add method addBook
// Add method FOR ADMINS generateBorrowedBooks
// Find a way to generate a report of borrowed books
// Maybe add method for loanBook and receiveBook/retrieveBook
// Add method handleBookLoanRequest
// Add method handleBookReturnRequest
// Add persistence methods to load users/books here