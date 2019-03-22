import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Tester {

    private Translator translator;

    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.initialise();
        tester.process();
    }

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
                    String input, translation;
                    try {
                        System.out.println("Enter text in English: ");
                        input = scanner.nextLine();
                        String[] words = input.split("\\W+");
                        long startTime = Calendar.getInstance().getTimeInMillis();
                        for (String word : words) {
                            translation = translator.translate(word);
                            if (translation!=null && !translation.equals("")) {
                                System.out.print(translation+" ");
                            }
                        }
                        long endTime = Calendar.getInstance().getTimeInMillis();
                        double wordsPerSecond = words.length*1d/((endTime-startTime)/1000d);
                        System.out.println("\nSpeed: "+wordsPerSecond+" words per second. (It took "
                                +(endTime-startTime)+"ms ("+(endTime-startTime)/1000d+" seconds) to translate "+words.length+" words)");
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
                        translator.addToDictionary(input1,input2);
                        translator.writeFile("lithuanian");
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
                        translator.removeFromDictionary(input3);
                    }
                    catch (InputMismatchException e) {
                        userChoice=-1;
                        scanner = new Scanner(System.in); //recreating scanner because otherwise nextInt would not work
                        System.out.println("Invalid input. Please try again");
                    }
                    break;
                case 4:
                    scanner = new Scanner(System.in); //recreating scanner for nextLine
                    String input4;
                    try {
                        System.out.println("Enter text in other language: ");
                        input4 = scanner.nextLine();
                        translator.removeFromDictionaryByValue(input4);
                    }
                    catch (InputMismatchException e) {
                        userChoice=-1;
                        scanner = new Scanner(System.in); //recreating scanner because otherwise nextInt would not work
                        System.out.println("Invalid input. Please try again");
                    }
                    break;
                case 5:
                    scanner = new Scanner(System.in); //recreating scanner for nextLine
                    String input5;
                    try {
                        System.out.println("Enter file name without txt extension: ");
                        input5 = scanner.nextLine();
                        translator.translateFile(input5);
                    }
                    catch (InputMismatchException e) {
                        userChoice=-1;
                        scanner = new Scanner(System.in); //recreating scanner because otherwise nextInt would not work
                        System.out.println("Invalid input. Please try again");
                    }
                    break;
                case 6:
                    translator.printDictionaty();
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
        //TODO: add automated tests #2
        System.out.println("0. Exit");
    }

}
