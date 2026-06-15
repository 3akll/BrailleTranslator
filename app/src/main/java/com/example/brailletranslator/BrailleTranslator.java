package com.example.brailletranslator;

import androidx.annotation.NonNull;
import java.util.HashMap;

/* This logic can be simplified by doing only 1 HashMap, and mapping characters, numbers and
punctuations as they are strings ==> we will have one statement in the loop*/

/* For training purposes and for better clarity and organization I considered 3 HashMaps */

public class BrailleTranslator {
    // Public method ==> will be called by MainActivity class
    @NonNull
    public static String translate(String input) {

        StringBuilder output = new StringBuilder();

        // Convert input to lowercase to simplify mapping the letters
        String lowerCaseInput = input.toLowerCase();

        /* HashMap to store letter/brailleUnicodeCharacters
        Method createLetterBrailleMap() is used to make translate() method shorter and focused
        only on translating */
        HashMap<Character, String> letterBraille = createLetterBrailleMap();

        // HashMap to store number/brailleUnicodeCharacters
        HashMap<Integer, String> numberBraille = createNumberBrailleMap();

        // HashMap to store punctuation/brailleUnicodeCharacters
        HashMap<Character, String> punctuationBraille = createPunctuationBrailleMap();

        // Loop through lowerCaseInput to translate it
        for (int i = 0; i < lowerCaseInput.length(); i++) {
            // Check if the char is a letter or a space
            if (Character.isLetter(lowerCaseInput.charAt(i)) || lowerCaseInput.charAt(i) == ' ') {
                output.append(letterBraille.get(lowerCaseInput.charAt(i)));
            }
            else if (Character.isDigit(lowerCaseInput.charAt(i))) {
                output.append(numberBraille.get(Character.digit(lowerCaseInput.charAt(i),10))); // Radix = 10 ==> Base 10 numbers
            }
            else {
                // Check if the char is a punctuation and get his value
                if (punctuationBraille.containsKey(lowerCaseInput.charAt(i))) {
                    output.append(punctuationBraille.get(lowerCaseInput.charAt(i)));
                }
                // The char isn't recognized
                else {
                    output.append("?");
                }
            }
        }

        // Return a string representing the data contained by StringBuilder Object "output"
        return output.toString();
    }

    // Private method ==> belongs only to BrailleTranslator class
    // No need to be shared with other classes
    // Creates a map and then stores it in letterBraille HashMap
    @NonNull
    private static HashMap<Character, String> createLetterBrailleMap() {
        HashMap<Character, String> letterBrailleMap = new HashMap<>();
        letterBrailleMap.put(' ', "⠀"); // Unaffected by toLowerCase() method
        letterBrailleMap.put('a', "⠁");
        letterBrailleMap.put('b', "⠃");
        letterBrailleMap.put('c', "⠉");
        letterBrailleMap.put('d', "⠙");
        letterBrailleMap.put('e', "⠑");
        letterBrailleMap.put('f', "⠋");
        letterBrailleMap.put('g', "⠛");
        letterBrailleMap.put('h', "⠓");
        letterBrailleMap.put('i', "⠊");
        letterBrailleMap.put('j', "⠚");
        letterBrailleMap.put('k', "⠅");
        letterBrailleMap.put('l', "⠇");
        letterBrailleMap.put('m', "⠍");
        letterBrailleMap.put('n', "⠝");
        letterBrailleMap.put('o', "⠕");
        letterBrailleMap.put('p', "⠏");
        letterBrailleMap.put('q', "⠟");
        letterBrailleMap.put('r', "⠗");
        letterBrailleMap.put('s', "⠎");
        letterBrailleMap.put('t', "⠞");
        letterBrailleMap.put('u', "⠥");
        letterBrailleMap.put('v', "⠧");
        letterBrailleMap.put('w', "⠺");
        letterBrailleMap.put('x', "⠭");
        letterBrailleMap.put('y', "⠽");
        letterBrailleMap.put('z', "⠵");

        return letterBrailleMap;
    }

    // Creates a map and then stores it in numberBraille HashMap
    @NonNull
    private static HashMap<Integer, String> createNumberBrailleMap() {
        HashMap<Integer, String> numberBrailleMap = new HashMap<>();
        numberBrailleMap.put(0, "⠼⠚");
        numberBrailleMap.put(1, "⠼⠁");
        numberBrailleMap.put(2, "⠼⠃");
        numberBrailleMap.put(3, "⠼⠉");
        numberBrailleMap.put(4, "⠼⠙");
        numberBrailleMap.put(5, "⠼⠑");
        numberBrailleMap.put(6, "⠼⠋");
        numberBrailleMap.put(7, "⠼⠛");
        numberBrailleMap.put(8, "⠼⠓");
        numberBrailleMap.put(9, "⠼⠊");

        return numberBrailleMap;
    }

    // Creates a map and then stores it in numberBraille HashMap
    @NonNull
    private static HashMap<Character, String> createPunctuationBrailleMap() {
        HashMap<Character, String> punctuationBrailleMap = new HashMap<>();
        punctuationBrailleMap.put(',', "⠂");
        punctuationBrailleMap.put(';', "⠆");
        punctuationBrailleMap.put(':', "⠒");
        punctuationBrailleMap.put('.', "⠲");
        punctuationBrailleMap.put('?', "⠦");
        punctuationBrailleMap.put('!', "⠖");
        punctuationBrailleMap.put('(', "⠐⠣");
        punctuationBrailleMap.put(')', "⠐⠜");
        punctuationBrailleMap.put('/', "⠸⠌");
        punctuationBrailleMap.put('\\', "⠸⠡"); // The sequence \\ inserts a single backslash "\"
        punctuationBrailleMap.put('-', "⠤");

        return punctuationBrailleMap;
    }
}