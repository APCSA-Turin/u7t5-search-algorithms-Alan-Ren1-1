package com.example.project.Spell_Checker;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SpellChecker {
    private ArrayList<String> dictionary;
    private int loopCounter = 0;

    public SpellChecker() {
        dictionary = new ArrayList<String>();
        importDictionary();
        Collections.sort(dictionary); // Sort the dictionary for binary search
    }

    public ArrayList<String> getDictionary() {
        return dictionary;
    }

    public int getLoopCounter() {
        return loopCounter;
    }

    /** This uses LINEAR search to find a word in dictionary and returns
     *  true if the word is in dictionary and false otherwise.
     *
     *  It also tracks the number of words checked (loop iterations) and
     *  prints that value out before returning.
     */
    public boolean linearSpellCheck(String word) {
        loopCounter = 0; // Reset loop counter for testing
        for (String s : dictionary) {
            loopCounter++; // Increment loop counter
            if (word.equals(s)) {
                System.out.println("-- LINEAR SEARCH: Number of words checked (loop iterations): " + loopCounter);
                return true;
            }
        }
        System.out.println("-- LINEAR SEARCH: Number of words checked (loop iterations): " + loopCounter);
        return false;
    }

    /** This uses BINARY search to find a word in dictionary and returns
     *  true if the word is in dictionary and false otherwise.
     *
     *  It also tracks the number of words checked (loop iterations) and
     *  prints that value out before returning.
     */
    public boolean binarySpellCheck(String word) {
        loopCounter = 0; // Reset loop counter for testing
        int left = 0;
        int right = dictionary.size() - 1;

        while (left <= right) {
            loopCounter++; // Increment loop counter for each iteration
            int middle = left + (right - left) / 2;

            // Debugging output to see progress
            System.out.println("Checking middle: " + middle + " (word: " + dictionary.get(middle) + ")");

            if (dictionary.get(middle).equals(word)) {
                System.out.println("-- BINARY SEARCH: Word found after " + loopCounter + " iterations");
                return true; // Word found
            }

            if (dictionary.get(middle).compareTo(word) < 0) {
                left = middle + 1; // Search the right half
            } else {
                right = middle - 1; // Search the left half
            }
        }

        System.out.println("-- BINARY SEARCH: Word not found after " + loopCounter + " iterations");
        return false; // Word not found
    }

    // private helper method, called in the constructor, which loads the words
    // from the dictionary.txt text file into the "dictionary" instance variable!
    private void importDictionary() {
        try {
            File myFile = new File("src/main/java/com/example/project/Spell_Checker/dictionary.txt");
            Scanner fileScanner = new Scanner(myFile);
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                dictionary.add(data);
            }
            System.out.println("\ndictionary.txt file imported successfully!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpellChecker checker = new SpellChecker();
        System.out.println("----------------------------- SPELL CHECKER -------------------------------");
        System.out.println("The spell checker dictionary has " + checker.getDictionary().size() + " words in it!");

        System.out.println("Enter a word to look up; if the word is not found, it is not");
        System.out.print("spelled correctly (or is simply not a word), or type q to quit: ");
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();

        while (!word.equals("q")) {
            if (checker.binarySpellCheck(word)) {
                System.out.println("-- " + word + " WAS found in the dictionary (so it's spelled correctly)");
            } else {
                System.out.println("-- " + word + " was NOT found in the dictionary (so it's not spelled correctly)");
            }
            System.out.print("Enter a word to look up or q to quit: ");
            word = scan.nextLine();
        }
        scan.close();
    }
}
