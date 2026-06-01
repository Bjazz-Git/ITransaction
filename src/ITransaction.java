import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ITransaction {
    static Scanner scanner = new Scanner(System.in);
    static String delimiter = " ";
    static int counter = 1;
    static List<Customer> customers = new ArrayList<>();
    static List<User> users = new ArrayList<>();


    public static void main(String[] args){
        welcome();
        setupCustomers();
        String loginResult = login();

        if (loginResult.equals("admin")){
            adminDashboard();
        }

        for (Customer customer : customers){
            if (loginResult.equals(customer.getUsername())){
                customerDashboard(customer);
            }
        }
    }

    private static String login(){
        System.out.println("Please enter username and password, space separated");
        String enteredUsernamePassword = scanner.nextLine();

        if (validate(enteredUsernamePassword)){
          String[] usernamePassword = enteredUsernamePassword.split(delimiter);
          String username = usernamePassword[0];
          String password = usernamePassword[1];

          if (username.equals("admin") && password.equals("admin123")){
              return "admin";
          }

          for (User user: users){
              if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                  return user.getUsername();
              }
          }

            return "User could not be found";
        }

        else{
            return "validation failed";
        }
    }

    private static void welcome(){
        System.out.println("Welcome!");
    }

    private static boolean validate(String enteredUsernamePassword){
        String[] usernamePassword = enteredUsernamePassword.split(delimiter);

        if (usernamePassword.length == 2){
            if (usernamePassword[0].equals("admin") && usernamePassword[1].equals("admin123")){
                return true;
            }

            for (Customer customer : customers){
                if (customer.getUsername().equals(usernamePassword[0])){
                    if (customer.getPassword().equals(usernamePassword[1])) {
                        return true;
                    }
                }
            }

            System.out.println("Could not find a customer with this information");
            return false;
        }

        else{
            System.out.println("Invalid input.");
            System.out.println("Make sure you provide only 2 inputs and space them apart with " + delimiter);
            return false;
        }
    }


    private static void adminDashboard(){
        boolean programOn = true;
        AdminMenu adminMenu = new AdminMenu(customers);

        System.out.println("Welcome Admin!");
        while(programOn) {
            AdminMenu.printAdminDashboard();
            System.out.println("Type the number of one of the above options:");
            int option = scanner.nextInt();

            switch (option) {
                // See all customers
                case 1:
                    adminMenu.printCustomers();
                    break;
                // See all accounts
                case 2:
                    adminMenu.printAccounts();
                    break;
                // Delete an account
                case 3:
                    adminMenu.deleteAccount();
                    break;
                // End program if above input is not provided
                default:
                    System.out.println("GoodBye.");
                    programOn = false;
                    break;
            }
        }
    }

    private static void customerDashboard(Customer customer){
        boolean programOn = true;
        CustomerMenu menu = new CustomerMenu(customer);

        System.out.println("Welcome " + customer.getUsername());

        while(programOn) {
            CustomerMenu.printCustomerDashboard();
            System.out.println("Type the number of one of the above options:");
            int option = scanner.nextInt();

            switch (option) {
                // Check Account
                case 1:
                    menu.printAccount();
                    break;
                // Check balance
                case 2:
                    menu.printBalance();
                    break;
                // End program if above input is not provided
                default:
                    System.out.println("GoodBye.");
                    programOn = false;
                    break;
            }
        }
    }

    private static void setupCustomers(){
        //        CheckInsAccount checkInsAccount = new CheckInsAccount();
//        SavingsAccount savingsAccount = new SavingsAccount();

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
        Customer c2 = new Customer(counter++, "Jeff", u2.getUsername(), u2.getPassword(),  new ArrayList<>());
        ArrayList<Account> c2Accounts = new ArrayList<>();
        c2Accounts.add(createCheckingAccount(c1));
        c2Accounts.add(createSavingsAccount(c1));
        c2.setAccounts(c2Accounts);

        // Create customer 3 and accounts
        Customer c3 = new Customer(counter++, "John", u3.getUsername(), u3.getPassword(),  new ArrayList<>());
        ArrayList<Account> c3Accounts = new ArrayList<>();
        c3Accounts.add(createCheckingAccount(c1));
        c3Accounts.add(createSavingsAccount(c1));
        c3.setAccounts(c3Accounts);

        customers.add(c1);
        customers.add(c2);
        customers.add(c3);
    }

    private static CheckInsAccount createCheckingAccount(Customer customer){
        return new CheckInsAccount("123", customer,  (int) (Math.random() * 1000));
    }

    private static SavingsAccount createSavingsAccount(Customer customer){
        return new SavingsAccount("123", customer,  (int) (Math.random() * 1000));
    }

//    private Customer getCustomer(String username){
//        for (Customer customer : customers){
//            return customer;
//        }
//
//        return null;
//    }
}
