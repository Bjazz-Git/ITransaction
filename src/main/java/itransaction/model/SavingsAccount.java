package itransaction.model;

public class SavingsAccount extends Account{
    String accountType = "SavingsAccount";

    public SavingsAccount(String id, Customer customer, double balance){
        super(id, customer, balance);
    }

    @Override
    double addInterest() {
        return 0;
    }

    @Override
    public void deposit(double amount) {
        // Print error message if deposit amount is less than 1
        if (amount < 1){
            System.out.println(amount + " is not a valid withdraw amount");
        }

        // If deposit amount is valid, deposit amount into balance
        else{
            balance += amount;
        }
    }

    @Override
    public double withdraw(double amount) {
        // Print error message if withdraw amount is less than 1
        if (amount < 1){
            System.out.println(amount + " is not a valid withdraw amount");
        }

        // If balance withdraw would cause balance to below 100, print error message
        else if (overdraftLimit(amount)){
            System.out.println("Invalid. Withdrawing this much would push account below $100.");
        }

        // If withdraw amount is valid, withdraw amount from account
        else{
            balance -= amount;
            return amount;
        }

        // If this is reached then the withdrawal was unsuccessful
        return -1;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    boolean overdraftLimit(double amount) {
        return balance - amount < 100;
    }


    @Override
    public String toString() {
        return "model.SavingsAccount{id=" + id + ", balance=$" + balance + "}";
    }
}
