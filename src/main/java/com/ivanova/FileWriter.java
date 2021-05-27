package com.ivanova;

import java.io.*;
import java.util.List;

public class FileWriter {

    public static void write(String fileName, List<Flight> flights) {
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(new File(fileName))) {
            for (Flight flight : flights) {
                fileWriter.write(flight.toString()+"\n");
            }
        } catch (IOException ex) {
            System.out.println("Can not write to file");
        }
    }
}
