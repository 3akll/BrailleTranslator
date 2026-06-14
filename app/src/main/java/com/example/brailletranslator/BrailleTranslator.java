package com.example.brailletranslator;

import java.util.HashMap;

public class BrailleTranslator {
    static StringBuilder translate(String input) {

        StringBuilder output = new StringBuilder();

        // Convert input to lowercase to simplify mapping the letters
        String lowerCaseInput = input.toLowerCase();

        // HashMap to store letter/brailleUnicodeCharacters
        HashMap<Character, String> letterBraille = new HashMap<>();
        letterBraille.put(' ', "⠀"); // Unaffected by toLowerCase() method
        letterBraille.put('a', "⠁");
        letterBraille.put('b', "⠃");
        letterBraille.put('c', "⠉");
        letterBraille.put('d', "⠙");
        letterBraille.put('e', "⠑");
        letterBraille.put('f', "⠋");
        letterBraille.put('g', "⠛");
        letterBraille.put('h', "⠓");
        letterBraille.put('i', "⠊");
        letterBraille.put('j', "⠚");
        letterBraille.put('k', "⠅");
        letterBraille.put('l', "⠇");
        letterBraille.put('m', "⠍");
        letterBraille.put('n', "⠝");
        letterBraille.put('o', "⠕");
        letterBraille.put('p', "⠏");
        letterBraille.put('q', "⠟");
        letterBraille.put('r', "⠗");
        letterBraille.put('s', "⠎");
        letterBraille.put('t', "⠞");
        letterBraille.put('u', "⠥");
        letterBraille.put('v', "⠧");
        letterBraille.put('w', "⠺");
        letterBraille.put('x', "⠭");
        letterBraille.put('y', "⠽");
        letterBraille.put('z', "⠵");

        // Loop through lowerCaseInput to translate it
        for (int i = 0; i < lowerCaseInput.length(); i++) {
            // Check if the char is a letter or a space
            if (Character.isLetter(lowerCaseInput.charAt(i)) || lowerCaseInput.charAt(i) == ' ') {
                output.append(letterBraille.get(lowerCaseInput.charAt(i)));
            }
            // Logic for numbers to be added
            /*
            If the char is neither a letter nor a number (to be handled after)
                ==> it is classified as a special character
            If it is not recognized as a special character
                ==> it's an unsupported/unmapped character and will be replaced by '?'
            */
            else {
                // Logic for special characters to be added
                output.append("?");
            }
        }
        
        return output;
    }
}