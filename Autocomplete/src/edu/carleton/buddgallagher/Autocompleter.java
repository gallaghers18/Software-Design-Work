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
     * from which auto-completed results will be drawn. (In the context of this assignment,
     * this will be the path to the actors.txt file I provided you. But we'll also talk
     * later about how you might use inheritance to create subclasses of Autocompleter
     * to use different datasets and different approaches to doing the autocompletion.)
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



    }



    /**
     * @param searchString the string whose autocompletions are sought
     * @return the list of potential completions that match the search string,
     *  sorted in decreasing order of quality of the match (that is, the matches
     *  are sorted from best match to weakest match)
     */
    public List<String> getCompletions(String searchString) {
        return new ArrayList<String>();
    }
}

private class Actor {

    private String displayName;
    private String searchName;
    private int order;
    private int indexMatch;

    private Actor(String displayName) {
        
    }

}
