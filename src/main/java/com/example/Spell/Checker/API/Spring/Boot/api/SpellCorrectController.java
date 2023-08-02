package com.example.Spell.Checker.API.Spring.Boot.api;

import com.example.Spell.Checker.API.Spring.Boot.model.InputParagraph;
import com.example.Spell.Checker.API.Spring.Boot.model.SpellCorrectResult;
import com.example.Spell.Checker.API.Spring.Boot.service.SpellCorrectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("api/v1/correct")
@RestController
public class SpellCorrectController {
    private final SpellCorrectService spellCorrectService;
    String englishCorpusPath = "C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\corpus inggris.txt";
    String indonesianCorpusPath = "C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\corpus.txt";
    String englishDictionaryPath = "C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\kamus inggris.txt";
    String indonesianDictionaryPath = "C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\kamus indo.txt";

    @Autowired
    public SpellCorrectController(SpellCorrectService spellCorrectService) {
        this.spellCorrectService = spellCorrectService;
    }
    @PostMapping
    public ArrayList<SpellCorrectResult> spellCheckAndCorrectEnglish(@RequestBody InputParagraph inputParagraph){
        return spellCorrectService.doSpellCheckAndCorrect(inputParagraph,englishCorpusPath, englishDictionaryPath);
    }
    @RequestMapping(value = "/indonesian", method =  RequestMethod.POST)
    public ArrayList<SpellCorrectResult> spellCheckAndCorrectIndonesian(@RequestBody InputParagraph inputParagraph){
        return spellCorrectService.doSpellCheckAndCorrect(inputParagraph,indonesianCorpusPath, indonesianDictionaryPath);
    }
}
