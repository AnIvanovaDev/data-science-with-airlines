package com.ivanova.parsers;

public class BooleanParser implements Parser<Boolean, String> {
    private static final String TRUE_STRING = "1";

    @Override
    public Boolean parse(String input) {
        return TRUE_STRING.equals(input);
    }
}
