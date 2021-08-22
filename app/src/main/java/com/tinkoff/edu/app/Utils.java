package com.tinkoff.edu.app;

import java.util.Random;

public class Utils {
    private static int requestId;

    public static int randomInt(int min, int max) {
        Random rn = new Random();
        return rn.nextInt(max - min + 1) + min;
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static int randomValueFromArray(int[] vArray) {
        int rn = new Random().nextInt(vArray.length);
        return vArray[rn];
    }

    public static int requestCounter() {
        return requestId++;
    }
}
