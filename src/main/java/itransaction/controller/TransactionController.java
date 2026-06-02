package itransaction.controller;

import itransaction.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void createCustomer(@RequestBody Customer customer){
        customerRepository.createCustomer(customer);
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