package itransaction.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "customer")
public class Customer extends User {
    @Id
    private int id;
    @Field("mongo_id")
    private String mongoId = new ObjectId().toString();
    private String name;
    @DocumentReference
    private List<Account> accounts;

    public Customer(int id, String name, String username, String password, List<Account> accounts) {
        super(username, password);
        this.id = id;
        this.name = name;
        this.accounts = accounts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

//    public void addAccounts(Account account){
//        if (this.accounts == null){
//            this.accounts = new ArrayList<>();
//        }
//        this.accounts.add(account);
//        account.setAccountHolder(this);
//    }

    @Override
    public String toString() {
        return "model.Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
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

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
}
