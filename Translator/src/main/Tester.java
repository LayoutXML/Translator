package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
/**
 * Tester class with automated tests
 */
public class Tester {

    private Translator translator;

    /**
     * Initialise method for testing that creates a translator
     */
    public void initialise() {
        translator = new Translator();
        translator.initialise();
        translator.readFile(0);
    }

    /**
     * Method that acts on the user input
     */
    public void process() {
        int userChoice;
        Scanner scanner = new Scanner(System.in);
        do {
            displayMenu();
            try {
                userChoice = scanner.nextInt();
            }
            catch (InputMismatchException e) {
                userChoice=-1;
                scanner = new Scanner(System.in); //recreating scanner because otherwise nextInt would not work
                System.out.println("Invalid input. Please try again");
            }
            switch (userChoice) {
            	case 1:
            		translateInput("meal", 0);
            		translator.flipDictionary(0);
            		translateInput("keleivis", 0);
                    translator.flipDictionary(0);
            		translateInput("drove", 0);
            		translateInput("break up", 0);
            		translateInput("aim at", 0);
                    translateInput("We read 2 books", 0);
                    translateInput("The king, a tree", 0);
            		translateInput("My name is Bob and I live in Dundee.", 0);
                    translator.flipDictionary(0);
                    translateInput("Kiekvienas vasara man ir mano šeima eiti į jūros", 0);
                    translator.flipDictionary(0);
                    translator.addToDictionary("screen","ekranas", 0,true);
                    translator.addToDictionary("Snowboard","snieglentė",0,true);
                    translator.removeFromDictionary("about",0,true);
                    translator.removeFromDictionaryByValue("gyventojas",0,true);
                    translator.removeFromDictionary("flat",0,true);
                    translator.removeFromDictionaryByValue("butas",0,true);
                    translator.printDictionaty(0);
                    translator.translateFile("inputEnglish.txt",0,null,null);
                    translateInput("My location - Dundee. Am I in the Scotland, UK? Yes, I am!",0);

            		break;
                default:
                    System.out.println("\nInvalid option. Please try again");
                    break;
            }
        } while(userChoice!=0);
    }

    /**
     * displayMenu method that displays all available options with their corresponding keyboard input
     */
    private void displayMenu() {
        System.out.println("\nPlease select one of the options below:");
        System.out.println("1. Run automated tests");
    }

    /**
     * Method that splits input and then translates it
     * @param input input (original text)
     * @param languageIndex language index
     */
    private void translateInput(String input, int languageIndex) {
                    System.out.println(input);
                    String translation, characters = "", lastTranslation="", lastOriginalWord="";
        int indexOf;
        boolean error = false, isFirst = true, lastEmpty=false;
        String[] words = input.split("\\P{L}+");
        for (String word : words) {
            if (!error) {
                indexOf = input.indexOf(word);
                if (indexOf == -1) {
                    error = true;
                } else {
                    characters = input.substring(0, indexOf);
                    input = input.substring(indexOf + word.length());
                }
            }
            boolean phrasalVerb = false;
            for (String phrase : translator.getPhrasalVerbs()) {
                if (word.toLowerCase().equals(phrase)) {
                    phrasalVerb = true;
                }
            }
            boolean capitalize = lastEmpty && (lastTranslation.contains(".") || lastTranslation.contains("?") || lastTranslation.contains("!"));
            if (phrasalVerb) {
                translation = translator.translate(lastOriginalWord + " " + word, languageIndex);
            } else {
                System.out.print(lastTranslation);
                translation = translator.translate(word, languageIndex);
            }
            lastTranslation="";
            if (isFirst || characters.contains(".") || characters.contains("?") || characters.contains("!") || capitalize) {
                if (translation.length() > 0) {
                    translation = translation.substring(0, 1).toUpperCase() + translation.substring(1);
                    if (isFirst) {
                        isFirst = false;
                    }
                }
            }
            if ((!error && !lastEmpty) || (characters.contains("\n"))) {
                lastTranslation+=characters;
            }
            if (!translation.equals("")) {
                lastTranslation+=translation;
                lastEmpty = false;
            } else {
                lastEmpty = true;
            }
            lastOriginalWord = word;
        }
        System.out.print(lastTranslation + input+"\n\n");
    }
}
