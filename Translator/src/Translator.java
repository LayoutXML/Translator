import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SuppressWarnings("Duplicates")
/**
 * Translator class that contains methods to work with dictionary and dictionary itself
 */
public class Translator {

    private HashMap<String, String> dictionary; //TODO: support multiple languages (multiple dictionaries), make array of #8
    private List<String> exceptions;
    private boolean fileRead; //has the translation been read from the file at least once
    private boolean isReading; //is file open and being read
    private boolean isWriting; //is file open and being written in
    private boolean pendingRead; //is program waiting to reading file
    private boolean pendingWrite; //is program waiting to write file
    private boolean isAddNewWordsToDictOptionEnabled; //TODO: react to this option #3
    private boolean turboMode; //when false ensures 1:1 relationship between languages
    private String[] languageFileNames = {"lithuanian","swedish","albanian"};

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
        exceptions = new ArrayList<>();
        readFile("lithuanian");
    }

    /**
     * Method that returns translation for a given word
     * @param original word in an original language (key)
     * @return translation (value)
     */
    public String translate(String original) {
        if (!exceptions.contains(original)) {
            String translation = dictionary.get(original.toLowerCase());
            if (translation == null) {
                return original.toLowerCase();
            } else {
                return translation;
            }
        }
        return "";
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

            String line, translation;

            while((line = bufferedReader.readLine()) != null) {
                text.append("\n").append(line);
            }

            bufferedReader.close();

            String[] words = text.substring(1).split("\\W+"); //TODO: preserve characters, not split "don't" into 2 words #4
            long startTime = Calendar.getInstance().getTimeInMillis();
            for (String word : words) {
                translation = translate(word);
                if (translation!=null && !translation.equals("")) {
                    System.out.print(translation+" ");
                }
            }
            long endTime = Calendar.getInstance().getTimeInMillis();
            double wordsPerSecond = words.length*(1d/(endTime-startTime)*1000);
            System.out.println("\nSpeed: "+wordsPerSecond+" words per second. (It took "
                    +(endTime-startTime)+"ms ("+(endTime-startTime)/1000d+" seconds) to translate "+words.length+" words)");
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
        String key = "";
        if (!turboMode) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                if (entry.getValue().equals(translation)) {
                    found = true;
                    key = entry.getKey();
                }
            }
        }
        if (!found) {
            String oldValue = dictionary.put(original.toLowerCase(), translation.toLowerCase());
            if (oldValue != null) {
                System.out.println("\"" + original + "\"-\"" + translation + "\" overrode previous pair \"" + original + "\"-\"" + oldValue + "\".");
            } else {
//                System.out.println("\"" + original + "\"-\"" + translation + "\" added.");
            }
        } else {
            String oldValue = dictionary.remove(key);
            dictionary.put(key.toLowerCase(), translation.toLowerCase());
            if (oldValue != null) {
                System.out.println("\"" + original + "\"-\"" + translation + "\" overrode previous pair \"" + key + "\"-\"" + oldValue + "\".");
            }
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
    public void readFile(String fileName) {
        if (!isWriting) {
            isReading = true;
            fileRead = false;
            pendingRead = false;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    FileReader fileReader;
                    BufferedReader bufferedReader;

                    try {
                        //Main dic
                        fileReader = new FileReader(fileName + ".txt");
                        bufferedReader = new BufferedReader(fileReader);

                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            String[] words = line.split("\t", 2);
                            addToDictionary(words[0].toLowerCase(), words[1].toLowerCase());
                        }

                        bufferedReader.close();

                        //Exceptions (if exist)
                        //TODO: make sure array elements are initialised and empty if not or exception #10
                        fileReader = new FileReader(fileName + "-exceptions.txt");
                        bufferedReader = new BufferedReader(fileReader);

                        line = "";

                        while ((line = bufferedReader.readLine()) != null) {
                            exceptions.add(line);
                        }

                        bufferedReader.close();

                    } catch (IOException e) {
                        System.out.println("Error reading file");
                    } finally {
                        fileRead = true;
                        isReading = false;
                        System.out.println("File read. Size: " + dictionary.size());
                        performPending(null, fileName);
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
    public void writeFile(String fileName) {
        if (!isReading) {
            isWriting = true;
            pendingWrite = false;
            Thread thread = new Thread() {
                @Override
                public void run() {

                    try {
                        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName+".txt"), StandardCharsets.UTF_8));

                        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                            out.write(entry.getKey() + "\t" + entry.getValue()+"\n");
                        }

                        out.close();
                    } catch (IOException e) {
                        System.out.println("Error writing file");
                    } finally {
                        isWriting = false;
                        System.out.println("File written");
                        performPending(fileName, null);
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
    private void performPending(String filename1, String filename2) {
        if (pendingWrite && filename1!=null) {
            writeFile(filename1);
        }
        if (pendingRead && filename2!=null) {
            readFile(filename2);
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
