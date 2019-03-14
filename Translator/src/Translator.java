import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class Translator {

    private HashMap<String, String> dictionary;
    private boolean fileRead;
    private boolean isReading;
    private boolean isAddNewWordsToDictOptionEnabled; //TODO: react to this option

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

            String[] words = text.toString().split(" "); //TODO: better splitting algorithm (phrases + punctuation)
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

    public void addToDictionary(String original, String translation) {
        String oldValue = dictionary.put(original.toLowerCase(), translation.toLowerCase());
        if (oldValue!=null) {
            System.out.println("\""+original+"\"-\""+translation+"\" overrode previous pair \""+original+"\"-\""+oldValue+"\".");
        } else {
            System.out.println("\""+original+"\"-\""+translation+"\" added.");
        }
        writeFile();
    }

    public void removeFromDictionary(String original) {
        String oldValue = dictionary.remove(original.toLowerCase());
        if (oldValue!=null) {
            System.out.println("\""+original+"\"-\""+oldValue+"\" removed.");
        } else {
            System.out.println("\""+original+"\" was not in the dictionary, so nothing was removed.");
        }
        writeFile();
    }

    public void printDictionaty() {
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public void removeFromDictionaryByValue(String translation) {
        //TODO: iterate through hashmap (dictionary) and remove element once value matches translation
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

    private void readFile() {
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
                    //TODO: ensure correct encoding/locale

                    String line;

                    while((line = bufferedReader.readLine()) != null) {
                        String[] words = line.split("\t",2);
                        dictionary.put(words[1].toLowerCase(),words[0].toLowerCase());
                        //TODO: ensure 1:1 relationship, possibly implement this in addToDictionary and use it here
                    }

                    bufferedReader.close();
                } catch (IOException e) {
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

    private void writeFile() {
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
        //TODO: if isReading then write file asap when it turns false
        //one way to implement this would be to create a variable isWaitingToWrite
        //and a wrapper method for setIsReading which then checks if isWaitingToWrite when called
        //and calls this method
    }
}
