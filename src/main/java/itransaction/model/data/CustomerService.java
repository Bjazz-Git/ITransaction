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
        if (customer != null) {
            // 1. Generate the sequential integer ID for the customer document
            customer.setId(sequenceGeneratorService.generateSequence(Customer.SEQUENCE_NAME));

            // 2. Process linked accounts safely if they are included in the payload
            if (customer.getAccounts() != null) {

                // Assign fresh sequential String IDs to any brand-new accounts
                for (Account account : customer.getAccounts()) {
                    if (account.getId() == null || account.getId().trim().isEmpty()) {
                        int nextSeq = sequenceGeneratorService.generateSequence("accounts_sequence");
                        account.setId(String.valueOf(nextSeq));
                    }
                }

                // Verify that no existing account IDs are being illicitly reassigned
                List<Account> customerAccounts = verifyAccounts(customer.getAccounts());

                // Persist the newly IDs into the account collection
                for (Account account : customerAccounts) {
                    accountRepo.save(account);
                }

                // Bind the verified collection list back to the customer model mapping
                customer.setAccounts(customerAccounts);
            }

            // 3. Save everything to the customer collection and return the 201 status
            Customer savedCustomer = customerRepo.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }


    // UpdateCustomer
    public void updateCustomer(Customer customer, int id){
        // Ensures that the account doesn't have a new id
        Customer existingCustomer = findById(id);

        // If the id wasn't changed during the update, update the customer and their accounts
        if (customer.getId() == 0 || id == customer.getId()) {
            existingCustomer.setName(customer.getName());
            existingCustomer.setUsername(customer.getUsername());
            existingCustomer.setPassword(customer.getPassword());

            if (customer.getAccounts() != null) {
                for (Account account : customer.getAccounts()) {
                    //TODO Could cause new accounts to be created
                    accountRepo.save(account);
                }
                existingCustomer.setAccounts(customer.getAccounts());
            }

           customerRepo.save(existingCustomer);
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
        if (customer.getAccounts() != null) {
            for (Account account : customer.getAccounts()) {
                if (account != null && account.getId() != null) {
                    accountRepo.deleteById(account.getId());
                }
            }
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

        for (Account account : accounts) {
            // Skip validation if the account ID is brand new (null or empty)
            if (account.getId() == null || account.getId().trim().isEmpty()) {
                customerAccounts.add(account);
                continue; // Keep moving to the next account safely
            }

            // Only check for duplicates if an actual ID was provided by the frontend
            if (accountRepo.findById(account.getId()).isPresent()) {
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
