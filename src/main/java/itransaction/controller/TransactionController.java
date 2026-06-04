package itransaction.controller;

import itransaction.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class TransactionController {
    static List<User> users = new ArrayList<>();
    static List<Customer> customers = new ArrayList<>();
    CustomerRepository customerRepository;
    AccountRepository accountRepository;
    double premiumThreshold = 1000;

    @Autowired
    CustomerRepo customerRepo;

    public TransactionController(CustomerRepository customerRepository, AccountRepository accountRepository){
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    // GetAllCustomers
    @GetMapping("")
    List<Customer> getAllCustomers(){
        return  customerRepo.findAll();
    }

    // GetCustomerByID
    @GetMapping("id/{id}")
    Customer getCustomerById(@PathVariable Integer id){
        return customerRepository.getCustomerById(id);
    }

    // GetCustomerByName
    @GetMapping("name/{name}")
    Customer getCustomerByName(@PathVariable String name){
        return customerRepository.getCustomerByName(name);
    }

    // GetPremiumCustomers
    @GetMapping("/premium")
    List<Customer> getPremiumCustomers(){
        return customerRepository.getPremiumCustomers(premiumThreshold);
    }

    // CreateCustomer
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void createCustomer(@RequestBody Customer customer){
        customerRepo.save(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    // UpdateCustomer
    void updateCustomer(@RequestBody Customer customer, @PathVariable int id){
        customerRepository.updateCustomer(customer, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    // DeleteCustomer
    void deleteCustomer(@PathVariable int id){
        customerRepository.deleteCustomer(id);
    }

    // GetAllAccounts
    @GetMapping("/accounts")
    List<Account> getAllAccounts(){
        return accountRepository.getAllAccounts();
    }

    // GetAccountById
    @GetMapping("/accounts/id/{id}")
    Account getAccountsById(@PathVariable String id){
        return accountRepository.getAccountById(id);
    }

    // GetAccountByName
    @GetMapping("/accounts/name/{name}")
    List<Account> getAccountByName(@PathVariable String name){
        return accountRepository.getAccountsByName(name);
    }

    // CreateAccount (Assign to customer)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/id/{id}/accounts")
    void createAccount(@RequestBody Account account, @PathVariable int id){
        // Get the account's owner and store that information in the account
        Customer accountCustomer = customerRepository.getCustomerById(id);
        account.setAccountHolder(accountCustomer);
        accountRepository.createAccount(account);
    }

    // UpdateAccount
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/id/{id}/accounts")
    void updateAccount(@PathVariable String id, @RequestBody Account account){
        accountRepository.updateAccount(id, account);
    }

    // DeleteAccount
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable String id){
        accountRepository.deleteAccount(id);
    }
}