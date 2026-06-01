import java.util.ArrayList;
import java.util.List;

public class AdminMenu {
    List<Customer> customers = new ArrayList<>();

    AdminMenu(List<Customer> customers){
        this.customers = customers;
    }

    public static void printAdminDashboard(){
        System.out.println("1. See all customers");
        System.out.println("2. See all accounts");
        System.out.println("3. Delete an account");
        System.out.println("4. Exit");
    }

    public void printCustomers(){
        for (Customer customer : customers){
            System.out.println(customer);
        }
    }

    public void printAccounts(){
        for (Customer customer : customers){
            List<Account> customerAccounts = customer.getAccounts();
            if (customerAccounts.isEmpty()){
                System.out.println("Customer has no accounts");
            }

            for (int i = 0; i < customerAccounts.size(); i++){
               if (customerAccounts.get(i).getAccountType().equals("CheckingsAccount")) {
                   printCheckingAccount(customerAccounts.get(i));
               }
            }
        }
    }

    public void printCheckingAccount(Account account){
        if (account.getAccountType().equals("CheckingsAccount")) {
            System.out.println("Checking Account balance $" + account.getBalance());
        }

        else{
            System.out.println("Saving Account balance $" + account.getBalance());
        }
    }

    public void deleteAccount(){
        int customerIdx = 0;
        int accountIdx = 0;
        Customer customer = customers.get(customerIdx);
        Account account = customer.getAccounts().get(accountIdx);

        List<Account> accounts = customer.getAccounts();
        accounts.remove(0);
//        for (Account currentAccount : accounts){
//            if (currentAccount == account){
////                currentAccount
//            }
//        }
    }

    private Customer getCustomer(){
        printCustomers();
        System.out.println("Enter the id of the customer you want to select.");
        return null;
    }
}
