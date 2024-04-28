package src;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import src.txtparsing.DocumentData;
import src.txtparsing.TxtParsing;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class IndexCreation {

    /**
     * creates an index for the given .txt file
     * @param filepath the path of the .txt file
     * @param indexLocation the location that the index will be stored*/
    public IndexCreation(String filepath,String indexLocation) {
    try {
        Directory dir = FSDirectory.open(Paths.get(indexLocation));
        Analyzer analyzer = new EnglishAnalyzer(); //normalizes the documents

        Similarity similarity = new ClassicSimilarity(); // our retrieval model

        //index writer configuration
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setSimilarity(similarity);

        // Create a new index in the directory, removing any
        // previously indexed documents:
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        IndexWriter indexWriter = new IndexWriter(dir,config); // our index writer

        List<DocumentData> docs = TxtParsing.parse(filepath);

        //for each doc create the Lucene Documents + use index writer
        for(DocumentData d : docs){
            createIndexDoc(d,indexWriter);
        }

        indexWriter.close();

        System.out.println("\n All the documents succesfully added in Index!");

    }catch(IOException e){
        System.out.println(" caught a " + e.getClass() +
                "\n with message: " + e.getMessage());

    }

    }


    /**
     * @param d the individual documents in the .txt
     * @param indexWriter the index writer that will write the Lucene document
     * */
    private void createIndexDoc(DocumentData d, IndexWriter indexWriter) {

        try{
            Document doc = new Document();
            //create the fields to store the id and the body of the document
            StoredField id = new StoredField("id",d.getId());
            doc.add(id);

            TextField body = new TextField("body",d.getBody(), Field.Store.NO);
            doc.add(body);

            if(indexWriter.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE){
                System.out.println("adding" + d);
                indexWriter.addDocument(doc);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
