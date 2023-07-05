package com.example.Spell.Checker.API.Spring.Boot;

import com.example.Spell.Checker.API.Spring.Boot.model.TestCase;
import com.example.Spell.Checker.API.Spring.Boot.service.SpellCorrectService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestAccuracyAndSpeed {
    public static void main(String[] args) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of("C:\\Users\\ACER\\Desktop\\Spell-Checker-API-Spring-Boot\\src\\main\\java\\com\\example\\Spell\\Checker\\API\\Spring\\Boot\\spell-testset1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        List<TestCase> tests = SpellCorrectService.parseTestSet(lines);

        SpellCorrectService.testSpellCorrect(tests);
    }
}
