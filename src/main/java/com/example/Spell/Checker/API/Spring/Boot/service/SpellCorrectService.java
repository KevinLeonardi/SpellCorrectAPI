package com.example.Spell.Checker.API.Spring.Boot.service;

import com.example.Spell.Checker.API.Spring.Boot.model.InputParagraph;
import com.example.Spell.Checker.API.Spring.Boot.model.SpellCorrectResult;
//import com.example.Spell.Checker.API.Spring.Boot.model.StringObject;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class SpellCorrectService {
    static Random random = new Random();
    static ArrayList<String>[] tableOfWordLength = new ArrayList[16];//table dengan panjang kata 1, berada di index 0,
    //panjang kata 2, di index 2 dst
    static String filePath = "C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\kamus inggris.txt";
//    static String filePath = "C:\\Users\\ACER\\IdeaProjects\\Spell Correct API\\src\\hasil1tolower.txt";
    static LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    private static Map<String, Integer> WORDS;
    @Autowired
    public SpellCorrectService() {
    }

    public ArrayList<SpellCorrectResult> doSpellCorrect(InputParagraph inputParagraph){
        ArrayList<SpellCorrectResult> spellCorrectResults = new ArrayList<>();
        ArrayList<String> suggestions;

        initializeArrayListInTableOfWordLength();
        insertWordsInTableOfWordLength();

        for(int i = 0; i < inputParagraph.getInputInArray().length; i++){
            String word = inputParagraph.getInputInArray()[i];
            suggestions = spellCheckAndSuggest(word);
            spellCorrectResults.add(new SpellCorrectResult(word,suggestions));
        }
        return spellCorrectResults;
    }

//    public static long computeWordHashValue(String word){
//        char[] wordInChar = word.toCharArray();
//        long hashValue = 0;
//        for(int i = 0; i < wordInChar.length; i++){
//            hashValue+= (long) wordInChar[i] *r[i];
//        }
//        hashValue = hashValue%(Integer.MAX_VALUE);
//        return hashValue;
//    }
//    private static void generateRandomNumberArray(){
//        for(int i =0; i < r.length; i++) {
//            r[i] = random.nextInt(Integer.MAX_VALUE/4);
//        }
//    }
    public static void testSpellCorrect(){
//        ArrayList<String> suggestions;
        WORDS = new HashMap<>();
        loadWords("C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\corpus inggris.txt");
        String word = "promblem"; // The word to be corrected
        initializeArrayListInTableOfWordLength();
        insertWordsInTableOfWordLength();
        String correctedWord = correction(word);
        System.out.println("Corrected word: " + correctedWord);
    }
    public static double P(String word) {
        int totalWordCount = WORDS.values().stream().mapToInt(Integer::intValue).sum();
        return (double) WORDS.getOrDefault(word, 0) / totalWordCount;
    }

    public static void loadWords(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\W+");
                for (String w : words) {
                    WORDS.put(w, WORDS.getOrDefault(w, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String correction(String word) {
        String[] candidates = spellCheckAndSuggest(word).toArray(new String[0]);
//        System.out.println(Arrays.toString(candidates));
        String correctedWord = candidates[0]; // Default to the first candidate
        double maxProbability = P(correctedWord);

        for (int i = 1; i < candidates.length; i++) {
            String candidate = candidates[i];
            double probability = P(candidate);
            if (probability > maxProbability) {
                correctedWord = candidate;
                maxProbability = probability;
            }
        }

        return correctedWord;
    }
    public static void printContentOfHashTable() {
        //Print the contents of each ArrayList in the array
        for (int i = 0; i < 16; i++) {
            System.out.println("Elements in ArrayList at index " + i + ":");
            for (String element : tableOfWordLength[i]) {
                System.out.println(element);
            }
            System.out.println();
        }
    }

    public static ArrayList<String> returnSuggestion(String query){
        ArrayList<String> suggestions = new ArrayList<>();
        for(int i = query.length()-2; i < query.length()+3; i++){
            for(int j = 0; j < tableOfWordLength[i].size(); j++){
                String wordInTable = tableOfWordLength[i].get(j);
                if(levenshteinDistance.apply(query,wordInTable)<=2){
                    suggestions.add(wordInTable);
                }
            }
        }
        return suggestions;
    }
    public static boolean isFoundInDictionaryTables(String query){
        int queryLength = query.length();
        //StringObject o = new StringObject(query,computeWordHashValue(query));
        if(tableOfWordLength[queryLength-1].contains(query)){
            return true;
        }else{
            return false;
        }
    }
    public static void initializeArrayListInTableOfWordLength(){
        // Initialize each ArrayList in the array
        for (int i = 0; i < tableOfWordLength.length; i++) {
            tableOfWordLength[i] = new ArrayList<>();
        }
    }
    public static void insertWordsInTableOfWordLength(){
//        generateRandomNumberArray();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int length = line.length();
//                System.out.println(line);
//                String input = line;
                //long hashValue = computeWordHashValue(line);
                //StringObject entry = new StringObject(line,hashValue);
                if (length < 16) {
                    if(tableOfWordLength[length - 1].contains(line)){

                    }else {
                        tableOfWordLength[length-1].add(line);//supaya kata yg panjangnya 1 masuk ke index 0,
                    }
                } else {
                    tableOfWordLength[15].add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static ArrayList<String> spellCheckAndSuggest(String query){
        ArrayList<String> result = new ArrayList<>();
        if(isFoundInDictionaryTables(query)){
         //   System.out.println(query+" is found in dictionary, no need to be corrected");
        }else {
         //   System.out.println(query+" is not found in dictionary");
            result = returnSuggestion(query);
        }
        return result;
    }


}
