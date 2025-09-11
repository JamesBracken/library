package org;

import org.library.Library;
import org.user.User;
import org.utils.CsvToJsonConverter;
import org.utils.JsonReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Library myLibrary = new Library();
        Path filePath = myLibrary.getUSER_FILE_PATH();
        File file = filePath.toFile();
        if(file.length() > 0) myLibrary.initializeIDCount(List.of(JsonReader.readFromJsonFile(myLibrary.getUSER_FILE_PATH(), User[].class)));

        //INPUTTING USERS TO JSON

        //if(file.length() > 0) myLibrary.initializeIDCount(List.of(JsonReader.readFromJsonFile(myLibrary.getBOOK_FILE_PATH(), User[].class)));
//
//
//        User james = new User("James", true, LocalDate.parse("1982-07-03"));
//        User christopher = new User("Christopher", true, LocalDate.parse("1982-07-03"));

//        System.out.println("User.getIDCount: " + User.getIDCount());
//        myLibrary.registerUser(james);
//        System.out.println("User.getIDCount: " + User.getIDCount());
//        myLibrary.registerUser(christopher);
//        System.out.println("User.getIDCount: " + User.getIDCount());
//
//        System.out.println("myLibrary: " + myLibrary);
//        System.out.println("james: " + james);
//        System.out.println("User.getIDCount: " + User.getIDCount());

        //TRANSLATING BOOKS CSV TO JSON BOOKS

        System.out.println("CsvToJsonConverter TEST");
        CsvToJsonConverter.convertCsvToJson(myLibrary.getCSV_BOOK_FILE_PATH(), myLibrary.getBOOK_FILE_PATH());
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
// Add method addBook
// Add method FOR ADMINS generateBorrowedBooks
// Find a way to generate a report of borrowed books
// Maybe add method for loanBook and receiveBook/retrieveBook
// Add method handleBookLoanRequest
// Add method handleBookReturnRequest
// Add persistence methods to load users/books here

//USER
// Add properties of userId, name, isAdmin, dateOfBirth
// Add method borrowBook
// Add method returnBook
//
//
//
//
//
//
//
//