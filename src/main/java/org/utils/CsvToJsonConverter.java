package org.utils;

import org.book.Book;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvToJsonConverter {

    public static List<Book> readBooksFromCsv(Path filePath) {
        List<Book> newBooks = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                long id = Integer.parseInt(fields[0]);
                String title = fields[1];
                String author = fields[2];
                String genre = fields[3];
                String subGenre = fields[4];
                String publisher = fields[5];
                newBooks.add(new Book(id, title, author, genre, subGenre, publisher, 0));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + filePath + " Exception: " + e);
        }
        return newBooks;
    }

    public static void convertCsvToJson(Path csvPath, Path jsonPath) {
        List<Book> books = readBooksFromCsv(csvPath);
        JsonWriter.writeToJsonFile(books, jsonPath);
    }
}
