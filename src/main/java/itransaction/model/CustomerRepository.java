package itransaction.model;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {
    List<User> users = new ArrayList<>();
    List<Customer> customers = new ArrayList<>();
    int counter = 1;

    @PostConstruct
    private void init() {
        User u1 = new User("bob", "bob123");
        User u2 = new User("jeff", "jeff123");
        User u3 = new User("john", "john123");
        users.add(u1);
        users.add(u2);
        users.add(u3);

        // Create customer 1 and accounts
        Customer c1 = new Customer(counter++, "Bob", u1.getUsername(), u1.getPassword(), new ArrayList<>());
        ArrayList<Account> c1Accounts = new ArrayList<>();
        c1Accounts.add(createCheckingAccount(c1));
        c1Accounts.add(createSavingsAccount(c1));
        c1.setAccounts(c1Accounts);

        // Create customer 2 and accounts
        Customer c2 = new Customer(counter++, "Jeff", u2.getUsername(), u2.getPassword(), new ArrayList<>());
        ArrayList<Account> c2Accounts = new ArrayList<>();
        c2Accounts.add(createCheckingAccount(c1));
        c2Accounts.add(createSavingsAccount(c1));
        c2.setAccounts(c2Accounts);

        // Create customer 3 and accounts
        Customer c3 = new Customer(counter++, "John", u3.getUsername(), u3.getPassword(), new ArrayList<>());
        ArrayList<Account> c3Accounts = new ArrayList<>();
        c3Accounts.add(createCheckingAccount(c1));
        c3Accounts.add(createSavingsAccount(c1));
        c3.setAccounts(c3Accounts);

        customers.add(c1);
        customers.add(c2);
        customers.add(c3);
    }

    private static CheckingsAccount createCheckingAccount(Customer customer) {
        return new CheckingsAccount(String.valueOf((int) (Math.random() * 998) + 1), customer, (int) (Math.random() * 1000), 50);
    }

    private static SavingsAccount createSavingsAccount(Customer customer) {
        return new SavingsAccount(String.valueOf((int) (Math.random() * 998) + 1), customer, (int) (Math.random() * 1000));
    }

    public List<Customer> getCustomers(){
        return customers;
    }

    public List<User> getUsers(){
        return users;
    }

    public Customer getCustomerById(int customerId) {
        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                return customer;
            }
        }

        return null;
    }

    public Customer getCustomerByName(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }

        return null;
    }

    public void createCustomer(Customer customer){
        customers.add(customer);
    }
}
