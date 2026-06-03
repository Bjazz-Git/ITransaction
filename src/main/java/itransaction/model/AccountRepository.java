package itransaction.model;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository {
    CustomerRepository customerRepository;

    public AccountRepository(CustomerRepository customers){
        this.customerRepository = customers;
    }

    // GetAllAccounts
    public List<Account> getAllAccounts(){
        List<Account> accounts  = new ArrayList<>();
        List<Customer> customers = customerRepository.getCustomers();

        for (int i = 0; i < customers.size(); i++){
            List<Account> customerAccounts = customers.get(i).getAccounts();
            if (!customerAccounts.isEmpty()){
                accounts.addAll(customerAccounts);
            }
        }

        return accounts;
    }

    // GetAccountById
    public Account getAccountById(String id){
        List<Account> accounts  = getAllAccounts();

        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }


        return null;
    }

    // GetAccountByName
    public List<Account> getAccountsByName(String name){
        List<Account> accounts  = getAllAccounts();
        List<Account> customerAccounts = new ArrayList<>();

        for (Account account : accounts){
            Customer customer = account.getAccountHolder();
            if (customer.getName().equalsIgnoreCase(name)){
                customerAccounts.add(account);
            }
        }

        return customerAccounts;
    }

    // CreateAccount (Assign to customer)
    public void createAccount(Account account){
        List<Customer> customers = customerRepository.getCustomers();

        for (Customer customer : customers){
            if (customer == account.getAccountHolder()){
                customer.getAccounts().add(account);
            }
        }
    }

    // UpdateAccount
    public void updateAccount(String id, Account account){
        List<Account> accounts  = getAllAccounts();

        for (int i = 0; i < accounts.size(); i++){
            if (accounts.get(i).getId().equals(account.getId())){
                Customer customer = accounts.get(i).getAccountHolder();
                for (int j = 0; j < customer.getAccounts().size(); j++){
                    if (customer.getAccounts().get(j).getId().equals(account.getId())) {
                        // Sets account holder to customer
                        account.setAccountHolder(customer);
                        // Updates account with new information
                        customer.getAccounts().set(j, account);
                        // Ends method after update happens
                        return;
                    }
                }
            }
        }

    }

    // DeleteAccount
    public void deleteAccount(String id){
        List<Account> accounts  = getAllAccounts();

        for (int i = 0; i < accounts.size(); i++){
            if (accounts.get(i).getId().equals(id)){
                Customer customer = accounts.get(i).getAccountHolder();
                for (int j = 0; j < customer.getAccounts().size(); j++){
                    if (customer.getAccounts().get(j).getId().equals(id)) {
                        customer.getAccounts().remove(j);
                        // Ends method after delete happens
                        return;
                    }
                }
            }
        }
    }
}
