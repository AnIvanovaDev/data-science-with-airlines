package com.ivanova.parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateParser implements Parser<LocalDate, String> {
    public static final String DATE_FORMAT = "M/d/uu";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Override
    public LocalDate parse(String input) {
        try {
            return input == null ? null : LocalDate.parse(input, DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}
