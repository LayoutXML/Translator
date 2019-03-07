import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Translator {

    private HashMap<String, String> dictionary;
    private boolean fileRead;
    private boolean isReading;

    public static void main(String[] args) {
        //main
        Translator translator = new Translator();
        translator.initialise();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        System.out.println("");
        long startTime = Calendar.getInstance().getTimeInMillis();
        for (String word : words) {
            System.out.print(translator.translate(word)+" ");
        }
        long endTime = Calendar.getInstance().getTimeInMillis();
        double wordsPerSecond = words.length*(1d/(endTime-startTime)*1000);
        System.out.println("Speed: "+wordsPerSecond+" words per second.");
    }

    public void initialise() {
        dictionary = new HashMap<>();
        fileRead = false;
        isReading = false;
        readFile();
    }

    public String translate(String original) {
        String translation = dictionary.get(original.toLowerCase());
        if (translation==null) {
            return original.toLowerCase();
        } else {
            return translation;
        }
    }

    public void addToDictionary(String original, String translation) {
        dictionary.put(original.toLowerCase(), translation.toLowerCase());
    }

    public void removeFromDictionary(String original) {
        dictionary.remove(original.toLowerCase());
    }

    public void readFile() {
        isReading = true;
        fileRead = false;
        Thread thread = new Thread() {
            @Override
            public void run() {
                FileReader fileReader;
                BufferedReader bufferedReader;
                String fileName = "dictionary";

                try {
                    fileReader = new FileReader(fileName+".txt");
                    bufferedReader = new BufferedReader(fileReader);

                    String line;

                    while((line = bufferedReader.readLine()) != null) {
                        String[] words = line.split("\t",2);
                        dictionary.put(words[1].toLowerCase(),words[0].toLowerCase());
                    }

                    bufferedReader.close();
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Error reading file");
                } finally {
                    fileRead = true;
                    isReading = false;
                    System.out.println("File read. Size: "+dictionary.size());
                }
            }
        };
        thread.run();
    }

    public void writeFile() {
        if (!isReading) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    FileOutputStream fileOutputStream;
                    PrintWriter printWriter;
                    String fileName = "dictionary";

                    try {
                        fileOutputStream = new FileOutputStream(fileName + ".txt");
                        printWriter = new PrintWriter(fileOutputStream);

                        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                            printWriter.write(entry.getKey() + "\t" + entry.getValue()+"\n");
                        }

                        printWriter.close();
                    } catch (IOException e) {
                        System.out.println("Error writing file");
                    }
                }
            };
            thread.run();
        }
    }
}
