package org;

import org.library.Library;
import org.library.LibraryService;

public class Main {
    public static void main(String[] args) {
        Library myLibrary = new Library();
        LibraryService libraryService = new LibraryService();
        libraryService.initializeLibraryApplication(myLibrary);
    }
}