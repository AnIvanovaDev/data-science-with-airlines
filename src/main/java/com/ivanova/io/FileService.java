package com.ivanova.io;

import com.ivanova.parsers.FlightParser;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Anastasiia Ivanova
 * @since 1.0.0
 */
public class FileService {
    public List<String> readFileFromClassPath(String fileName) {
        try {
            URL resource = FileService.class.getResource(fileName);
            Path path = Paths.get(resource.toURI());
            return Files.lines(path).skip(1).collect(Collectors.toList());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Unable to read file");
        }
    }

    public void writeFile(String fileName, List<String> results) {
        try (PrintStream pr = new PrintStream(fileName, StandardCharsets.UTF_8.name())) {
            results.forEach(pr::println);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to write to file");
        }

    }
}
