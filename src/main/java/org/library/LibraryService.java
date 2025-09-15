package org.library;

import java.time.LocalDate;
import java.util.Scanner;

public class LibraryService {

    private final Scanner scanner = new Scanner(System.in);
    private Library library;

    public void initializeLibraryApplication(Library chosenLibrary) {
        //Add initialization logic such as setting up user and books lists with data
        library = chosenLibrary;
        library.initializeLibraryBooksData();
        library.initializeLibraryUsersData();
        displayStartMenu();
    }


    public void displayStartMenu() {
        System.out.println(
                """
                        Welcome to the library, to use our services you must have an account and login.
                        How may we be of service?
                        1) Login to my account
                        2) Register for an account
                        3) Exit program
                        """);
        handleStartMenuOptions();
    }

    public void handleStartMenuOptions() {
        boolean isHandlerActive = true;
        String selectedMenuItem = scanner.nextLine().trim();
        while (isHandlerActive) {
            switch (selectedMenuItem) {
                case "1" -> promptUserLogin();
                case "2" -> displayRegistration();
                case "3" -> displayExitApplication();
            }
        }
    }


}


//LIBRARY SERVICE // menu type -- options
//Landing menu - Login, Register
//Main menu - Display library books, Display available books, Borrow a book
//ADMIN Main menu - Display library books, Display available books, Borrow a book, Run report
//
//
//Login
//Register
//Display library books
//Display available library books
//Borrow a book
//Run report -- preferably in CSV