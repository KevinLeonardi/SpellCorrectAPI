package com.example.Spell.Checker.API.Spring.Boot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SpellCorrectResult {
    private String wordToCorrect;
    private ArrayList<String> suggestions;

    public SpellCorrectResult(@JsonProperty("wordChecked") String wordToCorrect,
                              @JsonProperty("suggestions") ArrayList<String> suggestions) {
        this.wordToCorrect = wordToCorrect;
        this.suggestions = suggestions;
    }

    public String getWordToCorrect() {
        return wordToCorrect;
    }

    public ArrayList<String> getSuggestions() {
        return suggestions;
    }
}
