package com.tinkoff.edu.app;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    /**
     * Генерирует случайную строку указанной длины
     * из символов имеющейся строки
     *
     * @param length   - длина строки
     * @param alphabet - строка, из которой происходит генерация новой
     * @return случайная строка указанной длины
     */
    public static String generateStringFromAlphabetWithLength(int length, String alphabet) {

        StringBuilder b = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randIdx = ThreadLocalRandom.current().nextInt(alphabet.length());
            char randChar = alphabet.charAt(randIdx);
            b.append(randChar);
        }

        return b.toString();
    }
}
