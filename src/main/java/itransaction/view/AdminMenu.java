package itransaction.view;

import itransaction.model.Account;
import itransaction.model.AdminOptions;
import itransaction.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class AdminMenu {
    List<Customer> customers = new ArrayList<>();

    public AdminMenu(List<Customer> customers){
        this.customers = customers;
    }

    public static void printAdminDashboard(){
        for (AdminOptions option : AdminOptions.values()){
            System.out.println(option.getCount() + ". " + option.getOptionName());
        }
    }

    public void printCustomers(){
        for (Customer customer : customers){
            System.out.println(customer);
        }
        System.out.println();
    }

    public void printAccounts(){
        for (Customer customer : customers){
            List<Account> customerAccounts = customer.getAccounts();
            if (customerAccounts.isEmpty()){
                System.out.println("model.Customer has no accounts");
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

    public void updateCustomer(Customer customer){
        if (customer != null) {
            System.out.println("Leave input blank if you wish to keep the original value");
            String username = RetrieveInput.readString("Enter new username (" + customer.getName() + "): ");
            String password = RetrieveInput.readString("Enter new password (" + customer.getPassword() + "): ");

            if (!username.strip().isBlank()) {
                customer.setUsername(username);
            }
            if (!password.strip().isBlank()) {
                customer.setPassword(password);
            }
        }

        else{
            System.out.println("model.Customer selected is invalid.");
        }
    }

    public void deleteCustomer(Customer customer){
        customers.remove(customer);
    }
}