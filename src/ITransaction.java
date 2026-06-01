import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ITransaction {
    static Scanner scanner = new Scanner(System.in);
    static String delimiter = " ";
    static int counter = 1;
    static List<Customer> customers = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    static{
        CheckInsAccount checkInsAccount = new CheckInsAccount();
        SavingsAccount savingsAccount = new SavingsAccount();

        User u1 = new User("bob", "bob123");
        User u2 = new User("jeff", "jeff123");
        User u3 = new User("john", "john123");
        users.add(u1);
        users.add(u2);
        users.add(u3);

        Customer c1 = new Customer(counter++, "Bob", u1.getUsername(), u1.getPassword(), new ArrayList<>());
        Customer c2 = new Customer(counter++, "Jeff", u2.getUsername(), u2.getPassword(),  new ArrayList<>());
        Customer c3 = new Customer(counter++, "John", u3.getUsername(), u3.getPassword(),  new ArrayList<>());
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);
    }


    public static void main(String[] args){
        welcome();
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

    }

    private static boolean validate(String usernamePassword){
        return true;
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
                    break;
                // Check balance
                case 2:
                    break;
                default:
                    System.out.println("GoodBye.");
                    programOn = false;
                    break;
            }
        }
    }

//    private Customer getCustomer(String username){
//        for (Customer customer : customers){
//            return customer;
//        }
//
//        return null;
//    }
}
