package edu.carleton.buddgallagher;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Created by Sean Gallagher, Elizabeth Budd, Tanvi Mehta
 */



class AutocompleterTest {
    private Autocompleter completer;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
         completer = new Autocompleter("actors.txt");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        completer = null;
    }


    @org.junit.jupiter.api.Test
    void testEmptyString() {
        List<String> completions = completer.getCompletions("");
        assertEquals(0, completions.size(), "Empty string did not return empty list");
    }

    @org.junit.jupiter.api.Test
    void testNullInput() {
        List<String> completions = completer.getCompletions(null);
        assertEquals(0, completions.size(), "Null input did not return empty list");
    }

    @org.junit.jupiter.api.Test
    void testNonMatchingInput() {
        List<String> completions = completer.getCompletions("4");
        assertEquals(0, completions.size(), "Non-matching input did not return empty list");
    }

    //check that there are the appropriate number of matches
    @org.junit.jupiter.api.Test
    void testNumber() {
        List<String> completions = completer.getCompletions("qui");
        assertEquals(5, completions.size(), "There is an incorrect number of matches");
    }

    //check that all matches contain the search string
    @org.junit.jupiter.api.Test
    void containsString() {
        List<String> completions = completer.getCompletions("te");
        for (int i = 0; i < completions.size(); i++) {
            assertEquals(true, completions.get(i).contains("te"), "One of the matches doesn't contain the search string.";
        }
    }

    @org.junit.jupiter.api.Test
    void testFormattingOfInput() {
        List<String> completions1 = completer.getCompletions("tri");
        List<String> completions2 = completer.getCompletions("T      r  I");
        String[] completionsArray1 = completions1.toArray(new String[0]);
        String[] completionsArray2 = completions2.toArray(new String[0]);
        assertEquals(true, completionsArray1.equals(completionsArray2), "Input was not properly trimmed and made lowercase");

    }

    //ORDERING STUFF BEGINS

    //We want matches at the beginning of the last name before beginning of first name
    @org.junit.jupiter.api.Test
    void beginLastNameBeforeBeginFirstName() {
        List<String> completions = completer.getCompletions("tayl");
        int x = completions.indexOf("Taylor, Lili");
        int y = completions.indexOf("Kitsch, Taylor");
        assertEquals(true, x < y,"Beginning of first name is before beginning of last name");
    }

    @org.junit.jupiter.api.Test
    void beginFirstNameBeforeMidLastName() {
        List<String> completions = completer.getCompletions("tin");
        int x = completions.indexOf("Fey, Tina");
        int y = completions.indexOf("Astin, Sean");
        assertEquals(true, x < y , "A middle of last name match came before a beginning of first name match");
    }

    @org.junit.jupiter.api.Test
    void midLastNameBeforeMidFirstName() {
        List<String> completions = completer.getCompletions("tin");
        int x = completions.indexOf("Perry, Tyler");
        int y = completions.indexOf("Pepper. Barry");
        assertEquals(true, x < y, "A middle of first name match came before a middle of last name match");
    }

    @org.junit.jupiter.api.Test
    void orderByCloserToFrontOfString() {
        List<String> completions = completer.getCompletions("lly");
        int x = completions.indexOf("Kelly");
        int y = completions.indexOf("Connelly");
        assertEquals(true, x < y, "Not ordered by proximity to front (both not at beginning of last names");
    }

    @org.junit.jupiter.api.Test
    void alphabeticalOrdering() {
        List<String> completions = completer.getCompletions("Kelly");
        int x = completions.indexOf("Kelly, Gene");
        int y = completions.indexOf("Kelly, Grace");
        assertEquals(true, x < y, "Ordering was not alphabetical (given match at same index in name)");
    }

    @org.junit.jupiter.api.Test
    void mononymousNamesAsLastNames() {
        List<String> completions = completer.getCompletions("ch");
        int x = completions.indexOf("Cher");
        int y = completions.indexOf("Applegate, Christina");
        assertEquals(true, x < y, "Mononymous name is not treated as last name in ordering");
    }

    @org.junit.jupiter.api.Test
    void hyphenFirstNameOrdering() {
        List<String> completions = completer.getCompletions("Paul");
        int x = completions.indexOf("Bettany, Paul");
        int y = completions.indexOf("Belmondo, Jean-Paul");
        assertEquals(true, x < y, "After hyphen string treated as beginning of first name");
    }

    @org.junit.jupiter.api.Test
    void hyphenLastNameOrdering() {
        List<String> completions = completer.getCompletions("Johnson");
        int x = completions.indexOf("Johnson, Ben");
        int y = completions.indexOf("Taylor-Johnson, Aaron");
        assertEquals(true, x < y, "After hyphen string treated as beginning of last name");
    }

    //ORDERING STUFF ENDS

    @org.junit.jupiter.api.Test
    void findsMatchesAcrossComma() {
        List<String> completions = completer.getCompletions("n, s");
        for (int i = 0; i < completions.size(); i++) {
            assertEquals(true, completions.get(i).contains("n, s"), "Did not match across the comma");
        }
    }

    @org.junit.jupiter.api.Test
    void noRepeatedNames() {
        List<String> completions = completer.getCompletions("li");
        completions.remove("Collins, Lily");
        assertEquals(false, completions.contains("Collins, Lily"), "Name appeared multiple times in list");
    }

}


