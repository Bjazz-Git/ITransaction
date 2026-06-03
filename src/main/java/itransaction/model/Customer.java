package itransaction.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public class Customer extends User {
    private int id;
    private String name;
    private List<Account> accounts;
    private String username;
    private String password;

    public Customer(int id, String name, String username, String password, List<Account> accounts) {
        super(username, password);
        this.id = id;
        this.name = name;
        this.accounts = accounts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "model.Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
