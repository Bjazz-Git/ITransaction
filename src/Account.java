abstract class Account {
    String id;
    double balance;
    Customer accountHolder;

    Account(String accountNumber, Customer accountHolder, double balance){
        this.id = accountNumber;
        this.balance = balance;
        this.accountHolder = accountHolder;
    }

    abstract double addInterest();

    abstract void deposit(double amount);

    abstract double withdraw(double amount);

    abstract String getAccountType();

    abstract double getBalance();

    abstract String getId();

    abstract boolean overdraftLimit(double amount);
}
