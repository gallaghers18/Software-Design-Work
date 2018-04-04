/**
 * Autocompleter.java
 * Jeff Ondich, 20 March 2018
 *
 * Sean Gallagher, Elizabeth Budd, Tanvi Mehta
 *
 * This class exposes a very simple interface for generating auto-completions of search strings.
 * The purpose of this class is to give the students in CS257 an opportunity to practice creating
 * unit tests.
 */
package edu.carleton.buddgallagher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Autocompleter {

    private ArrayList<Actor> actors;

    /**
     * @param dataFilePath the path to the data file containing the set of items to
     *                     from which auto-completed results will be drawn. (In the context of this assignment,
     *                     this will be the path to the actors.txt file I provided you. But we'll also talk
     *                     later about how you might use inheritance to create subclasses of Autocompleter
     *                     to use different datasets and different approaches to doing the autocompletion.)
     */
    public Autocompleter(String dataFilePath) {
        // Initialization goes here, as needed. For example, you might load
        // from a file into a list (or a hashmap or something like that)
        // the list of strings that are going to form the dataset of potential
        // auto-completions. The initialization will be up to you.
        actors = new ArrayList<Actor>();

        File inputFile = new File(dataFilePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            return;
        }

        while (scanner.hasNextLine()) {
            actors.add(new Actor(scanner.nextLine()));
        }


    }


    /**
     * @param s The string (either search string or name) to be de-spaced and lowercased.
     * @return String that is lowercased and de-spaced.
     */
    private String getComparisonString(String s) {
        String comparisonString = s.toLowerCase();
        comparisonString = comparisonString.replaceAll("\\s+", "");
        return comparisonString;
    }


    /**
     * @param searchString the string whose autocompletions are sought
     * @return the list of potential completions that match the search string,
     * sorted in decreasing order of quality of the match (that is, the matches
     * are sorted from best match to weakest match)
     */
    public List<String> getCompletions(String searchString) {
        //Check for null input
        if (searchString == null) {
            searchString = "";
        }
        searchString = getComparisonString(searchString);
        ArrayList<Actor> unorderedMatches = new ArrayList<Actor>();
        ArrayList<String> matches = new ArrayList<String>();
        //Check for empty search input
        if (searchString == "") {
            return matches;
        }
        //Handling the comma case in the search string
        if (searchString.contains(",")) {
            for (int i = 0; i < actors.size(); i++) {
                Actor currentActor = actors.get(i);
                if (currentActor.searchName.contains(searchString)) {
                    int indexSearchString = currentActor.searchName.indexOf(searchString);
                    currentActor.indexMatch = indexSearchString;
                    currentActor.order = 0;
                    unorderedMatches.add(currentActor);
                }
            }
        }
        //Adding matches to an array
        else {
            for (int i = 0; i < actors.size(); i++) {
                Actor currentActor = actors.get(i);
                if (currentActor.searchName.contains(searchString)) {
                    int indexSearchString = currentActor.searchName.indexOf(searchString);
                    int indexComma = currentActor.searchName.indexOf(",");
                    //Handling Mononymous Names as Last names
                    if (indexComma == -1) {
                        indexComma = 400000000;
                    }
                    currentActor.indexMatch = indexSearchString;
                    //Begin Categorizing actors by priority of match
                    //Check if search string is the start of the last name or elsewhere in the last name.
                    if (indexComma > indexSearchString) {
                        if (indexSearchString == 0) {
                            currentActor.order = 4;
                        } else {
                            currentActor.order = 2;
                        }
                    }
                    //Check if search string is the start of the first name or elsewhere in the first name.
                    else if (indexComma < indexSearchString) {
                        if (indexSearchString == indexComma + 1) {
                            currentActor.order = 3;
                        } else {
                            currentActor.order = 1;
                        }
                    }
                    unorderedMatches.add(currentActor);
                }
            }
        }

        // Sorting the actors based on (I) Order and (II) matchIndex.
        for (int i = 1; i < unorderedMatches.size(); i++) {
            int j = i;
            //Insertion sort
            while (j > 0) {
                Actor prevActor = unorderedMatches.get(j - 1);
                Actor currActor = unorderedMatches.get(j);
                //Check order
                if (currActor.order > prevActor.order) {
                    unorderedMatches.remove(j);
                    unorderedMatches.add(j - 1, currActor);
                }
                //Check match index
                else if (currActor.order == prevActor.order) {
                    if (currActor.indexMatch < prevActor.indexMatch) {
                        unorderedMatches.remove(j);
                        unorderedMatches.add(j - 1, currActor);
                    }
                }
                j--;
            }
        }
        //Add display names to array of strings to be returned to user
        for (int i = 0; i < unorderedMatches.size(); i++) {
            matches.add(unorderedMatches.get(i).displayName);
        }
        return matches;
    }

    private class Actor {

        private String displayName;
        private String searchName;
        private int order;
        private int indexMatch;

        private Actor(String displayName) {
            this.displayName = displayName;
            this.searchName = getComparisonString(displayName);

        }

    }

    public static void main(String[] args) {
        Autocompleter completer = new Autocompleter(args[0]);
        List<String> matches = completer.getCompletions(args[1]);
        for (String name : matches) {
            System.out.println(name);
        }
    }
}






