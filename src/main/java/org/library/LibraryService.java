package org.library;

import io.github.cdimascio.dotenv.Dotenv;
import org.book.Book;
import org.user.User;
import org.utils.CsvWriter;
import org.utils.JsonReader;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class LibraryService {

    private final Scanner scanner = new Scanner(System.in);
    private final Dotenv dotenv = Dotenv.configure().filename("vars.env").load();
    private final String MASTER_PASSWORD = dotenv.get("MASTER_PASSWORD");

    private Library library = new Library();
    private User loggedInUser;

    public void initializeLibraryApplication(Library chosenLibrary) {
        Path userFilePath = library.getUSER_FILE_PATH();
        File userFile = userFilePath.toFile();
        Path bookFilePath = library.getBOOK_FILE_PATH();
        File bookFile = bookFilePath.toFile();

        if (bookFile.length() > 0)
            library.initializeIDCount(List.of(JsonReader.readFromJsonFile(bookFilePath, Book[].class)));
        if (userFile.length() > 0)
            library.initializeIDCount(List.of(JsonReader.readFromJsonFile(userFilePath, User[].class)));
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
        while (isHandlerActive) {
            int selectedMenuItem;
            try {
                selectedMenuItem = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("You must input a valid number, Exception: " + e);
                continue;
            }
            switch (selectedMenuItem) {
                case 1 -> {
                    isHandlerActive = false;
                    promptUserLogin();
                }
                case 2 -> {
                    isHandlerActive = false;
                    displayRegistration();
                }
                case 3 -> handleExitApplication();
                default -> System.out.println(selectedMenuItem + " is not a valid option");
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
                if (isValidID) {
                    isHandlerActive = false;
                    loggedInUser = library.getUserByID(Long.parseLong(userInputID));
                    System.out.println("""
                            \s
                            You have successfully logged in""");
                    displayLoggedInUserMenuOptions();
                } else {
                    System.out.println(userInputID + " is an invalid ID");
                }
            }
        }
    }

    public void displayRegistration() {
        System.out.println("To register with us kindly read through the displayed instructions and input your information");
        handleRegistration();
    }

    public void handleRegistration() {
        String nameInput = promptUserForName();
        LocalDate dateOfBirthInput = promptUserDateOfBirth();
        boolean isAdminInput = promptUserIsAdmin();
        User newUser = new User(nameInput, isAdminInput, dateOfBirthInput);
        System.out.printf("""
                
                You have successfully registered for a library account.
                
                Your user ID is: %d%n\s
                You will now be returned to the main menu\s
                """, newUser.getUserID());
        library.registerUser(newUser);
        displayStartMenu();
    }

    public LocalDate promptUserDateOfBirth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.println("Please input your date of birth in the format of dd/mm/yyyy ");
            String userInputDateOfBirth = scanner.nextLine().trim();
            try {
                LocalDate dateOfBirth = LocalDate.parse(userInputDateOfBirth, formatter);
                System.out.println("You have successfully input your date of birth");
                return dateOfBirth;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again");
            }
        }
    }

    public boolean promptUserIsAdmin() {
        // true booleans used in this method instead of boolean flags because loops are exited
        // through returns and breaks
        System.out.println("Should this user be set up with admin rights? y/n");
        while (true) {
            String shouldUserBeAdmin = scanner.nextLine().trim().toLowerCase();
            if (shouldUserBeAdmin.equals("y")) {
                while (true) {
                    System.out.println("To register this user as an admin kindly input the library master password: ");
                    String userInputMasterPassword = scanner.nextLine().trim().toLowerCase();
                    if (!userInputMasterPassword.equals(MASTER_PASSWORD)) {
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
                    System.out.println("\n Master password successfully input ");
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

    public void displayLoggedInUserMenuOptions() {
        if (!loggedInUser.isAdmin()) {
            System.out.println("""
                    
                    Please choose an option
                    1) Display all library books
                    2) Display currently available books
                    3) Borrow a book
                    4) Return a book
                    9) Logout
                    """);
        } else {
            System.out.println("""
                    
                    Please choose an option
                    1) Display all library books
                    2) Display currently available books
                    3) Borrow a book
                    4) Return a book
                    5) Create a report of all currently borrowed books
                    6) Create a report for an individual specified book
                    9) Logout
                    """);
        }
        handleLoggedInUserMenuOptions();
    }

    public void handleLoggedInUserMenuOptions() {
        String userInput;
        boolean isHandlerActive = true;
        if (!loggedInUser.isAdmin()) {
            while (isHandlerActive) {
                userInput = scanner.nextLine().trim();
                switch (userInput) {
                    case "1" -> {
                        isHandlerActive = false;
                        displayAllLibraryBooks();
                        displayLoggedInUserMenuOptions();
                    }
                    case "2" -> {
                        isHandlerActive = false;
                        displayAvailableLibraryBooks();
                        displayLoggedInUserMenuOptions();
                    }
                    case "3" -> {
                        isHandlerActive = false;
                        handleBorrowBook();
                    }
                    case "4" -> {
                        isHandlerActive = false;
                        handleReturnBook();
                    }
                    case "9" -> {
                        isHandlerActive = false;
                        handleUserLogout();
                    }
                    default -> System.out.println("\n" + userInput + " is not a valid option ");
                }
            }
        } else {
            while (isHandlerActive) {
                userInput = scanner.nextLine().trim();
                switch (userInput) {
                    case "1" -> {
                        isHandlerActive = false;
                        displayAllLibraryBooks();
                        displayLoggedInUserMenuOptions();
                    }
                    case "2" -> {
                        isHandlerActive = false;
                        displayAvailableLibraryBooks();
                        displayLoggedInUserMenuOptions();
                    }
                    case "3" -> {
                        isHandlerActive = false;
                        handleBorrowBook();
                    }
                    case "4" -> {
                        isHandlerActive = false;
                        handleReturnBook();
                    }
                    case "5" -> {
                        isHandlerActive = false;
                        produceAllBorrowedBooksReport();
                    }
                    case "6" -> {
                        isHandlerActive = false;
                        produceSpecifiedBorrowedBookReport();
                    }
                    case "9" -> {
                        isHandlerActive = false;
                        handleUserLogout();
                    }
                    default -> System.out.println("\n" + userInput + " is not a valid option ");
                }
            }
        }
    }

    public void produceSpecifiedBorrowedBookReport() {
        boolean isHandlerActive = true;
        String[] header = {"Report of the specified book from this library", "\n \nTitle ", "Author ", "Genre ", "Sub Genre ", "Publisher ", " Times Borrowed"};
        System.out.println("Please input the ID of the book you would like to produce a report for, you can input -1 to view all our books and -9 to go to the previous menu");
        long userInputBookID;
        while (isHandlerActive) {
            try {
                userInputBookID = Long.parseLong(scanner.nextLine().trim());
                Book selectedBook = library.getBookByID(userInputBookID);
                if (userInputBookID == -1) {
                    displayAllLibraryBooks();
                    System.out.println("\nPlease input the ID of the book you would like to produce a report for, you can input -1 to view all our books and -9 to go to the previous menu");
                    continue;
                }
                if (userInputBookID == -9) {
                    isHandlerActive = false;
                    displayLoggedInUserMenuOptions();
                    break;
                }
                if (selectedBook == null) {
                    System.out.println("There is no book with the ID: " + userInputBookID);
                    continue;
                }
                List<List<String>> borrowedBooksList = library.getBooks().stream().filter(book -> book.getBookID() == selectedBook.getBookID())
                        .map(book -> Arrays.asList(
                                String.valueOf(book.getBookID()),
                                book.getTitle(),
                                book.getAuthor(),
                                book.getGenre(),
                                book.getSubGenre(),
                                book.getPublisher(),
                                String.valueOf(book.getTimesBorrowed()))
                        ).toList();
                CsvWriter.writeIndividualItemToCsvFile(borrowedBooksList, "data/individual_book.csv", header);
                System.out.println("\n Report successfully produced");
                displayLoggedInUserMenuOptions();
            } catch (NumberFormatException e) {
                System.out.println("Your input is not a valid, Exception: " + e);
            }
        }
    }

    public void produceAllBorrowedBooksReport() {
        if (loggedInUser == null) return;
        if (!loggedInUser.isAdmin()) {
            System.out.println("You must be an admin to run reports");
            return;
        }
        String[] header = {"Report of all books currently borrowed from this library", "\n \nTitle ", "Author ", "Genre ", "Sub Genre ", "Publisher ", " Times Borrowed"};
        List<List<String>> borrowedBooksList = library.getBorrowedBooks().values().stream().flatMap(Set::stream).sorted(Comparator.comparing(Book::getBookID)).map(
                book -> Arrays.asList(
                        String.valueOf(book.getBookID()),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        book.getSubGenre(),
                        book.getPublisher(),
                        String.valueOf(book.getTimesBorrowed()))
        ).toList();
        if ((long) borrowedBooksList.size() == 0) {
            System.out.println("There are currently no books borrowed and the report will not be produced");
            displayLoggedInUserMenuOptions();
            return;
        }
        System.out.println("\n Report successfully produced");
        CsvWriter.writeToCsvFile(borrowedBooksList, "data/books.csv", header);
        displayLoggedInUserMenuOptions();
    }

    public void handleReturnBook() {
        System.out.println("Please enter the ID of the book you would like to return, enter -1 to view the books you have borrowed or -9 to return to the previous menu");
        boolean isHandlerActive = true;
        while (isHandlerActive) {
            long userInputBookID;
            Book selectedBook = null;
            try {
                userInputBookID = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("You must input a valid number, Exception: " + e);
                continue;
            }
            if (userInputBookID == -9) {
                isHandlerActive = false;
                displayLoggedInUserMenuOptions();
            }
            if (library.getBorrowedBooks().containsKey(loggedInUser)) {
                selectedBook = library.getBorrowedBooks().get(loggedInUser).stream().filter(book -> book.getBookID() == userInputBookID).findFirst().orElse(null);
            }
            if (userInputBookID == -1) {
                if (library.getBorrowedBooks().get(loggedInUser) == null) {
                    System.out.println("You have not borrowed any books");
                    continue;
                }
                System.out.println(library.getBorrowedBooks().get(loggedInUser));
            } else if (selectedBook != null) {
                loggedInUser.returnBook(library, userInputBookID);
                System.out.println("You have successfully returned the book with ID: " + userInputBookID + ", Title: " + selectedBook.getTitle());
                isHandlerActive = false;
            } else {
                System.out.println("The Book with ID: " + userInputBookID + " cannot be found in your borrowed books");
            }
        }
        displayLoggedInUserMenuOptions();
    }

    public void handleBorrowBook() {
        System.out.println("Please enter the ID of the book you would like to borrow, enter -1 to view all available books or -9 to go return to the previous menu");
        boolean isHandlerActive = true;
        while (isHandlerActive) {
            long userInputBookID;
            try {
                userInputBookID = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("You must input a valid number, Exception: " + e);
                continue;
            }
            Book selectedBook = library.getAvailableBooks().stream().filter(book -> book.getBookID() == userInputBookID).findFirst().orElse(null);
            if (userInputBookID == -9) {
                isHandlerActive = false;
                displayLoggedInUserMenuOptions();
            }
            if (userInputBookID == -1) {
                displayAvailableLibraryBooks();
                System.out.println("Please enter the ID of the book you would like to borrow, enter -1 to view all available books or -9 to go return to the previous menu");
            } else if (selectedBook != null) {
                loggedInUser.borrowBook(library, userInputBookID);
                System.out.println("You have successfully borrowed the book with ID: " + userInputBookID + ", Title: " + selectedBook.getTitle());
                isHandlerActive = false;
            } else {
                System.out.println("The Book with ID: " + userInputBookID + " cannot be found in our available books");
            }
        }
        displayLoggedInUserMenuOptions();
    }

    public void displayAvailableLibraryBooks() {
        List<Book> bookList = library.getAvailableBooks().stream().sorted(Comparator.comparing(Book::getBookID)).toList();
        for (Book book : bookList) System.out.println(book.toString());
    }

    public void displayAllLibraryBooks() {
        List<Book> bookList = library.getBooks().stream().sorted(Comparator.comparing(Book::getBookID)).toList();
        System.out.println("ID, Title, Author, Genre, Subgenre, Publisher, Times borrowed");
        for (Book book : bookList) System.out.println(book.toString());
    }

    public void handleUserLogout() {
        loggedInUser = null; // Resetting global variable
        displayStartMenu();
    }

    public void handleExitApplication() {
        System.out.println("Thank you for using our library");
        System.exit(0);
    }
}