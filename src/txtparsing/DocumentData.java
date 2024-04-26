package src.txtparsing;

public class DocumentData {
    /*This class will store the data for each Document*/
    private String id;
    private String body;

    public DocumentData(String id,String body){
        this.id = id;
        this.body = body;
    }

    /*Setters and Getters*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}