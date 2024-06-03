package src.txtparsing;
import src.utils.IO;

import java.util.ArrayList;
import java.util.List;

public class TxtParsing{

    /**
     * reads the file + splits + puts it in an id-body format
     * @param file the filename
     * @return an ArrayList with the documents in the txt*/
    public static List<DocumentData> parse(String file){
        String txt_file = IO.txtToString(file); //entire file to string
        String[] docs = txt_file.split("///"); // id and body combined

        List<DocumentData> docData = new ArrayList<DocumentData>();
        for(String doc : docs){
            String[] split_docs = doc.trim().split("\n",2);
            docData.add(new DocumentData(split_docs[0],split_docs[1]));
        }
        return docData;
    }

    /**
     * reads the file with the queries and extracts them
     * @param file the filename
     * @return an ArrayList with only the queries*/
    public static List<QueryData> extractQueries(String file){
        String txt_file = IO.txtToString(file);
        String[] unprocessedQueries = txt_file.split("///");

        List<QueryData> queries = new ArrayList<QueryData>();

        for(String q : unprocessedQueries){
           String[] fullQuery = q.trim().split("\n");
           queries.add(new QueryData(fullQuery[0],fullQuery[1]));

        }
        return queries;

    }
}