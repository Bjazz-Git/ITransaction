package itransaction.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "customer")
public class Customer extends User {
    @Id
    private String id;
    private String name;
    private List<Account> accounts;
//    @Field("customer_username")
//    private String username;
//    @Field("customer_password")
//    private String password;

    public Customer(String id, String name, String username, String password, List<Account> accounts) {
        super(username, password);
        this.id = id;
        this.name = name;
        this.accounts = accounts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "model.Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accounts=" + accounts +
                '}';
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

//    public ObjectId getObjectId() {
//        return objectId;
//    }
//
//    public void setObjectId(ObjectId objectId) {
//        this.objectId = objectId;
//    }
}
