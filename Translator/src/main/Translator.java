package main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SuppressWarnings("Duplicates")
/**
 * Translator class that contains methods to work with dictionaries and dictionaries itself
 */
public class Translator {

    private List<HashMap<String, String>> dictionaries; //an arraylist of dictionary hashmaps
    private List<String> exceptions; //lithuanian language exceptions
    private boolean fileRead; //has the translation been read from the file at least once
    private boolean isReading; //is file open and being read
    private boolean isWriting; //is file open and being written in
    private boolean pendingRead; //is program waiting to reading file
    private boolean pendingWrite; //is program waiting to write file
    private boolean isAddNewWordsToDictOptionEnabled; //TODO: react to this option #3
    private boolean turboMode; //when false ensures 1:1 relationship between languages
    private String[] languageFileNames = {"lithuanian","swedish","albanian"};
    private String[] phrasalVerbs = {"up","down","off","out","in"};

    /**
     * Method that sets default values for variables and reads the file
     */
    public void initialise() {
        dictionaries = new ArrayList<HashMap<String, String>>();
        for (String language: languageFileNames) {
            dictionaries.add(new HashMap<>());
        }
        fileRead = false;
        isReading = false;
        isWriting = false;
        pendingRead = false;
        pendingWrite = false;
        turboMode = true;
        exceptions = new ArrayList<>();
    }

