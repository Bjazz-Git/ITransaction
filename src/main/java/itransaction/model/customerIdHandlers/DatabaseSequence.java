package itransaction.model.customerIdHandlers;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
public class DatabaseSequence {
    @Id
    private String id;
    private int seq;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getSeq() { return seq; }
    public void setSeq(int seq) { this.seq = seq; }
}