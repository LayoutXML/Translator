import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Tester {

    private Translator translator;

    /**public static void main(String[] args) {
        Tester tester = new Tester();
        tester.initialise();
        tester.process();
    }*/

    public void initialise() {
        translator = new Translator();
        translator.initialise();
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
                    scanner = new Scanner(System.in); //recreating scanner for nextLine
                    String input, translation, characters = "", lastTranslation="", lastOriginalWord="";
                    int indexOf;
                    boolean error = false, isFirst = true, lastEmpty=false;
                    try {
                        System.out.println("Enter text in English: ");
                        input = scanner.nextLine();
                        String[] words = input.split("\\W+");
                        long startTime = Calendar.getInstance().getTimeInMillis();
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
                                translation = translator.translate(lastOriginalWord + " " + word, 0);
                            } else {
                                System.out.print(lastTranslation);
                                translation = translator.translate(word, 0);
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
                        System.out.print(lastTranslation + input);
                        long endTime = Calendar.getInstance().getTimeInMillis();
                        double wordsPerSecond = words.length*1d/((endTime-startTime)/1000d);
                        System.out.format("\n\nSpeed: %.2f words per second. (It took "
                                + (endTime - startTime) + "ms (" + (endTime - startTime) / 1000d + " seconds) to translate " + words.length + " words)\n",wordsPerSecond);
                    }
                    catch (InputMismatchException e) {
                        userChoice=-1;
                        scanner = new Scanner(System.in); //recreating scanner because otherwise nextInt would not work
                        System.out.println("Invalid input. Please try again");
                    }
                    break;
                case 2:
                    scanner = new Scanner(System.in); //recreating scanner for nextLine
                    String input1, input2;
                    try {
                        System.out.println("Enter text in English: ");
                        input1 = scanner.nextLine();
                        System.out.println("Enter text in other language: ");
                        input2 = scanner.nextLine();
                        translator.addToDictionary(input1,input2, 0);
                        translator.writeFile(0);
                    }
                    catch (InputMismatchException e) {
                        userChoice=-1;
                        scanner = new Scanner(System.in); //recreating scanner because otherwise nextInt would not work
                        System.out.println("Invalid input. Please try again");
                    }
                    break;
                case 3:
                    scanner = new Scanner(System.in); //recreating scanner for nextLine
                    String input3;
                    try {
                        System.out.println("Enter text in English: ");
                        input3 = scanner.nextLine();
                        translator.removeFromDictionary(input3, 0);
                    }
                    catch (InputMismatchException e) {
                        userChoice=-1;
                        scanner = new Scanner(System.in); //recreating scanner because otherwise nextInt would not work
                        System.out.println("Invalid input. Please try again");
                    }
                    translator.writeFile(0);
                    break;
                case 4:
                    scanner = new Scanner(System.in); //recreating scanner for nextLine
                    String input4;
                    try {
                        System.out.println("Enter text in other language: ");
                        input4 = scanner.nextLine();
                        translator.removeFromDictionaryByValue(input4, 0);
                    }
                    catch (InputMismatchException e) {
                        userChoice=-1;
                        scanner = new Scanner(System.in); //recreating scanner because otherwise nextInt would not work
                        System.out.println("Invalid input. Please try again");
                    }
                    translator.writeFile(0);
                    break;
                case 5:
                    scanner = new Scanner(System.in); //recreating scanner for nextLine
                    String input5;
                    try {
                        System.out.println("Enter file name without txt extension: ");
                        input5 = scanner.nextLine();
                        translator.translateFile(input5, 0);
                    }
                    catch (InputMismatchException e) {
                        userChoice=-1;
                        scanner = new Scanner(System.in); //recreating scanner because otherwise nextInt would not work
                        System.out.println("Invalid input. Please try again");
                    }
                    break;
                case 6:
                    translator.printDictionaty(0);
                    break;
                case 7:
                	translator.flipDictionary(0);
                	break;
                case 0:
                    System.out.println("\nGoodbye...");
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
        System.out.println("1. Translate user input");
        System.out.println("2. Add a word or phrase to the dictionary");
        System.out.println("3. Remove a word or phrase (English input)");
        System.out.println("4. Remove a word or phrase (other language input)");
        System.out.println("5. Translate a file");
        System.out.println("6. Print dictionary");
        System.out.println("7. Flip dictionary");
        //TODO: add automated tests #2
        System.out.println("0. Exit");
    }

}
