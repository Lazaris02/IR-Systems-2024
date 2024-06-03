package src;

import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.LMJelinekMercerSimilarity;
import org.apache.lucene.search.similarities.Similarity;

public class App {
    /*Runs the entire process */
    public static void main(String[] args){

        //create the index for the file
        String filepath = "docs//documents.txt";
        String indexLocation = ("index");

        //decide the similarity function
        int sim = 0;
        Similarity similarity = similarityFunction(sim);

        IndexCreation ourIndex = new IndexCreation(filepath,indexLocation,similarity);

        //search in the index using queries

        String filepathQ = "docs//queries.txt";
        String fieldName = "body";
        int k = 50; // the number of most relevant documents we want per query
        IndexSearch ourSearch = new IndexSearch(indexLocation,fieldName,k,filepathQ,similarity,sim);
    }
    private static Similarity similarityFunction(int sim){
        /*sim == 0 Classic similarity
         * sim == 1 BM25 similarity
         * sim == 2 LMJ similarity*/
        if(sim == 0){
            return new ClassicSimilarity();
        }
        if(sim == 1){
            return new BM25Similarity(1.4f,0.76f);
        }

        if(sim == 2){
            return new LMJelinekMercerSimilarity(0.7f);
        }
        return null;

    }

}
