abstract class Account {
    String id;
    double balance;
    Customer accountHolder;

    Account(String accountNumber, Customer accountHolder, double balance){
        this.id = id;
        this.balance = balance;
        this.accountHolder = accountHolder;
    }

    abstract double addInterest();

    abstract void Deposit(double amount);

    abstract String getAccountType();

    abstract double getBalance();
}
