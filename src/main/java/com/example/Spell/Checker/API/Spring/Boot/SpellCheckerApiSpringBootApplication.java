package com.example.Spell.Checker.API.Spring.Boot;

import com.example.Spell.Checker.API.Spring.Boot.model.InputParagraph;
import com.example.Spell.Checker.API.Spring.Boot.model.SpellCorrectResult;
import com.example.Spell.Checker.API.Spring.Boot.service.SpellCorrectService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class SpellCheckerApiSpringBootApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SpellCheckerApiSpringBootApplication.class, args);
//		SpellCorrectService.initializeArrayListInTableOfWordLength();
//		SpellCorrectService.insertWordsInTableOfWordLength();
//		SpellCorrectService.printContentOfHashTable();
		SpellCorrectService.testSpellCorrect();
	}

}
