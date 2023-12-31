package com.example.Spell.Checker.API.Spring.Boot.service;

import com.example.Spell.Checker.API.Spring.Boot.model.InputParagraph;
import com.example.Spell.Checker.API.Spring.Boot.model.SpellCorrectResult;
//import com.example.Spell.Checker.API.Spring.Boot.model.StringObject;
import com.example.Spell.Checker.API.Spring.Boot.model.TestCase;
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
//    static String filePath = "C:\\Users\\ACER\\Documents\\Skripsi Kevin Leonardi Spell Corrector\\kamus inggris.txt";
//    static String filePath = "C:\\Users\\ACER\\IdeaProjects\\Spell Correct API\\src\\hasil1tolower.txt";
    static LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    public static Map<String, Integer> WORDS;
    @Autowired
    public SpellCorrectService() {
    }

//    public ArrayList<SpellCorrectResult> doSpellCheckAndSuggest(InputParagraph inputParagraph){
//        ArrayList<SpellCorrectResult> spellCorrectResults = new ArrayList<>();
//        ArrayList<String> suggestions;
//
//        initializeArrayListInTableOfWordLength();
//        insertWordsInTableOfWordLength();
//
//        for(int i = 0; i < inputParagraph.getInputInArray().length; i++){
//            String word = inputParagraph.getInputInArray()[i];
//            suggestions = spellCheckAndSuggest(word);
//            spellCorrectResults.add(new SpellCorrectResult(word,suggestions));
//        }
//        return spellCorrectResults;
//    }
    public ArrayList<SpellCorrectResult> doSpellCheckAndCorrect(InputParagraph inputParagraph, String corpusPath, String dictionaryPath){
        ArrayList<SpellCorrectResult> spellCorrectResults = new ArrayList<>();
        String correction;
        WORDS = new HashMap<>();
        loadWords(corpusPath);


        initializeArrayListInTableOfWordLength();
        insertWordsInTableOfWordLength(dictionaryPath);

        for(int i = 0; i < inputParagraph.getInputInArray().length; i++){
            String word = inputParagraph.getInputInArray()[i];
            correction = correction(word);
            ArrayList<String> correctionInArrayList= new ArrayList<>();
            correctionInArrayList.add(correction);
            spellCorrectResults.add(new SpellCorrectResult(word,correctionInArrayList));
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
    public static void testSpellCorrect(List<TestCase> tests, String corpusPath, String dictionaryPath){
//        ArrayList<String> suggestions;
        ArrayList<String> notFound = new ArrayList<>();
        ArrayList<String> rightList = new ArrayList<>();

        WORDS = new HashMap<>();
        loadWords(corpusPath);
//        String word = "promblem"; // The word to be corrected
        initializeArrayListInTableOfWordLength();
        insertWordsInTableOfWordLength(dictionaryPath);
//        printContentOfHashTable();
        long start = System.currentTimeMillis();
        int good = 0;
        int n = tests.size();

        for (TestCase test : tests) {
            String right = test.getRight();
            String wrong = test.getWrong();

            String w = wrong;
            if (isFoundInDictionaryTables(w)) {
//                good++;
                System.out.println("word: " + w + " exists in the vocabulary. No correction required");
            } else {
                String wOld = w;
                w = correction(w);
                if(w.equals("")){
                    notFound.add(wOld);
                    rightList.add(right);
                }
                System.out.println("found replacement: " + w + " for word: " + wOld);
            }

            if (w.equals(right)) {
                good++;
//                System.out.println("w is "+w);
            }
        }
        long dt = System.currentTimeMillis() - start;
        System.out.printf("%.0f%% of %d correct at %.0f words per second%n",
                (double) good / n * 100, n, n / (dt / 1000.0));

//        System.out.println("good "+good+" n = "+n);
//        System.out.println("/////////////////////////////////////////////////");
//        for(int i = 0 ; i < notFound.size(); i++){
//            int rightListItemSize = rightList.get(i).length();
//            int leftListItemSize = notFound.get(i).length();
//            System.out.println(rightList.get(i)+" "+notFound.get(i)+" text length dif "+Math.abs(rightListItemSize-leftListItemSize)+
//                    " lev distance is "+levenshteinDistance.apply(rightList.get(i), notFound.get(i)));
//        }
//        System.out.println("notFound size "+notFound.size());
//
//        System.out.println("=================================================");
////        for(int i = 0 ; i < rightList.size(); i++){
////            System.out.println(rightList.get(i));
////        }
//        System.out.println("correct size "+rightList.size());
//        System.out.println("not found  "+notFound);
    }

    public static List<TestCase> parseTestSet(List<String> lines) {
        List<TestCase> testCases = new ArrayList<>();
        for (String line : lines) {
//            System.out.println(line);
            String[] parts = line.split(":");
            String right = parts[0].trim();
            String[] wrongs = parts[1].trim().split(" ");

            for (String wrong : wrongs) {
                testCases.add(new TestCase(right, wrong));
            }
        }
        return testCases;
    }

    public static double P(String word) {
        int totalWordCount = WORDS.values().stream().mapToInt(Integer::intValue).sum();
//        System.out.println("occurrence "+word+" "+WORDS.getOrDefault(word, 0)+" totalWordCount "+totalWordCount);
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
        word = word.toLowerCase();
        String[] candidates1 = spellCheckAndSuggestEditDistance1(word).toArray(new String[0]);//berisi 2 array, yg edit distance 1 dan 2
//        System.out.println("candidates1 "+ Arrays.toString(candidates1));
        String[] candidates2 = spellCheckAndSuggestEditDistance2(word).toArray(new String[0]);
//        System.out.println("candidates2 "+Arrays.toString(candidates2));

//        System.out.println("candidates1" +Arrays.toString(candidates1));
        if(candidates1.length==0 && candidates2.length==0){
//            System.out.println("no correction for word "+word);
            return "";
        }
        if(candidates1.length>0){
//            System.out.println("candidates1 "+ Arrays.toString(candidates1));
            return choose1Word(candidates1);
        }else{
//            System.out.println("candidates2 "+Arrays.toString(candidates2));
            return choose1Word(candidates2);
        }
    }

    public static String choose1Word(String[] candidates){
        String correctedWord = candidates[0]; // Default to the first candidate
        double maxProbability = P(correctedWord);
//        System.out.println(correctedWord+" "+maxProbability);

        for (int i = 1; i < candidates.length; i++) {
            String candidate = candidates[i];
            double probability = P(candidate);
//            System.out.println("candidate "+candidate+" , probability "+probability);
            if (probability > maxProbability) {
                correctedWord = candidate;
                maxProbability = probability;
//                System.out.println(correctedWord+""+maxProbability);
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
        ArrayList<String> suggestions;

        if(query.length()<15){
            suggestions = returnSuggestionForWordLengthNotMoreThan15(query);
        }else{
            suggestions = returnSuggestionForWordLength15AndMore(query);
        }
        return suggestions;
    }
    public static ArrayList<String> returnSuggestionEditDistance1(String query){
        ArrayList<String> suggestions;

        if(query.length()<15){
            suggestions = returnSuggestionForWordLengthNotMoreThan15EditDistance1(query);
        }else{
            suggestions = returnSuggestionForWordLength15AndMoreEditDistance1(query);
        }
        return suggestions;
    }
    public static ArrayList<String> returnSuggestionEditDistance2(String query){
        ArrayList<String> suggestions;

        if(query.length()<15){
            suggestions = returnSuggestionForWordLengthNotMoreThan15EditDistance2(query);
        }else{
            suggestions = returnSuggestionForWordLength15AndMoreEditDistance2(query);
        }
        return suggestions;
    }
    public static ArrayList<String> returnSuggestionForWordLengthNotMoreThan15EditDistance2(String query){
        ArrayList<String> suggestions = new ArrayList<>();
        int wordIndex = query.length()-1;
        if(wordIndex-2<=0){
            wordIndex=2;
        }
//        System.out.println("query length "+query.length());
        for(int i = wordIndex-2; i < wordIndex+3; i+=4){//supaya tidak melakukan edit distance 1 lgi
//            System.out.println(tableOfWordLength[i]);
//            System.out.println(query);
            for(int j = 0; j < tableOfWordLength[i].size(); j++){
//                System.out.println("i = "+i);
                String wordInTable = tableOfWordLength[i].get(j);
                if(levenshteinDistance.apply(query,wordInTable)==2){
                    suggestions.add(wordInTable);
                }
            }
        }
        return suggestions;
    }
    public static ArrayList<String> returnSuggestionForWordLength15AndMoreEditDistance2(String query){
        ArrayList<String> suggestions = new ArrayList<>();
        int wordIndex = query.length()-1;
//        System.out.println("query length "+query.length());
        for(int i = wordIndex-2; i < 16; i+=4){//example pronounciation with length 14 at index 13, so we check from index 11-15
            for(int j = 0; j < tableOfWordLength[i].size(); j++){
//                System.out.println("i = "+i);
                String wordInTable = tableOfWordLength[i].get(j);
                if(levenshteinDistance.apply(query,wordInTable)==2){
                    suggestions.add(wordInTable);
                }
            }
        }
        return suggestions;
    }

    public static ArrayList<String> returnSuggestionForWordLengthNotMoreThan15EditDistance1(String query){
        ArrayList<String> suggestions = new ArrayList<>();
        int wordIndex = query.length()-1;
        if(wordIndex-1<0){
            wordIndex=1;
        }
//        System.out.println("query length "+query.length());
        for(int i = wordIndex-1; i < wordIndex+2; i++){//example pronounciation with length 14 at index 13, so we check from index 11-15
//            System.out.println(tableOfWordLength[i]);
//            System.out.println("i "+i);
            for(int j = 0; j < tableOfWordLength[i].size(); j++){
//                System.out.println("j = "+j);
                String wordInTable = tableOfWordLength[i].get(j);
//                System.out.println("query "+query+" wordInTable "+wordInTable);
                if(levenshteinDistance.apply(query,wordInTable)==1){
//                    System.out.println(wordInTable+" added");
                    suggestions.add(wordInTable);
                }
            }
        }
        return suggestions;
    }
    public static ArrayList<String> returnSuggestionForWordLength15AndMoreEditDistance1(String query){
        ArrayList<String> suggestions = new ArrayList<>();
        int wordIndex = query.length()-1;
//        System.out.println("query length "+query.length());
        for(int i = wordIndex-1; i < 16; i++){//example pronounciation with length 14 at index 13, so we check from index 11-15
            for(int j = 0; j < tableOfWordLength[i].size(); j++){
//                System.out.println("i = "+i);
                String wordInTable = tableOfWordLength[i].get(j);
                if(levenshteinDistance.apply(query,wordInTable)==1){
                    suggestions.add(wordInTable);
                }
            }
        }
        return suggestions;
    }


    public static ArrayList<String> returnSuggestionForWordLengthNotMoreThan15(String query){
        ArrayList<String> suggestions = new ArrayList<>();
        int wordIndex = query.length()-1;
//        System.out.println("query length "+query.length());
        for(int i = wordIndex-2; i < wordIndex+3; i++){//example pronounciation with length 14 at index 13, so we check from index 11-15
            for(int j = 0; j < tableOfWordLength[i].size(); j++){
//                System.out.println("i = "+i);
                String wordInTable = tableOfWordLength[i].get(j);
                if(levenshteinDistance.apply(query,wordInTable)<=2){
                    suggestions.add(wordInTable);
                }
            }
        }
        return suggestions;
    }
    public static ArrayList<String> returnSuggestionForWordLength15AndMore(String query){
        ArrayList<String> suggestions = new ArrayList<>();
        for(int i = query.length()-2; i < 16; i++){
            for(int j = 0; j < tableOfWordLength[i].size(); j++){
//                System.out.println("i = "+i);
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
        if(queryLength<=15){
            if(tableOfWordLength[queryLength-1].contains(query)){
                return true;
            }else{
                return false;
            }
        }else {
            if(tableOfWordLength[15].contains(query)){
                return true;
            }else{
                return false;
            }
        }
    }
    public static void initializeArrayListInTableOfWordLength(){
        // Initialize each ArrayList in the array
        for (int i = 0; i < tableOfWordLength.length; i++) {
            tableOfWordLength[i] = new ArrayList<>();
        }
    }
    public static void insertWordsInTableOfWordLength(String filePath){
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
    public static ArrayList<String> spellCheckAndSuggestEditDistance1(String query){
        ArrayList<String> result = new ArrayList<>();
        if(isFoundInDictionaryTables(query)){
//               System.out.println(query+" is found in dictionary, no need to be corrected");
        }else {
            //   System.out.println(query+" is not found in dictionary");
            result = returnSuggestionEditDistance1(query);
//            System.out.println("result is "+result);
        }
        return result;
    }
    public static ArrayList<String> spellCheckAndSuggestEditDistance2(String query){
        ArrayList<String> result = new ArrayList<>();
        if(isFoundInDictionaryTables(query)){
            //   System.out.println(query+" is found in dictionary, no need to be corrected");
        }else {
            //   System.out.println(query+" is not found in dictionary");
            result = returnSuggestionEditDistance2(query);
//            System.out.println("result is "+result);
        }
        return result;
    }

//    public static String[] spellCheckAndCorrect(String query){
////        ArrayList<String> result = new ArrayList<>();
//        String result1, result2;
//        if(isFoundInDictionaryTables(query)){
//            //   System.out.println(query+" is found in dictionary, no need to be corrected");
//        }else {
//            //   System.out.println(query+" is not found in dictionary");
//            result1 = correction(query);
//
//        }
//        return result1;
//    }

}
