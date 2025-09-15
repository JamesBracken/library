package org.library;

import org.book.Book;
import org.user.User;
import org.utils.JsonReader;
import org.utils.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.utils.JsonWriter.mapper;


public class Library {
    private final Path USER_FILE_PATH = Paths.get("data", "users.json");
    private final Path BOOK_FILE_PATH = Paths.get("data", "books.json");
    private final Path CSV_BOOK_FILE_PATH = Paths.get("data", "books_data.csv");


    private Set<Book> books = new HashSet<>();
    private Set<Book> availableBooks = new HashSet<>();
    private Map<User, Set<Book>> borrowedBooks = new HashMap<>();
    private Set<User> users = new HashSet<>();

//May not need this or might have to change it

    public Library() {
    }

    public void setAvailableBooks(Set<Book> availableBooks) {
        this.availableBooks = availableBooks;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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

    public Path getCSV_BOOK_FILE_PATH() {
        return CSV_BOOK_FILE_PATH;
    }

    public Path getBOOK_FILE_PATH() {
        return BOOK_FILE_PATH;
    }

    public Path getUSER_FILE_PATH() {
        return USER_FILE_PATH;
    }

    public Set<User> getUsers() {
        return this.users;
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

    public <T> void initializeIDCount(List<T> items) {
        if (items.isEmpty()) return;

        Object firstItem = items.getFirst();

        if (firstItem instanceof User) {
            @SuppressWarnings("unchecked")
            List<User> users = (List<User>) items;

            long maxID = users.stream().mapToLong(User::getUserID).max().orElse(-1);
            User.setIDCount(maxID + 1);
        } else if (firstItem instanceof Book) {
            @SuppressWarnings("unchecked")
            List<Book> books = (List<Book>) items;

            long maxID = books.stream().mapToLong(Book::getBookID).max().orElse(-1);
            Book.setIDCount(maxID + 1);
        } else {
            System.out.println("The object: " + items + " is not currently being handled in initializeIDCount");
        }
    }

    //Populate library books list using database
    public void initializeLibraryBooksData() {
        try {
            File file = BOOK_FILE_PATH.toFile();
            System.out.println(Arrays.toString(JsonReader.readFromJsonFile(BOOK_FILE_PATH, Book[].class)));
            Book[] existingBookData = mapper.readValue(file, Book[].class);
            System.out.println("existingBookData: " + Arrays.toString(existingBookData));
            this.setAvailableBooks(new HashSet<>(Arrays.asList(existingBookData)));
            this.setBooks(new HashSet<>(Arrays.asList(existingBookData)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize library books data from: " + BOOK_FILE_PATH + " Exception: " + e);
        }
    }

    //Populate library users list using database
    public void initializeLibraryUsersData() {
        try {
            File file = USER_FILE_PATH.toFile();
            System.out.println(Arrays.toString(JsonReader.readFromJsonFile(USER_FILE_PATH, User[].class)));
            User[] existingUserData = mapper.readValue(file, User[].class);
            System.out.println("existingBookData: " + Arrays.toString(existingUserData));
            this.setUsers(new HashSet<>(Arrays.asList(existingUserData)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize library users data from: " + USER_FILE_PATH + " Exception: " + e);
        }
    }

    //Add book method
    public void addNewBook(Book book) {
        books.add(book);
        availableBooks.add(book);
        JsonWriter.appendToJsonFile(book, BOOK_FILE_PATH, Book[].class);
        //Adjust the IDCount for book
        this.initializeIDCount(List.of(JsonReader.readFromJsonFile(BOOK_FILE_PATH, Book[].class)));
    }

    public void handleBookLoanRequest(long bookID, long userID) {
        Book book = getAvailableBooks().stream().filter(item -> item.getBookID() == bookID).findFirst().orElse(null);
        User user = getUserByID(userID);
        if (user == null) throw new IllegalArgumentException("The user with ID: " + userID + " cannot be found");
        if (book == null) throw new IllegalArgumentException("The book with ID: " + bookID + " cannot be found");
        Set<Book> userBorrowedBookSet = getBorrowedBooks().get(user);
        if (userBorrowedBookSet == null) userBorrowedBookSet = new HashSet<>();
        userBorrowedBookSet.add(book);
        getBorrowedBooks().put(user, userBorrowedBookSet);
        getAvailableBooks().remove(book);
        updateBookTimesBorrowed(book);
    }

    public void handleBookReturnRequest(long bookID, long userID) {
        try {
            User user = getUserByID(userID);
            if (user == null) throw new IllegalArgumentException("The user with ID: " + userID + " cannot be found");
            Set<Book> userBorrowedBookSet = getBorrowedBooks().get(user);
            if (userBorrowedBookSet == null) throw new RuntimeException("You may not attempt to return a book if you have not borrowed any books");
            Book book = userBorrowedBookSet.stream().filter(currentBook -> currentBook.getBookID() == bookID).findFirst().orElse(null);
            if (book == null) throw new IllegalArgumentException("The book with ID: " + bookID + " cannot be found");

            getAvailableBooks().add(book);
            userBorrowedBookSet.remove(book);
        } catch (NullPointerException e) {
            throw new RuntimeException("You may not return the book with ID: " + bookID + " as you did not borrow this");
        }
    }

    public User getUserByID(long ID) {
        return getUsers().stream().filter(user -> user.getUserID() == ID).findFirst().orElse(null);
    }

    public void updateBookTimesBorrowed(Book book) {
        book.setTimesBorrowed(book.getTimesBorrowed() + 1);
        JsonWriter.updateInJsonFile(book, getBOOK_FILE_PATH(), Book[].class, Book::getBookID);
    }

}

// Add properties of availableBooks, borrowedBooks < k, v > < user, Set<book>>, books FINISHED
// Add method registerUser FINISHED
// Find a way to make write users to a JSON file FINISHED
// Populate library books list using database FINISHED
// Add method addBook FINISHED
// Populate library users list using database FINISHED
// Add persistence methods to load users/books here FINISHED
// Add method handleBookLoanRequest FINISHED
// Add method handleBookReturnRequest FINISHED
// Find a way to generate a report of borrowed books
// Add method FOR ADMINS


