package com.example.brailletranslator;

import java.util.HashMap;

public class BrailleTranslator {
    static StringBuilder translate(String input) {

        StringBuilder output = new StringBuilder();

        // Convert input to lowercase to simplify mapping the letters
        String lowerCaseInput = input.toLowerCase();

        // HashMap to store letter/brailleUnicodeCharacters
        HashMap<Character, String> letterBraille = new HashMap<>();
        letterBraille.put('a', "⠁");
        letterBraille.put('b', "⠃");
        letterBraille.put('c', "⠉");
        letterBraille.put('d', "⠙");

        // Loop through lowerCaseInput to translate it
        for (int i = 0; i < lowerCaseInput.length(); i++) {
            output.append(letterBraille.get(lowerCaseInput.charAt(i)));
        }
        
        return output;
    }
}