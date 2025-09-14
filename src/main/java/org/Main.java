package org;

import org.book.Book;
import org.library.Library;
import org.user.User;
import org.utils.CsvToJsonConverter;
import org.utils.JsonReader;
import org.utils.JsonWriter;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //VARIABLES TO BE USED FOR TESTING DON'T DELETE
        Library myLibrary = new Library();
        //Library users vars
        Path userFilePath = myLibrary.getUSER_FILE_PATH();
        File userFile = userFilePath.toFile();
        //Library book vars
        Path bookFilePath = myLibrary.getBOOK_FILE_PATH();
        File bookFile = bookFilePath.toFile();

        // INITIALIZE ID COUNTS, MAY NEEDTO INCORPORATE THIS INTO addNewBook AND registerUser
        if (bookFile.length() > 0)
            myLibrary.initializeIDCount(List.of(JsonReader.readFromJsonFile(bookFilePath, Book[].class)));
        if (userFile.length() > 0)
            myLibrary.initializeIDCount(List.of(JsonReader.readFromJsonFile(userFilePath, User[].class)));

//        User james = new User("James", true, LocalDate.parse("1982-07-03"));
//        myLibrary.registerUser(james);
//        User christopher = new User("Christopher", true, LocalDate.parse("1982-07-03"));
//        myLibrary.registerUser(christopher);

        //INPUTTING USERS TO JSON

        myLibrary.initializeLibraryBooksData();
        myLibrary.initializeLibraryUsersData();
        Set<User> testUserSet = myLibrary.getUsers();
        User testUser = testUserSet.stream().filter(user -> user.getUserID() == 1).findFirst().orElse(testUserSet.stream().findFirst().get());

//        --------------------------------------------------
        //TRANSLATING BOOKS CSV TO JSON BOOKS

//        System.out.println("Book.getIDCount: " + Book.getIDCount());
//        Book sampleBook = new Book(Book.getIDCount() ,"Robinson Crusoe", "Daniel Defoe", "adventure", "fiction", "Penguin Books", 0);
//        myLibrary.addNewBook(sampleBook);
//        System.out.println("getAvailableBooks: " + myLibrary.getAvailableBooks());
//        System.out.println("getBorrowedBooks: " + myLibrary.getBorrowedBooks());

        //TESTING BOOK LOAN BY USER
        testUser.borrowBook(myLibrary, 4);
        System.out.println("getBorrowedBooks: " + myLibrary.getBorrowedBooks());

    }
}

//PSEUDOCODE

// Create a java library project FINISHED
// Create 3 different packages revolving around Book, Library and User FINISHED
// Find a way to parse the CSV format into JSON and write the data to a JSON file using java FINISHED
// Find a way to write data to a JSON file using java FINISHED

//BOOK
// Add properties of number, title, author, genre, subGenre, publisher, timesBorrowed FINISHED
//

//LIBRARY
// Add properties of availableBooks, borrowedBooks < k, v > < user, Set<book>>, books FINISHED
// Add method registerUser FINISHED
// Find a way to make write users to a JSON file FINISHED
// Populate library books list using database FINISHED
// Add method addBook FINISHED
// Populate library users list using database FINISHED
// Add method handleBookLoanRequest FINISHED
// Add method handleBookReturnRequest
// Add persistence methods to load users/books here FINISHED
// Find a way to generate a report of borrowed books
// Add method FOR ADMINS generateBorrowedBooks

//USER
// Add properties of userId, name, isAdmin, dateOfBirth FINISHED
// Add method borrowBook FINISHED
// Add method returnBook

//LIBRARY SERVICE // menu type -- options
//Landing menu - Login, Register
//Main menu - Display library books, Display available books, Borrow a book
//ADMIN Main menu - Display library books, Display available books, Borrow a book, Run report
//
//
//
//
//
//
//
//Login
//Register
//Display library books
//Display available library books
//Borrow a book
//Run report -- preferably in CSV


//POSSIBLE BUGS, TESTING, CHECKS, FIXES NEEDED
// Test if database can hold duplicate books (Specifically id)
// Ensure library data is re-initialised when database is updated


//OPTIONAL EXTENSIONS
//Find book by genre, title, author, publisher,
//Book to be returned within a specific timeframe decided by the library
//Book borrow extension if book loan timeframe is implemented