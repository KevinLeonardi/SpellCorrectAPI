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
        List<String> lines;
        String indonesianCorpusPath = "C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\corpus.txt";
        String indonesianDictionaryPath = "C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\kamus indo.txt";
        String englishCorpusPath = "C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\corpus inggris.txt";
        String englishDictionaryPath = "C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\kamus inggris.txt";
//
//n;        try {
//                lines = Files.readAllLines(Path.of("C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\spell-testset3.txt"));
////            lines = Files.readAllLines(Path.of("C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\spell test set english 1.txt"));
//            } catch (IOException e) {
//                e.printStackTrace();
//                retur
//        }
//        List<TestCase> tests = SpellCorrectService.parseTestSet(lines);
//        SpellCorrectService.testSpellCorrect(tests,englishCorpusPath,englishDictionaryPath);

        ////////////////////////////////////////
        String corpusPath = indonesianCorpusPath;
        String dictionaryPath = indonesianDictionaryPath;
        SpellCorrectService.WORDS = new HashMap<>();
        SpellCorrectService.loadWords(corpusPath);
        SpellCorrectService.initializeArrayListInTableOfWordLength();
        SpellCorrectService.insertWordsInTableOfWordLength(dictionaryPath);
        System.out.println(SpellCorrectService.correction("dalm"));
        System.out.println(levenshteinDistance.apply("analised","analysed"));
    }
}
