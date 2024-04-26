package src.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IO{

    /**
     * Reads entire file into a String
     * @param filename the name of the file
     * @return  String */
    public static String txtToString(String filename){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filename));
            scanner.useDelimiter("\\A");
            return scanner.next();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}