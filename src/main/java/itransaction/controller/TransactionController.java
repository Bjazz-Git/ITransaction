package itransaction.controller;

import itransaction.model.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import itransaction.view.AdminMenu;
import itransaction.view.CustomerMenu;
import itransaction.view.RetrieveInput;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class TransactionController {
    static List<User> users = new ArrayList<>();
    static List<Customer> customers = new ArrayList<>();
    CustomerRepository customerRepository;

    public TransactionController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @GetMapping("")
    List<Customer> getAllCustomers(){
            return customerRepository.getCustomers();
    }

    @GetMapping("/api/users")
    List<User> getAllUsers(){
        return customerRepository.getUsers();
    }

    @GetMapping("/{id}")
    Customer getCustomerById(@PathVariable Integer id){
       return customerRepository.getCustomerById(id);
    }

    private static String login(String username, String password) {
        if (username.equals("admin") && password.equals("admin123")) {
            return "admin";
        }

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user.getUsername();
            }
        }

        return "";

    }


//    private static void adminDashboard() {
//        boolean programOn = true;
//        AdminMenu adminMenu = new AdminMenu(customers);
//
//        while (programOn) {
//            int option = RetrieveInput.readInt("Type the number of one of the above options:");
//
//            switch (AdminOptions.values()[option - 1]) {
//                // See all customers
//                case SEEALLCUSTOMERS:{
//                    adminMenu.printCustomers();
//                    break;
//                }
//                // See all accounts
//                case SEEALLACCOUNTS: {
//                    adminMenu.printAccounts();
//                    break;
//                }
//                // Delete an account
//                case DELETEANACCOUNT: {
//                    adminMenu.printCustomers();
//                    Customer customer = getCustomer();
//                    Account account = getAccount(customer);
//                    adminMenu.deleteAccount(customer, account);
//                    break;
//                }
//                // Update a customer
//                case UPDATECUSTOMER: {
//                    adminMenu.printCustomers();
//                    Customer customer = getCustomer();
//                    adminMenu.updateCustomer(customer);
//                    break;
//                }
//                // Delete a customer
//                case DELETECUSTOMER: {
//                    adminMenu.printCustomers();
//                    Customer customer = getCustomer();
//                    adminMenu.deleteCustomer(customer);
//                    break;
//                }
//                // End program if above input is not provided
//                case EXIT: {
//                    System.out.println("GoodBye.");
//                    programOn = false;
//                    break;
//                }
//            }
//        }
//    }

//    private static void customerDashboard(Customer customer) {
//        boolean programOn = true;
//        CustomerMenu menu = new CustomerMenu(customer);
//
//        System.out.println("Welcome " + customer.getUsername());
//
//        while (programOn) {
//            CustomerMenu.printCustomerDashboard();
//            int option = RetrieveInput.readInt("Type the number of one of the above options:");
//
//            switch (CustomerOptions.values()[option - 1]) {
//                // Check model.Account
//                case CHECKACCOUNT:
//                    menu.printAccounts();
//                    break;
//                // Check balance
//                case CHECKACCOUNTBALANCE:
//                    menu.printBalance();
//                    break;
//                // Withdraw
//                case WITHDRAW:
//                    menu.withdraw();
//                    break;
//                // Deposit
//                case DEPOSIT:
//                    menu.deposit();
//                    break;
//                // End program
//                case EXIT:
//                    System.out.println("GoodBye.");
//                    programOn = false;
//                    break;
//            }
//        }
//    }





    private static Account getAccount(Customer customer) {
        if (customer != null) {
            List<Account> accounts = customer.getAccounts();

            for (int i = 0; i < accounts.size(); i++) {
                System.out.println(accounts.get(i));
            }

            String id = RetrieveInput.readString("Enter the account's id:");

            for (int i = 0; i < accounts.size(); i++) {
                Account account = accounts.get(i);
                if (account.getId().equals(id)) {
                    return account;
                }
            }
        }

        return null;
    }
}