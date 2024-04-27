package src;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.FSDirectory;
import src.txtparsing.TxtParsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;

public class IndexSearch {

    /** this constructor creates indexReader -- used to parse the index
     * and an indexSearcher to search for documents in the index
     * @param indexLocation the directory our index is stored
     * @param fieldName the document fieldname we want to search -- body
     * @param k the number of most relevant documents to be returned*/
    public IndexSearch(String indexLocation,String fieldName,int k){
        try {
            //the reader gives us access to the index
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexLocation)));
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            //we need the same analyzer and similarity as the index
            indexSearcher.setSimilarity(new ClassicSimilarity());

            //search the index
            search(indexSearcher,fieldName,k);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /** performs search according to the queries given for a specific field.
     * writes the results in a .txt file
     * @param indexSearcher the index searcher to be used
     * @param fieldName the name of the field we want to search --body
     * @param k the number of most relative to be returned
     *  */
    private void search(IndexSearcher indexSearcher, String fieldName,int k){
        try{
            // define which analyzer to use for the normalization of user's query
            Analyzer analyzer = new EnglishAnalyzer();

            // create a query parser on the field "body"
            QueryParser parser = new QueryParser(fieldName, analyzer);

            //read queries from queries.txt file
            List<String> queries = TxtParsing.extractQueries("docs//queries.txt");

            // search the index using the indexSearcher
            for(String q : queries) {
                //parse each query according to QueryParser
                Query query = parser.parse(q);
                System.out.println("Searching for: " + query.toString(fieldName));

                // search the index using the indexSearcher
                TopDocs results = indexSearcher.search(query, 50);
                ScoreDoc[] hits = results.scoreDocs;
                long numTotalHits = results.totalHits;
                System.out.println(numTotalHits + " total matching documents");

                //print and write the results to file
                for (ScoreDoc hit : hits) {
                    Document hitDoc = indexSearcher.doc(hit.doc);
                    System.out.println("\tScore "+hit.score +"\tid="+hitDoc.get("id"));

                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
