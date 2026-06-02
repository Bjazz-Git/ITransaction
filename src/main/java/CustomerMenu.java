import java.util.List;

public class CustomerMenu {
    Customer customer;

    CustomerMenu(Customer customer){
       this.customer = customer;
    }

    public static void printCustomerDashboard(){
        for (CustomerOptions option : CustomerOptions.values()){
            System.out.println(option.getCount() + ": " + option.getOption());
        }
        System.out.println();
    }

    public void printAccounts(){
        for (Account account: customer.getAccounts()){
            System.out.println(account);
        }
        System.out.println();
    }

    public void printBalance(){
        for (Account account: customer.getAccounts()){
            System.out.println(account.getAccountType() + " balance: $" + account.getBalance());
        }
        System.out.println();
    }

    public double withdraw(){
        printAccounts();
        String id = RetrieveInput.readString("Enter the id of the account you wish to withdraw from:");
        List<Account> accounts = customer.getAccounts();

        Account accountToWD = null;
        for(Account account : accounts){
            if (account.getId().equals(id)){
                accountToWD = account;
            }
        }

        if (accountToWD != null) {
            double withdrawAmount = RetrieveInput.readDouble("How much do you wish to withdraw: ");
            return accountToWD.withdraw(withdrawAmount);
        }

        // Error occurred when withdrawing
        return -1;
    }

    public void deposit(){
        printAccounts();
        String id = RetrieveInput.readString("Enter the id of the account you wish to deposit to:");
        List<Account> accounts = customer.getAccounts();

        Account accountToWD = null;
        for(Account account : accounts){
            if (account.getId().equals(id)){
                accountToWD = account;
            }
        }

        if (accountToWD != null) {
            double depositAmount = RetrieveInput.readDouble("How much do you wish to deposit: ");
            accountToWD.deposit(depositAmount);
        }
    }
}
