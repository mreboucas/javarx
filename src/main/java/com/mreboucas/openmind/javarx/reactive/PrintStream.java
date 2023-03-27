package com.mreboucas.openmind.javarx.reactive;

import java.util.stream.IntStream;

public class PrintStream {

    public static void main(String[] args) {
        IntStream.of(1,2,3).forEach(System.out::println);
    }

}
