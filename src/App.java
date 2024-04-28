package src;

public class App {
    /*Runs the entire process */
    public static void main(String[] args){

        //create the index for the file
        String filepath = "docs//documents.txt";
        String indexLocation = ("index");
        IndexCreation ourIndex = new IndexCreation(filepath,indexLocation);

        //search in the index using queries

        String filepathQ = "docs//queries.txt";
        String fieldName = "body";
        int k = 50; // the number of most relevant documents we want per query
        IndexSearch ourSearch = new IndexSearch(indexLocation,fieldName,k,filepathQ);
    }
}
