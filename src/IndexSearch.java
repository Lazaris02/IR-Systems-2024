package src;

import org.apache.lucene.search.similarities.Similarity;
import src.utils.IO;

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
import src.txtparsing.QueryData;
import src.txtparsing.TxtParsing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class IndexSearch {
    private final String filepathQ;
    private  final String writeFilePath;
    private final String[] methodNames = {"ClassicSim","B25","LMJ"};

    /** this constructor creates indexReader -- used to parse the index
     * and an indexSearcher to search for documents in the index
     * @param indexLocation the directory our index is stored
     * @param fieldName the document field name we want to search -- body
     * @param k the number of most relevant documents to be returned*/
    public IndexSearch(String indexLocation, String fieldName, int k, String filepathQ, Similarity similarity,int sim){
        this.filepathQ = filepathQ;
        this.writeFilePath = "docs//ourResults_".concat(methodNames[sim]+".txt");
        try {
            //the reader gives us access to the index
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexLocation)));
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            //we need the same analyzer and similarity as the index
            indexSearcher.setSimilarity(similarity);

            //search the index
            search(indexSearcher,fieldName,k,sim);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /** performs search according to the queries given for a specific field.
     * writes the results in a .txt file
     * @param indexSearcher the index searcher to be used
     * @param fieldName the name of the field we want to search --body
     * @param k the number of most relative to be returned
     * @param sim to get the name of the sim function
     *  */
    private void search(IndexSearcher indexSearcher, String fieldName,int k,int sim){
        try{
            // define which analyzer to use for the normalization of user's query
            Analyzer analyzer = new EnglishAnalyzer();

            // create a query parser on the field "body"
            QueryParser parser = new QueryParser(fieldName, analyzer);

            //read queries from queries.txt file
            List<QueryData> queries = TxtParsing.extractQueries(filepathQ);
            // search the index using the indexSearcher

            File file = new File(writeFilePath);

            if(file.exists()){
                file.delete();
            } // if the file exists from previous execution it is deleted first.

            for(QueryData q : queries) {
                //parse each query question according to QueryParser
                Query query = parser.parse(q.getQueryQuestion());
                System.out.println("Searching for: " + query.toString(fieldName) + " with id="+q.getQueryId());

                // search the index using the indexSearcher
                TopDocs results = indexSearcher.search(query, 50);
                ScoreDoc[] hits = results.scoreDocs;
                long numTotalHits = results.totalHits;
                System.out.println(numTotalHits + " total matching documents");

                //write the results to file -- queries already sorted by queryId
                for (ScoreDoc hit : hits) {
                    Document hitDoc = indexSearcher.doc(hit.doc);
                    IO.writeToFile(q.getQueryId(),hitDoc.get("id"),hit.score,writeFilePath,methodNames[sim]);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
