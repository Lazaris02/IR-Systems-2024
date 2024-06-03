package src.utils;


import java.io.*;
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

    /**
     * writes the results for each document as a tuple in a file
     * @param queryId the query we write the results for
     * @param docId the document the score refers to
     * @param score how relative the particular document is to the particular query
     * @param writeFileName the file we write the results in
     * */
    public static void writeToFile(String queryId,String docId,float score,String writeFileName,String methodName){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(writeFileName,true));
            //the string we need to write for every result
            String tuple =queryId.trim()+"\t"+"Q0"+"\t"+docId.trim()+"\t0"+"\t"+score+"\t"+methodName+"\n";
            writer.write(tuple);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}