package com.example.Spell.Checker.API.Spring.Boot;

import com.example.Spell.Checker.API.Spring.Boot.model.InputParagraph;
import com.example.Spell.Checker.API.Spring.Boot.model.SpellCorrectResult;
import com.example.Spell.Checker.API.Spring.Boot.model.TestCase;
import com.example.Spell.Checker.API.Spring.Boot.service.SpellCorrectService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpellCheckerApiSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpellCheckerApiSpringBootApplication.class, args);
//		SpellCorrectService.initializeArrayListInTableOfWordLength();
//		SpellCorrectService.insertWordsInTableOfWordLength();
//		SpellCorrectService.printContentOfHashTable();
//		List<String> lines = new ArrayList<>();
////		lines.add("correct: wrong1 wrong2");
//		// Add more test cases
//
//		List<TestCase> tests = SpellCorrectService.parseTestSet(lines);
//		System.out.println(tests);

//		SpellCorrectService.testSpellCorrect(tests);
	}

}
