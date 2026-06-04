package itransaction.model;

import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonTypeInfo(
        use=JsonTypeInfo.Id.NAME,
        include=JsonTypeInfo.As.EXISTING_PROPERTY,
        property="accountType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CheckingsAccount.class, name = "CheckingsAccount"),
        @JsonSubTypes.Type(value = SavingsAccount.class, name = "SavingsAccount")
})
@Document(collection = "accounts")
public abstract class Account {
    @Id
    String id;
    double balance;
    // Prevents infinite loop of child and parent data
    @JsonIgnore
    Customer accountHolder;

    Account(String accountNumber, Customer accountHolder, double balance){
        this.id = accountNumber;
        this.balance = balance;
        this.accountHolder = accountHolder;
    }

    Account(){

    }

    abstract double addInterest();

    public abstract void deposit(double amount);

    public abstract double withdraw(double amount);

    public abstract String getAccountType();

    public abstract double getBalance();

    public abstract String getId();

    abstract boolean overdraftLimit(double amount);

    public abstract Customer getAccountHolder();

    public abstract void setAccountHolder(Customer customer);
}
