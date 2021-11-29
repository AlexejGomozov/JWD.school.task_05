package com.javacource.task4.util;

public class VanIdGenerator {
    private static long counter = 0;

    private VanIdGenerator(){
    }
    public static long generate() {
        return ++counter ;
    }
}
