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

    public void promptUserLogin() {
        System.out.println("Kindly enter your user ID to proceed");
        handleUserLogin();
    }

    public void handleUserLogin() {
        boolean isHandlerActive = true;
        boolean isValidID = false;
        while (isHandlerActive) {
            String userInputID = scanner.nextLine().trim();
            try {
                isValidID = library.getUsers().stream().anyMatch(user -> user.getUserID() == Integer.parseInt(userInputID));
            } catch (NumberFormatException e) {
                System.out.println();
            } finally {
                System.out.println("isValidID: " + isValidID);
                if (isValidID) {
                    isHandlerActive = true;
                    System.out.println("Valid id input");
                    System.out.println("Must add display next menu");
                    // DISPLAY NEXT MENU
                }
            }
        }
    }

    public void displayRegistration() {
        System.out.println("To register with us kindly read through the displayed instructions and input your information");
        handleRegistration();
    }

    public void handleRegistration() {
        boolean isHandlerActive = true;
        boolean isValidID = false;
        long userID;
        boolean isAdminInput = promptUserIsAdmin();
        String nameInput = promptUserForName();
        LocalDate dateOfBirthInput;

//        while (isHandlerActive) {
//            String userInputID = scanner.nextLine().trim();
//            try {
//            }
//        }
    }

    // Can improve security by adding a limit on password attempts, maybe exit application after
    public boolean promptUserIsAdmin() {
        System.out.println("Should this user be set up with admin rights? y/n");
        while (true) {
            String shouldUserBeAdmin = scanner.nextLine().trim().toLowerCase();
            if (shouldUserBeAdmin.equals("y")) {
                String masterPassword = "12345"; // Temporarily placing here, to be moved to ENVIRONMENTAL VARIABLES file
                while (true) {
                    System.out.println("To register this user as an admin kindly input the library master password: ");
                    String userInputMasterPassword = scanner.nextLine().trim().toLowerCase();
                    if (!userInputMasterPassword.equals(masterPassword)) {
                        System.out.println("Incorrect master password");
                        while (true) {
                            System.out.println("Would you like to re-attempt to input the password? Please input 'y' or 'n'");
                            String userInputShouldContinue = scanner.nextLine().trim().toLowerCase();
                            if (userInputShouldContinue.equals("y")) {

                                break;
                            } else if (userInputShouldContinue.equals("n")) {
                                return false;
                            } else {
                                System.out.println("That is not a valid option, please input 'y' or 'n' ");
                            }
                        }
                        continue;
                    }
                    System.out.println("Master password successfully input");
                    return true;
                }
            } else if (shouldUserBeAdmin.equals("n")) {
                return false;
            } else {
                System.out.println("You must either input the letter 'y' or 'n'");
            }
        }
    }

    public String promptUserForName() {
        System.out.println("Please input your name");
        String userInputName;
        while (true) {
            try {
                userInputName = scanner.nextLine().trim();
                if (!userInputName.matches("^[\\p{L}\\-']+$")) {
                    System.out.println("Please input a valid name with only alphabetical characters");
                    continue;
                }
                return userInputName;
            } catch (RuntimeException e) {
                System.out.println("Invalid name input. Exception: " + e);
            }
        }
    }

    public void displayExitApplication() {

    }

    public void handleExitApplication() {

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