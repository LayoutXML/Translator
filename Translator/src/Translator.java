import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
/**
 * Translator class that contains methods to work with dictionary and dictionary itself
 */
public class Translator {

    private HashMap<String, String> dictionary; //TODO: support multiple languages (multiple dictionaries) #8
    private boolean fileRead; //has the translation been read from the file at least once
    private boolean isReading; //is file open and being read
    private boolean isWriting; //is file open and being written in
    private boolean pendingRead; //is program waiting to reading file
    private boolean pendingWrite; //is program waiting to write file
    private boolean isAddNewWordsToDictOptionEnabled; //TODO: react to this option #3
    private boolean turboMode; //when false ensures 1:1 relationship between languages
    //TODO: flip dictionary keys and values #9

    /**
     * Method that sets default values for variables and reads the file
     */
    public void initialise() {
        dictionary = new HashMap<>();
        fileRead = false;
        isReading = false;
        isWriting = false;
        pendingRead = false;
        pendingWrite = false;
        turboMode = true;
        readFile();
    }

    /**
     * Method that returns translation for a given word
     * @param original word in an original language (key)
     * @return translation (value)
     */
    public String translate(String original) {
        String translation = dictionary.get(original.toLowerCase());
        if (translation==null) {
            return original.toLowerCase();
        } else {
            return translation;
        }
    }

    /**
     * Method that reads and translates a file (to a console)
     * @param fileName file name
     */
    public void translateFile(String fileName) {
        FileReader fileReader;
        BufferedReader bufferedReader;
        StringBuilder text = new StringBuilder(); //StringBuilder for better performance (appending text in loop)
        try {
            fileReader = new FileReader(fileName+".txt");
            bufferedReader = new BufferedReader(fileReader);

            String line;

            while((line = bufferedReader.readLine()) != null) {
                text.append(line);
            }

            bufferedReader.close();

            String[] words = text.toString().split("\\W+"); //TODO: preserve characters, not split "don't" into 2 words #4
            long startTime = Calendar.getInstance().getTimeInMillis();
            for (String word : words) {
                System.out.print(translate(word)+" ");
            }
            long endTime = Calendar.getInstance().getTimeInMillis();
            double wordsPerSecond = words.length*(1d/(endTime-startTime)*1000);
            System.out.println("\nSpeed: "+wordsPerSecond+" words per second.");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    /**
     * Method that adds word or phrase pair to a dictionary
     * @param original original word or phrase (key)
     * @param translation translation (value)
     */
    public void addToDictionary(String original, String translation) {
        boolean found = false;
        if (!turboMode) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                if (entry.getValue().equals(translation)) {
                    found = true;
                }
            }
        }
        if (!found) {
            String oldValue = dictionary.put(original.toLowerCase(), translation.toLowerCase());
            if (oldValue != null) {
                System.out.println("\"" + original + "\"-\"" + translation + "\" overrode previous pair \"" + original + "\"-\"" + oldValue + "\".");
            } else {
                System.out.println("\"" + original + "\"-\"" + translation + "\" added.");
            }
        } else {

        }
        //TODO: ensure 1:1 relationship when not in turbo mode #7
    }

    /**
     * Method that removes a word or phrase from a dictionary by key
     * @param original word or phrase in an original language (key)
     */
    public void removeFromDictionary(String original) {
        String oldValue = dictionary.remove(original.toLowerCase());
        if (oldValue!=null) {
            System.out.println("\""+original+"\"-\""+oldValue+"\" removed.");
        } else {
            System.out.println("\""+original+"\" was not in the dictionary, so nothing was removed.");
        }
    }

    /**
     * Method that removes a word or phrase from a dictionary by value
     * @param translation translation (value)
     */
    public void removeFromDictionaryByValue(String translation) {
        ArrayList<String> keysToRemove = new ArrayList<>();
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            if (entry.getValue().equals(translation.toLowerCase())) {
                keysToRemove.add(entry.getKey());
            }
        }
        for (String key: keysToRemove) {
            dictionary.remove(key);
        }
        if (keysToRemove.size()==0) {
            System.out.println("\""+translation+"\" was not in the dictionary, so nothing was removed.");
        }
    }

    /**
     * Method that prints the dictionary to a console
     */
    public void printDictionaty() {
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    /**
     * Method to retrieve isAddNewWordsToDictOptionEnabled
     *
     * @return value of isAddNewWordsToDictOptionEnabled
     */
    public boolean isAddNewWordsToDictOptionEnabled() {
        return isAddNewWordsToDictOptionEnabled;
    }

    /**
     * Method to set isAddNewWordsToDictOptionEnabled
     *
     * @param addNewWordsToDictOptionEnabled value
     */
    public void setAddNewWordsToDictOptionEnabled(boolean addNewWordsToDictOptionEnabled) {
        isAddNewWordsToDictOptionEnabled = addNewWordsToDictOptionEnabled;
    }

    /**
     * Method that reads dictionary file to a dictionary
     */
    public void readFile() {
        if (!isWriting) {
            isReading = true;
            fileRead = false;
            pendingRead = false;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    FileReader fileReader;
                    BufferedReader bufferedReader;
                    String fileName = "dictionary";

                    try {
                        fileReader = new FileReader(fileName + ".txt");
                        bufferedReader = new BufferedReader(fileReader);
                        //TODO: ensure correct encoding/locale #6

                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            String[] words = line.split("\t", 2);
                            addToDictionary(words[1].toLowerCase(), words[0].toLowerCase());
                        }

                        bufferedReader.close();
                    } catch (IOException e) {
                        System.out.println("Error reading file");
                    } finally {
                        fileRead = true;
                        isReading = false;
                        System.out.println("File read. Size: " + dictionary.size());
                        performPending();
                    }
                }
            };
            thread.run();
        } else {
            pendingRead = true;
        }
    }

    /**
     * Method that writes a dictionary to a file
     */
    public void writeFile() {
        if (!isReading) {
            isWriting = true;
            pendingWrite = false;
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
                    } finally {
                        isWriting = false;
                        System.out.println("File written");
                        performPending();
                    }
                }
            };
            thread.run();
        } else {
            pendingWrite = true;
        }
    }

    /**
     * Method that performs pending tasks, for example reading/writing files
     */
    private void performPending() {
        if (pendingWrite) {
            writeFile();
        }
        if (pendingRead) {
            readFile();
        }
    }

    public void flipDictionary() {
        HashMap<String, String> dictionaryNew = new HashMap<>();
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            dictionaryNew.put(entry.getValue(),entry.getKey());
        }
        dictionary = dictionaryNew;
    }
}
