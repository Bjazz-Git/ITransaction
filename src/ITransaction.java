import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ITransaction {
    static String delimiter = " ";
    static int counter = 1;
    static List<Customer> customers = new ArrayList<>();
    static List<User> users = new ArrayList<>();


    public static void main(String[] args) {
        welcome();
        setupCustomers();
        String loginResult = login();

        if (loginResult.equals("admin")) {
            adminDashboard();
        }

        for (Customer customer : customers) {
            if (loginResult.equals(customer.getUsername())) {
                customerDashboard(customer);
            }
        }
    }

    private static String login() {
        while (true) {
            String enteredUsernamePassword = RetrieveInput.readString("Please enter username and password, space separated");

            if (validate(enteredUsernamePassword)) {
                String[] usernamePassword = enteredUsernamePassword.split(delimiter);
                String username = usernamePassword[0];
                String password = usernamePassword[1];

                if (username.equals("admin") && password.equals("admin123")) {
                    return "admin";
                }

                for (User user : users) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        return user.getUsername();
                    }
                }

                System.out.println("User could not be found.");
                System.out.println();
            }
        }
    }

    private static void welcome() {
        System.out.println("Welcome!");
    }

    private static boolean validate(String enteredUsernamePassword) {
        String[] usernamePassword = enteredUsernamePassword.split(delimiter);

        if (usernamePassword.length == 2) {
            if (usernamePassword[0].equals("admin") && usernamePassword[1].equals("admin123")) {
                return true;
            }

            for (Customer customer : customers) {
                if (customer.getUsername().equals(usernamePassword[0])) {
                    if (customer.getPassword().equals(usernamePassword[1])) {
                        return true;
                    }
                }
            }

            System.out.println("Could not find a customer with this information");
        } else {
            System.out.println("Invalid input.");
            System.out.println("Make sure you provide only 2 inputs and space them apart with " + delimiter);
        }
        System.out.println();
        return false;
    }


    private static void adminDashboard() {
        boolean programOn = true;
        AdminMenu adminMenu = new AdminMenu(customers);

        System.out.println("Welcome Admin!");
        while (programOn) {
            AdminMenu.printAdminDashboard();
            System.out.println();
            int option = RetrieveInput.readInt("Type the number of one of the above options:");

            switch (AdminOptions.values()[option - 1]) {
                // See all customers
                case SEEALLCUSTOMERS:{
                    adminMenu.printCustomers();
                    break;
                }
                // See all accounts
                case SEEALLACCOUNTS: {
                    adminMenu.printAccounts();
                    break;
                }
                // Delete an account
                case DELETEANACCOUNT: {
                    adminMenu.printCustomers();
                    Customer customer = getCustomer();
                    Account account = getAccount(customer);
                    adminMenu.deleteAccount(customer, account);
                    break;
                }
                // Update a customer
                case UPDATECUSTOMER: {
                    adminMenu.printCustomers();
                    Customer customer = getCustomer();
                    adminMenu.updateCustomer(customer);
                    break;
                }
                // Delete a customer
                case DELETECUSTOMER: {
                    adminMenu.printCustomers();
                    Customer customer = getCustomer();
                    adminMenu.deleteCustomer(customer);
                    break;
                }
                // End program if above input is not provided
                case EXIT: {
                    System.out.println("GoodBye.");
                    programOn = false;
                    break;
                }
            }
        }
    }

    private static void customerDashboard(Customer customer) {
        boolean programOn = true;
        CustomerMenu menu = new CustomerMenu(customer);

        System.out.println("Welcome " + customer.getUsername());

        while (programOn) {
            CustomerMenu.printCustomerDashboard();
            int option = RetrieveInput.readInt("Type the number of one of the above options:");

            switch (CustomerOptions.values()[option - 1]) {
                // Check Account
                case CHECKACCOUNT:
                    menu.printAccounts();
                    break;
                // Check balance
                case CHECKACCOUNTBALANCE:
                    menu.printBalance();
                    break;
                // Withdraw
                case WITHDRAW:
                    menu.withdraw();
                    break;
                // Deposit
                case DEPOSIT:
                    menu.deposit();
                    break;
                // End program
                case EXIT:
                    System.out.println("GoodBye.");
                    programOn = false;
                    break;
            }
        }
    }

    private static void setupCustomers() {
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

    private static CheckInsAccount createCheckingAccount(Customer customer) {
        return new CheckInsAccount(String.valueOf((int) (Math.random() * 998) + 1), customer, (int) (Math.random() * 1000), 50);
    }

    private static SavingsAccount createSavingsAccount(Customer customer) {
        return new SavingsAccount(String.valueOf((int) (Math.random() * 998) + 1), customer, (int) (Math.random() * 1000));
    }

    private static Customer getCustomer() {
        int customerId = RetrieveInput.readInt("Enter the user's id.");

        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                return customer;
            }
        }

        return null;
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
