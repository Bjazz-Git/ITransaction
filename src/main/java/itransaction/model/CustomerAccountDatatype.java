package itransaction.model;

public class CustomerAccountDatatype {
    Customer customer;
    Account account;

    public CustomerAccountDatatype(Customer customer, Account account) {
        this.customer = customer;
        this.account = account;
        this.account.setAccountHolder(customer);
        this.customer.getAccounts().add(account);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
