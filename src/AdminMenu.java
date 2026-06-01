import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    List<Customer> customers = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

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

            System.out.println(customer.getUsername() + "'s accounts");
            for (int i = 0; i < customerAccounts.size(); i++){
                System.out.println(customerAccounts.get(i));
            }

            System.out.println();
        }
    }

    public void deleteAccount(Customer customer, Account account){
        if (customer != null && account != null) {
            customer.getAccounts().remove(account);
        }
    }
}
