package com.example.Spell.Checker.API.Spring.Boot;

import com.example.Spell.Checker.API.Spring.Boot.model.TestCase;
import com.example.Spell.Checker.API.Spring.Boot.service.SpellCorrectService;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TestAccuracyAndSpeed {
    static LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    public static void main(String[] args) {
//        List<String> lines;
//        try {
//            lines = Files.readAllLines(Path.of("C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\spell test set english 1.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//        List<TestCase> tests = SpellCorrectService.parseTestSet(lines);
//        SpellCorrectService.testSpellCorrect(tests);

//        SpellCorrectService.initializeArrayListInTableOfWordLength();
//        SpellCorrectService.insertWordsInTableOfWordLength();
//        String[] candidates1 = SpellCorrectService.spellCheckAndSuggestEditDistance2("guic").toArray(new String[0]);//berisi 2 array, yg edit distance 1 dan 2
//        System.out.println(Arrays.toString(candidates1));
//        System.out.println(SpellCorrectService.returnSuggestionForWordLengthNotMoreThan15EditDistance1("a"));

//        SpellCorrectService.loadWords("C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\corpus inggris.txt");
//        String word = "promblem"; // The word to be corrected
        SpellCorrectService.initializeArrayListInTableOfWordLength();
        SpellCorrectService.insertWordsInTableOfWordLength();
//        SpellCorrectService.printContentOfHashTable();
        SpellCorrectService.WORDS = new HashMap<>();
        SpellCorrectService.loadWords("C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\corpus inggris.txt");
//        System.out.println(SpellCorrectService.WORDS.size());
        System.out.println(SpellCorrectService.correction("parites"));
//        System.out.println(levenshteinDistance.apply("sending","senidng"));
//        System.out.println();
    }
}
