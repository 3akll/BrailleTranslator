package com.example.brailletranslator;

import androidx.annotation.NonNull;
import java.util.HashMap;

/*
 * Separate HashMaps are used for letters, digits, and punctuation to keep the
 * mappings clear while learning. A single character map could also work because
 * the input is processed one character at a time.
 */

/**
 * Provides translation methods between regular text and braille symbols
 */
public class BrailleTranslator {
    /**
     * Translates input text to braille symbols
     * @param input text to translate
     * @return the translated braille result
     */
    @NonNull
    public static String translateTextToBraille(String input) {

        char currentChar;
        StringBuilder output = new StringBuilder();

        // Convert input of type text only to lowercase to simplify mapping the letters
        String lowerCaseInput = input.toLowerCase();

        /*
         * HashMap to store letter/brailleSymbol
         * Method createLetterBrailleMap() is used to make translate() method shorter and focused
         * only on translating
         */
        HashMap<Character, String> letterBraille = createLetterBrailleMap();

        // HashMap to store number/brailleSymbol
        HashMap<Integer, String> numberBraille = createNumberBrailleMap();

        // HashMap to store punctuation/brailleSymbol
        HashMap<Character, String> punctuationBraille = createPunctuationBrailleMap();

        // Loop through lowerCaseInput to translate it
        for (int i = 0; i < lowerCaseInput.length(); i++) {

            currentChar = lowerCaseInput.charAt(i);

            /* Check if currentChar:
             *      1- is a letter
             *      2- and is contained in the letterBraille HashMap
             *      3- or is a space
             */
            if (((Character.isLetter(currentChar)) && (letterBraille.containsKey(currentChar))) || (currentChar == ' ')) {
                output.append(letterBraille.get(currentChar));
            }
            /* Check if currentChar:
             *      1- is a digit
             *      2- and is contained in the numberBraille HashMap
             *
             *      Note: currentChar must be converted to the numeric value before checking if it
             *            is contained in the HashMap
             */
            else if ((Character.isDigit(currentChar)) && (numberBraille.containsKey(Character.digit(currentChar, 10)))) {
                output.append(numberBraille.get(Character.digit(currentChar,10))); // Radix = 10 ==> Base 10 numbers
            }
            else {
                // Check if currentChar is a punctuation
                if (punctuationBraille.containsKey(currentChar)) {
                    output.append(punctuationBraille.get(currentChar));
                }
                // currentChar isn't recognized / not mapped ==> print "?" character
                else {
                    output.append("?");
                }
            }
        }

        // Convert the StringBuilder content into a String and return it
        return output.toString();
    }

    /**
     * Translates input braille symbols to text
     * @param input braille symbols to translate
     * @return the translated text result
     */
    @NonNull
    public static String translateBrailleToText(String input) {

        String currentBraille;
        StringBuilder output = new StringBuilder();

        // HashMap to store brailleSymbol/letter
        HashMap<String, Character> brailleLetter = createBrailleLetterMap();

        for (int i = 0; i < input.length(); i++) {

            // Convert the char at position i to String, cause the key of brailleLetter HashMap is a String
            currentBraille = String.valueOf(input.charAt(i));

            // Check if currentBraille is contained in the brailleLetter HashMap
            if (brailleLetter.containsKey(currentBraille)) {
                output.append(brailleLetter.get(currentBraille));
            }
            else {
                // currentBraille isn't recognized / not mapped ==> print "?" character
                output.append("?");
            }
        }

        // Convert the StringBuilder content into a String and return it
        return output.toString();
    }

    /**
     * Creates a HashMap to store letter/brailleSymbol
     * @return a HashMap where each letter points to its Braille symbol
     */
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

    /**
     * Creates a HashMap to store punctuation/brailleSymbol
     * @return a HashMap where each punctuation points to its braille symbol
     */
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

    /**
     * Creates a HashMap to store number/brailleSymbol
     * @return a HashMap where each number points to its braille symbol
     */
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

    /**
     * Creates a HashMap to store brailleSymbol/letter
     * @return a HashMap where each braille symbol points to its letter
     */
    @NonNull
    private static HashMap<String, Character> createBrailleLetterMap() {

        HashMap<String, Character> brailleLetterMap = new HashMap<>();

        brailleLetterMap.put("⠀", ' ');
        brailleLetterMap.put("⠁", 'a');
        brailleLetterMap.put("⠃", 'b');
        brailleLetterMap.put("⠉", 'c');
        brailleLetterMap.put("⠙", 'd');
        brailleLetterMap.put("⠑", 'e');
        brailleLetterMap.put("⠋", 'f');
        brailleLetterMap.put("⠛", 'g');
        brailleLetterMap.put("⠓", 'h');
        brailleLetterMap.put("⠊", 'i');
        brailleLetterMap.put("⠚", 'j');
        brailleLetterMap.put("⠅", 'k');
        brailleLetterMap.put("⠇", 'l');
        brailleLetterMap.put("⠍", 'm');
        brailleLetterMap.put("⠝", 'n');
        brailleLetterMap.put("⠕", 'o');
        brailleLetterMap.put("⠏", 'p');
        brailleLetterMap.put("⠟", 'q');
        brailleLetterMap.put("⠗", 'r');
        brailleLetterMap.put("⠎", 's');
        brailleLetterMap.put("⠞", 't');
        brailleLetterMap.put("⠥", 'u');
        brailleLetterMap.put("⠧", 'v');
        brailleLetterMap.put("⠺", 'w');
        brailleLetterMap.put("⠭", 'x');
        brailleLetterMap.put("⠽", 'y');
        brailleLetterMap.put("⠵", 'z');

        // Bugs when translating braille that corresponds to numbers

        return brailleLetterMap;
    }
}