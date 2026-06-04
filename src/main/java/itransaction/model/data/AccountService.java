package itransaction.model.data;

import itransaction.model.Account;
import itransaction.model.Customer;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    // GetAllAccounts
    public List<Account> getAllAccounts(){
        return accountRepo.findAll();
    }

    // GetAccountById
    public Account getAccountsById(String id){
        return findById(id);
    }

    // GetAccountByName
    public List<Account> getAccountByName(Customer customer){
        List<Account> accounts = new ArrayList<>();

        if (customer != null){
            accounts.addAll(customer.getAccounts());
        }

        return accounts;
    }

    // TODO: Would update account if it has the same id as an existing one
    // CreateAccount (Assign to customer)
    public void createAccount(Account account, Customer customer){
        Account savedAccount = accountRepo.save(account);
        List<Account> customerAccounts = customer.getAccounts();

        for (Account customerAccount : customerAccounts){
            // If the customer already has this account, don't re-add it
            if (customerAccount.getId().equals(savedAccount.getId())){
                return;
            }
        }

        // Adds account to its respective customer
        customerAccounts.add(account);
    }

    // UpdateAccount
    public void updateAccount(String id, Account account){
        // Ensures that the account doesn't have a new id
        findById(id);
        // If the id wasn't changed during the update, update the account
        if (id.equals(account.getId())) {
            accountRepo.save(account);
        }

        else{
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You can't change the Account's Id!");
        }
    }

    // DeleteAccount
    public void deleteAccount(String id){
        Query query = new Query(Criteria.where("accounts").is(id));
        Update update = new Update().pull("accounts", id);

        // This updates the customer document in the database instantly
        mongoTemplate.updateMulti(query, update, Customer.class);

        accountRepo.deleteById(id);
    }

    private Account findById(String id){
        // Makes sure account is within database
        return accountRepo.findById(id).orElseThrow(()->
                new RuntimeException("Account not found with ID: " + id));
    }
}
