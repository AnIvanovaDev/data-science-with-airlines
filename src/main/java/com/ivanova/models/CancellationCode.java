package com.ivanova.models;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CancellationCode {
    NONE,
    A,
    B,
    C,
    D;

    private static final Map<String, CancellationCode> MAPPER;

    static {
        MAPPER = Arrays.stream(CancellationCode.values()).collect(Collectors.toMap(CancellationCode::name, Function.identity()));
    }

    public static CancellationCode parse(String input) {
        return MAPPER.getOrDefault(input, NONE);
    }
}
