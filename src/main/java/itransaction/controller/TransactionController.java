package itransaction.controller;

import itransaction.model.*;
import itransaction.model.data.AccountService;
import itransaction.model.data.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class TransactionController {
    static List<User> users = new ArrayList<>();
    static List<Customer> customers = new ArrayList<>();
    double premiumThreshold = 1000;
    CustomerService customerService;
    AccountService accountService;


    public TransactionController(CustomerService customerService, AccountService accountService){
        this.customerService = customerService;
        this.accountService = accountService;
    }

    // GetAllCustomers
    @GetMapping("")
    List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    // GetCustomerByID
    @GetMapping("id/{id}")
    Customer getCustomerById(@PathVariable Integer id){
        return customerService.getCustomerById(id);
    }

    // GetCustomerByName
    @GetMapping("name/{name}")
    Customer getCustomerByName(@PathVariable String name){
        return customerService.getCustomerByName(name);
    }

    // GetPremiumCustomers
    @GetMapping("/premium")
    List<Customer> getPremiumCustomers(){
        return null;
    }

    // CreateCustomer
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    // UpdateCustomer
    void updateCustomer(@RequestBody Customer customer, @PathVariable int id){
        customerService.updateCustomer(customer, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    // DeleteCustomer
    void deleteCustomer(@PathVariable int id){
        customerService.deleteCustomer(id);
    }

    // GetAllAccounts
    @GetMapping("/accounts")
    List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    // GetAccountById
    @GetMapping("/accounts/id/{id}")
    Account getAccountsById(@PathVariable String id){
        return accountService.getAccountsById(id);
    }

    // GetAccountByName
    @GetMapping("/accounts/name/{name}")
    List<Account> getAccountByName(@PathVariable String name){
        // Get the customer by their name and pass them into accountService
        Customer customer = customerService.getCustomerByName(name);
        return accountService.getAccountByName(customer);
    }

    // CreateAccount (Assign to customer)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/id/{id}/accounts")
    void createAccount(@RequestBody Account account, @PathVariable int id){
        Customer customer = customerService.getCustomerById(id);
        // Add account to account repo
        accountService.createAccount(account, customer);
        // Update customer with new account
        customerService.updateCustomer(customer, customer.getId());
    }

    // UpdateAccount
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/id/{id}/accounts")
    void updateAccount(@PathVariable String id, @RequestBody Account account){
        accountService.updateAccount(id, account);
    }

    // DeleteAccount
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable String id){
        accountService.deleteAccount(id);
    }
}