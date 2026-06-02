public enum CustomerOptions {
    CHECKACCOUNT(1, "Check Account"),
    CHECKACCOUNTBALANCE(2, "Check Account Balance"),
    WITHDRAW(3, "Withdraw from account"),
    DEPOSIT(4, "Deposit into account"),
    EXIT(5, "Exit");

    int count;
    String option;

    public int getCount() {
        return count;
    }

    public String getOption() {
        return option;
    }

    CustomerOptions(int count, String option){
        this.count = count;
        this.option = option;
    }
}
