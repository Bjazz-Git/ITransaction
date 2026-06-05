package itransaction.model.data;

import itransaction.model.Account;
import itransaction.model.Customer;
import itransaction.model.customerIdHandlers.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    // GetAllCustomers
    public List<Customer> getAllCustomers(){
        return  customerRepo.findAll();
    }

    // GetCustomerByID
    public Customer getCustomerById(Integer id){
        return customerRepo.findById(id).orElseThrow(()->
                new RuntimeException("Account not found with ID: " + id)
        );
    }

    // GetCustomerByName
    public Customer getCustomerByName(String name){
        return customerRepo.findByNameIgnoreCase(name);
    }

    // GetPremiumCustomers
    List<Customer> getPremiumCustomers(){
        return null;
    }

    // CreateCustomer
    public ResponseEntity<Customer> createCustomer(Customer customer){
        if (customer != null){
            customer.setId(sequenceGeneratorService.generateSequence(Customer.SEQUENCE_NAME));
        }

        // Save customer accounts to account repo
        if (customer.getAccounts() != null) {
            // Store only accounts not present in the account repo in the list
            List<Account> customerAccounts = verifyAccounts(customer.getAccounts());

            // Adds new accounts to account repo
            for (Account account : customerAccounts) {
                accountRepo.save(account);
            }

            // Stores new accounts into customer
            customer.setAccounts(customerAccounts);
        }

        Customer savedCustomer = customerRepo.save(customer);

        return ResponseEntity.ok(savedCustomer);
    }


    // UpdateCustomer
    public void updateCustomer(Customer customer, int id){
        // Ensures that the account doesn't have a new id
        findById(id);

        // If the id wasn't changed during the update, update the customer and their accounts
        if (id == customer.getId()) {
            for (Account account : customer.getAccounts()) {
                //TODO Could cause new accounts to be created
                accountRepo.save(account);
            }
            customerRepo.save(customer);
        }

        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You can't change the customer Id!");
        }
    }


    // DeleteCustomer
    public void deleteCustomer(int id){
        Customer customer = findById(id);

        // Deletes accounts associated with that customer
        for (Account account : customer.getAccounts()){
            accountRepo.deleteById(account.getId());
        }

        customerRepo.deleteById(id);
    }

    // Get all Premium Customers
    public List<Customer> getAllPremiumCustomers(double premium){
        List<Customer> customers = getAllCustomers();
        List<Customer> premiumCustomers = new ArrayList<>();

        // Loops through all customers
        for (Customer customer : customers){
            // Stores the value of the customer's accounts
            double totalValue = 0;
            // Loops through all the customer's accounts and sums there balance
            for (Account account : customer.getAccounts()){
                totalValue += account.getBalance();
            }

            // If the total value across accounts is greater than premium, then that customer is a premium customer
            if (totalValue > premium){
                premiumCustomers.add(customer);
            }
        }

        return premiumCustomers;
    }

    // Ensures that when a customer is added there accounts are unique
    private List<Account> verifyAccounts(List<Account> accounts){
        List<Account> customerAccounts = new ArrayList<>();

        for (Account account : accounts){
            // Throws an error if a duplicate account was added to customer
            if (accountRepo.findById(account.getId()).isPresent()){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Account ID " + account.getId() + " already exists and cannot be reassigned!");
            }
            customerAccounts.add(account);
        }

        return customerAccounts;
    }

    private Customer findById(int id){
        // Makes sure account is within database
        return customerRepo.findById(id).orElseThrow(()->
                new RuntimeException("Customer not found with ID: " + id));
    }
}
