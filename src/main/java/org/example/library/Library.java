package org.example.library;

import org.example.book.Book;
import org.example.user.User;

import java.util.*;

public class Library {

    private Set<Book> books = new HashSet<>();
    private Set<Book> availableBooks = new HashSet<>();
    private Map<User, Set<Book>> borrowedBooks = new HashMap<>();

    //May not need this or might have to change it
    public Library(Set<Book> books, Set<Book> availableBooks, Map<User, Set<Book>> borrowedBooks) {
        this.books = books;
        this.availableBooks = availableBooks;
        this.borrowedBooks = borrowedBooks;
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
}


// Add properties of availableBooks, borrowedBooks < k, v > < user, Set<book>>, books FINISHED
// Add method registerUser
// Find a way to make write users to a JSON file
// Add method addBook
// Add method FOR ADMINS generateBorrowedBooks
// Find a way to generate a report of borrowed books
// Maybe add method for loanBook and receiveBook/retrieveBook
// Add method handleBookLoanRequest
// Add method handleBookReturnRequest
// Add persistence methods to load users/books here