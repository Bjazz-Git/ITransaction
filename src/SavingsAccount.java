public class SavingsAccount extends Account{
    String accountType = "SavingsAccount";
    String id;
    double balance;
    Customer customer;

    SavingsAccount(String id, Customer customer, double balance){
        super(id, customer, balance);
    }

    @Override
    double addInterest() {
        return 0;
    }

    @Override
    void Deposit(double amount) {

    }

    @Override
    String getAccountType() {
        return "";
    }

    @Override
    double getBalance() {
        return 0;
    }
}
