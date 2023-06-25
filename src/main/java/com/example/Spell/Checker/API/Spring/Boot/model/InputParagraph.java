package com.example.Spell.Checker.API.Spring.Boot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputParagraph {
    public final String inputParagraph;
    private final String[] inputInArray;

    public InputParagraph(@JsonProperty("input") String inputParagraph) {
        this.inputParagraph = inputParagraph;
        String cleanedParagraph = removePunctuations(inputParagraph);
        inputInArray = cleanedParagraph.split("\\s+");
    }

    public String[] getInputInArray() {
        return inputInArray;
    }
    public static String removePunctuations(String source) {
        return source.replaceAll("\\p{Punct}", "");
    }
}
