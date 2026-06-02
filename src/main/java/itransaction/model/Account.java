package itransaction.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use=JsonTypeInfo.Id.NAME,
        include=JsonTypeInfo.As.EXISTING_PROPERTY,
        property="accountType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CheckingsAccount.class, name = "CheckingsAccount"),
        @JsonSubTypes.Type(value = SavingsAccount.class, name = "SavingsAccount")
})
public abstract class Account {
    String id;
    double balance;
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
}
