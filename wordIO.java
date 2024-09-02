import java.util.*;
import java.io.*;

public class wordIO {

    private static ArrayList<String> wordsList = new ArrayList<>();
    
    static Scanner infile = null; 

    public wordIO() {
        try {
            infile = new Scanner(new FileReader("./src/wordlelist.txt"));

            if (infile != null) {
                while (infile.hasNext()) {
                    String wordFromList = infile.next();
                    Collections.addAll(wordsList, wordFromList);
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0); // exits entire program
        } finally {
            if (infile != null) {
                infile.close();
            }
        }
    }

    public ArrayList<String> getWordList() {
        return wordsList;
    }


    public String getWordFromWordIO() {
        String word = wordsList.get(new Random().nextInt(wordsList.size()));
        return word;
    }



}
