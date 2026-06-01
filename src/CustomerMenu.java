import java.util.List;

public class CustomerMenu {
    Customer customer;

    CustomerMenu(Customer customer){
       this.customer = customer;
    }

    public static void printCustomerDashboard(){
        System.out.println("1. Check account");
        System.out.println("2. Check account balance");
        System.out.println("3. Exit");
    }

    public void printAccount(){
        List<Account> accounts = customer.getAccounts();
    }

    public void printBalance(){

    }
}
