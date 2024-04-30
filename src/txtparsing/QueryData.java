package src.txtparsing;

public class QueryData {
    private final String queryId;
    private final String queryQuestion;

    public QueryData(String queryId,String queryQuestion){
        this.queryId = queryId;
        this.queryQuestion = queryQuestion;
    }

    public String getQueryId(){return  this.queryId;}
    public String getQueryQuestion(){return  this.queryQuestion;}
}
