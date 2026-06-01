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

        for (User user : users){
            if (loginResult.equals(user.getUsername())){
                customerDashboard(user.getUsername());
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

        System.out.println("Welcome Admin!");
        while(programOn) {
            printAdminDashBoard();
            System.out.println("Type the number of one of the above options:");
            int option = scanner.nextInt();

            switch (option) {
                // See all customers
                case 1:
                    printCustomers();
                    break;
                // See all accounts
                case 2:
                    printAccounts();
                    break;
                // Delete an account
                case 3:
                    deleteAccount();
                    break;
                default:
                    System.out.println("GoodBye.");
                    programOn = false;
                    break;
            }
        }
    }

    private static void customerDashboard(String username){
        boolean programOn = true;

        System.out.println("Welcome " + username);

        while(programOn) {
            printAdminDashBoard();
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

    private static void printAdminDashBoard(){
        System.out.println("1. See all customers");
        System.out.println("2. See all accounts");
        System.out.println("3. Delete an account");
        System.out.println("4. Exit");
    }

    private static void printCustomerDashboard(){
        System.out.println("1. Check account");
        System.out.println("2. Check account balance");
        System.out.println("3. Exit");
    }

    private static void printCustomers(){
        for (Customer customer : customers){
            System.out.println(customer);
        }
    }

    private static void printAccounts(){

    }

    private static void deleteAccount(){

    }
}
