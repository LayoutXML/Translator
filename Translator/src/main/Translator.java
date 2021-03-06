package main;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
    private boolean turboMode; //when false ensures 1:1 relationship between languages but is slower, affect is the same as flipping dictionary twice
    private String[] languageFileNames = {"lithuanian","swedish","albanian"};
    private String[] phrasalVerbs = {"up","down","off","out","in"};
    private boolean[] flipped = {false,false,false};

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
     * Method that returns translation for a given word or phrase
     * @param original words in an original language (key)
     * @param languageIndex language index
     * @return translation (value)
     */
    public String translate(String original, int languageIndex) {
        if (!exceptions.contains(original.toLowerCase()) || flipped[languageIndex]) { //checks if a word is not in an exceptions list (if original is english)
            String translation = dictionaries.get(languageIndex).get(original.toLowerCase()); //find a translation
            if (translation == null) { //if translation does not exist
                if (languageIndex==0) { //if lithuanian (the only language with phrasal verbs)
                    String[] words = original.split("\\P{L}+"); //splits input into words
                    StringBuilder processed = new StringBuilder();
                    for (String word : words) {
                        int indexOf = original.indexOf(word);
                        String characters = original.substring(0, indexOf);
                        original = original.substring(indexOf + word.length());
                        if (!exceptions.contains(word.toLowerCase()) || flipped[languageIndex]) {
                            String wordTranslated = dictionaries.get(languageIndex).get(word.toLowerCase()); //translates each word
                            if (wordTranslated==null) {
                                processed.append(characters).append(word);
                            } else {
                                processed.append(characters).append(wordTranslated);
                            }
                        }
                    }
                    return processed.toString();
                } else {
                    return original;
                }
            } else {
                return translation;
            }
        }
        return "";
    }

    /**
     * Method that reads and translates a file (to a console or jtextpane)
     * @param fileName file name
     * @param languageIndex language index
     * @param area1 left area
     * @param area2 right area
     */
    public void translateFile(String fileName, int languageIndex, JTextPane area1, JTextPane area2) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                FileReader fileReader;
                BufferedReader bufferedReader;
                StringBuilder text = new StringBuilder(); //StringBuilder for better performance (appending text in loop)
                try {
                    fileReader = new FileReader(fileName);
                    bufferedReader = new BufferedReader(fileReader);

                    String line, translation, characters = "", lastTranslation="", lastOriginalWord="";
                    int indexOf;
                    boolean error = false, isFirst = true, lastEmpty=false;

                    while ((line = bufferedReader.readLine()) != null) {
                        text.append("\n").append(line); //reads text line by line
                    }

                    bufferedReader.close();

                    if (area1!=null) {
                        area1.setText(text.toString().substring(1));
                    }

                    String input = text.toString();
                    StringBuilder translationFinal = new StringBuilder();

                    String[] words = text.substring(1).split("\\P{L}+");
                    long startTime = Calendar.getInstance().getTimeInMillis();
                    for (String word : words) {
                        //this is for saving characters between the words
                        if (!error) {
                            indexOf = input.indexOf(word);
                            if (indexOf == -1) {
                                error = true;
                            } else {
                                characters = input.substring(0, indexOf);
                                input = input.substring(indexOf + word.length());
                            }
                        }
                        //this is for checking if it's a phrasal verb
                        boolean phrasalVerb = false;
                        for (String phrase : phrasalVerbs) {
                            if (word.toLowerCase().equals(phrase)) {
                                phrasalVerb = true;
                            }
                        }
                        boolean capitalize = lastEmpty && (lastTranslation.contains(".") || lastTranslation.contains("?") || lastTranslation.contains("!"));
                        if (phrasalVerb && languageIndex==0) {
                            translation = translate(lastOriginalWord + " " + word, languageIndex); //if phrasal word (out of two words), takes the previous word to form a phrase
                        } else {
                            if (area2==null) {
                                System.out.print(lastTranslation);
                            } else {
                                translationFinal.append(lastTranslation);
                            }
                            translation = translate(word, languageIndex);
                        }
                        lastTranslation="";
                        if (isFirst || characters.contains(".") || characters.contains("?") || characters.contains("!") || capitalize) {
                            if (translation.length() > 0) {
                                translation = translation.substring(0, 1).toUpperCase() + translation.substring(1); //capitalizes words when needed
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
                    if (area2==null) {
                        System.out.print(lastTranslation + input);
                    } else {
                        translationFinal.append(lastTranslation).append(input);
                    }
                    if (area2 != null) {
                        area2.setText(translationFinal.toString().substring(1));
                    }
                    long endTime = Calendar.getInstance().getTimeInMillis();
                    double wordsPerSecond = words.length * (1d / (endTime - startTime) * 1000);
                    System.out.format("\n\nSpeed: %.2f words per second. (It took "
                            + (endTime - startTime) + "ms (" + (endTime - startTime) / 1000d + " seconds) to translate " + words.length + " words)\n",wordsPerSecond);
                    JOptionPane.showMessageDialog(null, "\n\nSpeed: " + wordsPerSecond + " words per second. (It took "
                            + (endTime - startTime) + "ms (" + (endTime - startTime) / 1000d + " seconds) to translate " + words.length + " words)\n");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error reading file");
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
     * @param languageIndex language index
     * @param showMessage show message in a popup window
     */
    public void addToDictionary(String original, String translation, int languageIndex, boolean showMessage) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean found = false, showedMsg = false;
                String key = "";
                //if turbo mode is off, ensures 1:1 relationship by checking each word (hasmap entry value) if it already exists
                //when turbo mode is off, adding words to dictionary is slower and undermines the whole purpose of hashmap (fast data access)
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
                        if (showMessage) {
                            JOptionPane.showMessageDialog(null, "\"" + original + "\"-\"" + translation + "\" overrode previous pair \"" + original + "\"-\"" + oldValue + "\".");
                            showedMsg = true;
                        } else {
                            System.out.println("\"" + original + "\"-\"" + translation + "\" overrode previous pair \"" + original + "\"-\"" + oldValue + "\".");
                        }
                    }
                } else {
                    String oldValue = dictionaries.get(languageIndex).remove(key);
                    dictionaries.get(languageIndex).put(key.toLowerCase(), translation.toLowerCase());
                    if (oldValue != null) {
                        if (showMessage) {
                            JOptionPane.showMessageDialog(null, "\"" + original + "\"-\"" + translation + "\" overrode previous pair \"" + key + "\"-\"" + oldValue + "\".");
                            showedMsg = true;
                        } else {
                            System.out.println("\"" + original + "\"-\"" + translation + "\" overrode previous pair \"" + key + "\"-\"" + oldValue + "\".");
                        }

                    }
                }
                if (!showedMsg && showMessage) {
                    JOptionPane.showMessageDialog(null, "\"" + original + "\"-\"" + translation + "\" added.");
                }
            }
        };
        thread.run();
    }

    /**
     * Method that removes a word or phrase from a dictionary by key
     * @param original word or phrase in an original language (key)
     * @param languageIndex language index
     * @param showMessage show message in a popup window
     */
    public void removeFromDictionary(String original, int languageIndex, boolean showMessage) {
        String oldValue = dictionaries.get(languageIndex).remove(original.toLowerCase());
        if (oldValue!=null) {
            if (showMessage) {
                JOptionPane.showMessageDialog(null, "\""+original+"\"-\""+oldValue+"\" removed.");
            } else {
                System.out.println("\""+original+"\"-\""+oldValue+"\" removed.");
            }
        } else {
            if (showMessage) {
                JOptionPane.showMessageDialog(null, "\"" + original + "\" was not in the dictionary, so nothing was removed.");
            } else {
                System.out.println("\"" + original + "\" was not in the dictionary, so nothing was removed.");
            }
        }
    }

    /**
     * Method that removes a word or phrase from dictionaries by value
     * @param translation translation (value)
     * @param languageIndex language index
     * @param showMessage show message in a popup window
     */
    public void removeFromDictionaryByValue(String translation, int languageIndex, boolean showMessage) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                ArrayList<String> keysToRemove = new ArrayList<>(); //arraylist because if not 1:1 relationship, there might be multiple keys with the same value
                for (Map.Entry<String, String> entry : dictionaries.get(languageIndex).entrySet()) {
                    if (entry.getValue().equals(translation.toLowerCase())) {
                        keysToRemove.add(entry.getKey());
                    }
                }
                for (String key : keysToRemove) {
                    dictionaries.get(languageIndex).remove(key);
                }
                if (keysToRemove.size() == 0) {
                    if (showMessage) {
                        JOptionPane.showMessageDialog(null, "\"" + translation + "\" was not in the dictionary, so nothing was removed.");
                    } else {
                        System.out.println("\"" + translation + "\" was not in the dictionary, so nothing was removed.");
                    }
                } else {
                    if (showMessage) {
                        JOptionPane.showMessageDialog(null, "\"" + translation + "\" removed.");
                    } else {
                        System.out.println("\"" + translation + "\" removed.");
                    }
                }
            }
        };
        thread.run();
    }

    /**
     * Method that prints the dictionaries to a console
     * @param languageIndex language index
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

    /**
     * Method that returns a dictionary for a given language
     * @param languageIndex language index
     * @return hasmap - dictionary
     */
    public HashMap<String, String> getDictionary(int languageIndex) {
        return dictionaries.get(languageIndex);
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
     * Method that reads dictionary file to a dictionary hasmap
     * @param languageIndex language index
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
                        //Main dictionary
                        fileReader = new FileReader(languageFileNames[languageIndex] + ".txt");
                        bufferedReader = new BufferedReader(fileReader);
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] words = line.split("\t", 2);
                            addToDictionary(words[0].toLowerCase(), words[1].toLowerCase(), languageIndex, false);
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
                                addToDictionary(words[0].toLowerCase(), words[1].toLowerCase(), languageIndex, false);
                            }
                            bufferedReader.close();
                        }

                    } catch (IOException e) {
                        System.out.println("Error reading file");
                        JOptionPane.showMessageDialog(null, "Error reading file");
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
     * Method that writes a dictionary to a file
     * @param languageIndex language index
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
                            if (flipped[languageIndex]) {
                                out.write(entry.getValue() + "\t" + entry.getKey() + "\n");
                            } else {
                                out.write(entry.getKey() + "\t" + entry.getValue() + "\n");
                            }
                        }

                        out.close();
                    } catch (IOException e) {
                        System.out.println("Error writing file");
                        JOptionPane.showMessageDialog(null, "Error reading file");
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
     * Method that performs pending tasks, for example reading/writing files. Ensures that file writing and reading doesn't occur at the same time
     * @param languageIndex language index
     */
    private void performPending(int languageIndex, int languageIndex2) {
        if (pendingWrite && languageIndex!=-1) {
            writeFile(languageIndex);
        }
        if (pendingRead && languageIndex2!=-1) {
            readFile(languageIndex2);
        }
    }

    /**
     * Method that flips dictionary's keys and values for translation in the opposite direction
     * @param languageIndex language index
     */
    public void flipDictionary(int languageIndex) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                HashMap<String, String> dictionaryNew = new HashMap<>();
                for (Map.Entry<String, String> entry : dictionaries.get(languageIndex).entrySet()) {
                    dictionaryNew.put(entry.getValue(), entry.getKey()); //traverses through the hashmap and for each entry's value adds an entry with the value as a key
                }
                dictionaries.set(languageIndex, dictionaryNew);
                flipped[languageIndex] = !flipped[languageIndex];
            }
        };
        thread.run();
    }
}
