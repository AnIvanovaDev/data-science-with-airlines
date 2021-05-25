package com.ivanova.parsers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalTimeParser implements Parser<LocalTime, String> {

    public static final String TIME_FORMAT = "Hmm";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    @Override
    public LocalTime parse(String input) {
        try {
            return input == null ? null : LocalTime.parse(input, TIME_FORMATTER);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}
