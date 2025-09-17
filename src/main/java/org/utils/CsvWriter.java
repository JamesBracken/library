package org.utils;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class CsvWriter {

    public static <T> void writeToCsvFile(List<List<T>> data, String filePath, String[] header) {


        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            writer.writeNext(header);

            for (List<T> list : data) {
                ArrayList<String> newArray = new ArrayList<>();
                for (T item : list) {
                    newArray.add(String.valueOf(item));
                }
                writer.writeNext(newArray.toArray(new String[0]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Exception: " + e + " occurred in writeToCsvFile");
        }
    }

    public static <T> void writeIndividualItemToCsvFile(List<List<T>> data, String filePath, String[] header) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            writer.writeNext(header);
            for (List<T> list : data) {
                ArrayList<String> newArray = new ArrayList<>();
                for (T item : list) {
                    newArray.add(String.valueOf(item));
                }
                writer.writeNext(newArray.toArray(new String[0]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Exception: " + e + " occurred in writeToCsvFile");
        }
    }
}
