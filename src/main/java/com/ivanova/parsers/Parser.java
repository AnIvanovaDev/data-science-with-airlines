package com.ivanova.parsers;

public interface Parser<O,I> {

    O parse(I input);
}
