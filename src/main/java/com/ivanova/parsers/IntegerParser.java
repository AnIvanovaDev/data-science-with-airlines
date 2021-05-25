package com.ivanova.parsers;

public class IntegerParser implements Parser<Integer, String>{
    @Override
    public Integer parse(String input) {
        try {
            return input != null ? Integer.parseInt(input) : 0;
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }
}
