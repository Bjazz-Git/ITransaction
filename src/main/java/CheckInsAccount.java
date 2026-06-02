public class CheckInsAccount extends Account{
    String accountType = "CheckingsAccount";
    double overdraftLimit;

    CheckInsAccount(String id, Customer customer, double balance, double overdraftLimit){
        super(id, customer, balance);
        this.overdraftLimit = overdraftLimit;

    }

    @Override
    double addInterest() {
        return 0;

    }

    @Override
    void deposit(double amount) {
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
    double withdraw(double amount) {
        // Print error message if withdraw amount is less than 1
        if (amount < 1){
            System.out.println(amount + " is not a valid withdraw amount");
        }

        // If balance withdraw would cause balance to below overdraft limit, print error message
        else if (overdraftLimit(amount)){
            System.out.printf("Invalid. Withdrawing this much would push account below %.2f.", overdraftLimit);
            System.out.println();
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
    String getAccountType() {
        return accountType;
    }

    @Override
    double getBalance() {
        return balance;
    }

    @Override
    String getId() {
        return id;
    }

    @Override
    boolean overdraftLimit(double amount) {
        return balance - amount < overdraftLimit;
    }

    @Override
    public String toString() {
        return "CheckInsAccount{id=" + id + ", balance=$" + balance + "}";
    }
}
