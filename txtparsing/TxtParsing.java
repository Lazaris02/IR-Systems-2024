package txtparsing;
import utils.IO;

import java.util.ArrayList;
import java.util.List;

public class TxtParsing{

    /**
     * reads the file + splits + puts it in an id-body format
     * @param file The filename
     * @return an ArrayList with the documents in the txt*/
    public static List<DocumentData> parse(String file){
        String txt_file = IO.txtToString(file); //entire file to string
        String[] docs = txt_file.split("///"); // id and body combined

        List<DocumentData> docData = new ArrayList<DocumentData>();
        for(String doc : docs){
            String[] split_docs = doc.split("\n",1);
            docData.add(new DocumentData(split_docs[0],split_docs[1]));
        }
        return docData;
    }
}