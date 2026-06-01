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

    }

    public void deleteAccount(){

    }
}
