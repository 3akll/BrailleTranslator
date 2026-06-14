package com.example.brailletranslator;

public class BrailleTranslator {
    static String translate(String input) {
        String output = "";
        output = String.copyValueOf(input.toCharArray());
        return output;
    }
}