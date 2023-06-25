package com.example.Spell.Checker.API.Spring.Boot.service;

import com.example.Spell.Checker.API.Spring.Boot.model.InputParagraph;
import com.example.Spell.Checker.API.Spring.Boot.model.SpellCorrectResult;
import com.example.Spell.Checker.API.Spring.Boot.model.StringObject;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
@Service
public class SpellCorrectService {
    static Random random = new Random();
    //    static int  r = random.nextInt(Integer.MAX_VALUE);
    static int[] r = new int[32];
    static ArrayList<StringObject>[] tableOfWordLength = new ArrayList[16];//table dengan panjang kata 1, berada di index 0,
    //panjang kata 2, di index 2 dst
    static String filePath = "C:\\Users\\ACER\\IdeaProjects\\Spell Correct API\\src\\hasil1tolower.txt";
    static LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    @Autowired
    public SpellCorrectService() {
    }

    public  ArrayList<SpellCorrectResult> doSpellCorrect(InputParagraph inputParagraph){
        ArrayList<SpellCorrectResult> spellCorrectResults = new ArrayList<>();
        ArrayList<String> suggestions;

        for(int i = 0; i < inputParagraph.getInputInArray().length; i++){
            String word = inputParagraph.getInputInArray()[i];
            suggestions = spellCheckAndSuggest(word);
            spellCorrectResults.add(new SpellCorrectResult(word,suggestions));
        }
        return spellCorrectResults;
    }
    public static long computeWordHashValue(String word){
        char[] wordInChar = word.toCharArray();
        long hashValue = 0;
        for(int i = 0; i < wordInChar.length; i++){
            hashValue+= (long) wordInChar[i] *r[i];
        }
        hashValue = hashValue%(Integer.MAX_VALUE);
        return hashValue;
    }
    private static void generateRandomNumberArray(){
        for(int i =0; i < r.length; i++) {
            r[i] = random.nextInt(Integer.MAX_VALUE/4);
        }
    }
    private static void printContentOfHashTable() {
        //Print the contents of each ArrayList in the array
        for (int i = 0; i < 16; i++) {
            System.out.println("Elements in ArrayList at index " + i + ":");
            for (StringObject element : tableOfWordLength[i]) {
                System.out.println(element.getName());
            }
            System.out.println();
        }
    }

    public static ArrayList<String> returnSuggestion(String query){
        ArrayList<String> suggestions = new ArrayList<>();
        for(int i = query.length()-2; i < query.length()+3; i++){
            for(int j = 0; j < tableOfWordLength[i].size(); j++){
                String wordInTable = tableOfWordLength[i].get(j).getName();
                if(levenshteinDistance.apply(query,wordInTable)<=2){
                    suggestions.add(wordInTable);
                }
            }
        }
        return suggestions;
    }
    public static boolean isFoundInDictionaryTables(String query){
        int queryLength = query.length();
        StringObject o = new StringObject(query,computeWordHashValue(query));
        if(tableOfWordLength[queryLength-1].contains(o)){
            return true;
        }else{
            return false;
        }
    }
    private static void initializeArrayListInTableOfWordLength(){
        // Initialize each ArrayList in the array
        for (int i = 0; i < tableOfWordLength.length; i++) {
            tableOfWordLength[i] = new ArrayList<>();
        }
    }
    private static void insertWordsInTableOfWordLength(){
        generateRandomNumberArray();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int length = line.length();
                long hashValue = computeWordHashValue(line);
                StringObject entry = new StringObject(line,hashValue);
                if (length < 16) {
                    if(tableOfWordLength[length - 1].contains(entry)){
                    }else {
                        tableOfWordLength[length-1].add(new StringObject(line,hashValue));//supaya kata yg panjangnya 1 masuk ke index 0,
                    }
                } else {
                    tableOfWordLength[15].add(new StringObject(line,hashValue));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static ArrayList<String> spellCheckAndSuggest(String query){
        ArrayList<String> result = new ArrayList<>();
        initializeArrayListInTableOfWordLength();
        insertWordsInTableOfWordLength();
        if(isFoundInDictionaryTables(query)){
            System.out.println(query+" is found in dictionary, no need to be corrected");
        }else {
            System.out.println(query+" is not found in dictionary");
            result = returnSuggestion(query);
        }
        return result;
    }


}
