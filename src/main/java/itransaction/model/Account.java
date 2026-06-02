package itransaction.model;

public abstract class Account {
    String id;
    double balance;
    Customer accountHolder;

    Account(String accountNumber, Customer accountHolder, double balance){
        this.id = accountNumber;
        this.balance = balance;
        this.accountHolder = accountHolder;
    }

    abstract double addInterest();

    public abstract void deposit(double amount);

    public abstract double withdraw(double amount);

    public abstract String getAccountType();

    public abstract double getBalance();

    public abstract String getId();

    abstract boolean overdraftLimit(double amount);
}