    /**
     * Method that returns translation for a given word
     * @param original word in an original language (key)
     * @return translation (value)
     */
    public String translate(String original, int languageIndex) {
        if (!exceptions.contains(original.toLowerCase())) {
            String translation = dictionaries.get(languageIndex).get(original.toLowerCase());
            if (translation == null) {
                if (languageIndex==0) {
                    String[] words = original.split("\\W+");
                    StringBuilder processed = new StringBuilder();
                    for (String word : words) {
                        int indexOf = original.indexOf(word);
                        String characters = original.substring(0, indexOf);
                        original = original.substring(indexOf + word.length());
                        if (!exceptions.contains(word.toLowerCase())) {
                            processed.append(characters).append(word);
                        }
                    }
                    return processed.toString().toLowerCase();
                } else {
                    return original.toLowerCase();
                }
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
    public void translateFile(String fileName, int languageIndex) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                FileReader fileReader;
                BufferedReader bufferedReader;
                StringBuilder text = new StringBuilder(); //StringBuilder for better performance (appending text in loop)
                try {
                    fileReader = new FileReader(fileName + ".txt");
                    bufferedReader = new BufferedReader(fileReader);

                    String line, translation, characters = "", lastTranslation="", lastOriginalWord="";
                    int indexOf;
                    boolean error = false, isFirst = true, lastEmpty=false;

                    while ((line = bufferedReader.readLine()) != null) {
                        text.append("\n").append(line);
                    }

                    bufferedReader.close();

                    String input = text.toString();

                    String[] words = text.substring(1).split("\\W+");
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
                        for (String phrase : phrasalVerbs) {
                            if (word.toLowerCase().equals(phrase)) {
                                phrasalVerb = true;
                            }
                        }
                        boolean capitalize = lastEmpty && (lastTranslation.contains(".") || lastTranslation.contains("?") || lastTranslation.contains("!"));
                        if (phrasalVerb && languageIndex==0) {
                            translation = translate(lastOriginalWord + " " + word, languageIndex);
                        } else {
                            System.out.print(lastTranslation);
                            translation = translate(word, languageIndex);
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
                    double wordsPerSecond = words.length * (1d / (endTime - startTime) * 1000);
                    System.out.format("\n\nSpeed: %.2f words per second. (It took "
                            + (endTime - startTime) + "ms (" + (endTime - startTime) / 1000d + " seconds) to translate " + words.length + " words)\n",wordsPerSecond);
                } catch (IOException e) {
                    System.out.println("Error reading file");
                }
            }
        };
        thread.run();
    }

    /**
     * Method that adds word or phrase pair to a dictionary
     * @param original original word or phrase (key)
     * @param translation translation (value)
     */
    public void addToDictionary(String original, String translation, int languageIndex) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean found = false;
                String key = "";
                if (!turboMode) {
                    for (Map.Entry<String, String> entry : dictionaries.get(languageIndex).entrySet()) {
                        if (entry.getValue().equals(translation)) {
                            found = true;
                            key = entry.getKey();
                        }
                    }
                }
                if (!found) {
                    String oldValue = dictionaries.get(languageIndex).put(original.toLowerCase(), translation.toLowerCase());
                    if (oldValue != null) {
                        System.out.println("\"" + original + "\"-\"" + translation + "\" overrode previous pair \"" + original + "\"-\"" + oldValue + "\".");
                    }
                } else {
                    String oldValue = dictionaries.get(languageIndex).remove(key);
                    dictionaries.get(languageIndex).put(key.toLowerCase(), translation.toLowerCase());
                    if (oldValue != null) {
                        System.out.println("\"" + original + "\"-\"" + translation + "\" overrode previous pair \"" + key + "\"-\"" + oldValue + "\".");
                    }
                }
            }
        };
        thread.run();
    }

    /**
     * Method that removes a word or phrase from a dictionary by key
     * @param original word or phrase in an original language (key)
     */
    public void removeFromDictionary(String original, int languageIndex) {
        String oldValue = dictionaries.get(languageIndex).remove(original.toLowerCase());
        if (oldValue!=null) {
            System.out.println("\""+original+"\"-\""+oldValue+"\" removed.");
        } else {
            System.out.println("\""+original+"\" was not in the dictionaries, so nothing was removed.");
        }
    }

    /**
     * Method that removes a word or phrase from dictionaries by value
     * @param translation translation (value)
     */
    public void removeFromDictionaryByValue(String translation, int languageIndex) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                ArrayList<String> keysToRemove = new ArrayList<>();
                for (Map.Entry<String, String> entry : dictionaries.get(languageIndex).entrySet()) {
                    if (entry.getValue().equals(translation.toLowerCase())) {
                        keysToRemove.add(entry.getKey());
                    }
                }
                for (String key : keysToRemove) {
                    dictionaries.get(languageIndex).remove(key);
                }
                if (keysToRemove.size() == 0) {
                    System.out.println("\"" + translation + "\" was not in the dictionaries, so nothing was removed.");
                }
            }
        };
        thread.run();
    }

    /**
     * Method that prints the dictionaries to a console
     */
    public void printDictionaty(int languageIndex) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (Map.Entry<String, String> entry : dictionaries.get(languageIndex).entrySet()) {
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                }
            }
        };
        thread.run();
    }

    public HashMap<String, String> getDictionary(int languageIndex) {
        return dictionaries.get(languageIndex);
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
     * Method to retrieve phrasalVerbs
     *
     * @return value of phrasalVerbs
     */
    public String[] getPhrasalVerbs() {
        return phrasalVerbs;
    }

    /**
     * Method that reads dictionaries file to a dictionaries
     */
    public void readFile(int languageIndex) {
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
                        fileReader = new FileReader(languageFileNames[languageIndex] + ".txt");
                        bufferedReader = new BufferedReader(fileReader);
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] words = line.split("\t", 2);
                            addToDictionary(words[0].toLowerCase(), words[1].toLowerCase(), languageIndex);
                        }
                        bufferedReader.close();

                        if (languageIndex==0) {
                            //exceptions
                            fileReader = new FileReader(languageFileNames[languageIndex] + "-exceptions.txt");
                            bufferedReader = new BufferedReader(fileReader);
                            line = "";
                            while ((line = bufferedReader.readLine()) != null) {
                                exceptions.add(line);
                            }
                            bufferedReader.close();

                            //phrasal verbs
                            fileReader = new FileReader("phrasalVerbs.txt");
                            bufferedReader = new BufferedReader(fileReader);
                            line="";
                            while ((line = bufferedReader.readLine()) != null) {
                                String[] words = line.split("\t", 2);
                                addToDictionary(words[0].toLowerCase(), words[1].toLowerCase(), languageIndex);
                            }
                            bufferedReader.close();
                        }

                    } catch (IOException e) {
                        System.out.println("Error reading file");
                    } finally {
                        fileRead = true;
                        isReading = false;
                        System.out.println(languageIndex+" - File read. Size: " + dictionaries.get(languageIndex).size());
                        performPending(-1, languageIndex);
                    }
                }
            };
            thread.run();
        } else {
            pendingRead = true;
        }
    }

    /**
     * Method that writes a dictionaries to a file
     */
    public void writeFile(int languageIndex) {
        if (!isReading) {
            isWriting = true;
            pendingWrite = false;
            Thread thread = new Thread() {
                @Override
                public void run() {

                    try {
                        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(languageFileNames[languageIndex]+".txt"), StandardCharsets.UTF_8));

                        for (Map.Entry<String, String> entry : dictionaries.get(languageIndex).entrySet()) {
                            out.write(entry.getKey() + "\t" + entry.getValue()+"\n");
                        }

                        out.close();
                    } catch (IOException e) {
                        System.out.println("Error writing file");
                    } finally {
                        isWriting = false;
                        System.out.println("File written");
                        performPending(languageIndex, -1);
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
    private void performPending(int languageIndex, int languageIndex2) {
        if (pendingWrite && languageIndex!=-1) {
            writeFile(languageIndex);
        }
        if (pendingRead && languageIndex2!=-1) {
            readFile(languageIndex2);
        }
    }

    public void flipDictionary(int languageIndex) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                HashMap<String, String> dictionaryNew = new HashMap<>();
                for (Map.Entry<String, String> entry : dictionaries.get(languageIndex).entrySet()) {
                    dictionaryNew.put(entry.getValue(), entry.getKey());
                }
                dictionaries.set(languageIndex, dictionaryNew);
                System.out.println("Flipped successfully.");
            }
        };
        thread.run();
    }
}
