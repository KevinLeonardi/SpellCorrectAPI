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
    @Autowired
    public SpellCorrectController(SpellCorrectService spellCorrectService) {
        this.spellCorrectService = spellCorrectService;
    }
    @PostMapping
    public ArrayList<SpellCorrectResult> test(@RequestBody InputParagraph inputParagraph){
        return spellCorrectService.doSpellCorrect(inputParagraph);
    }
}
