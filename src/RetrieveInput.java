import java.util.Scanner;

public class RetrieveInput {
    static Scanner scanner = new Scanner(System.in);

    public static String readString(String message){
        System.out.println(message);
        return scanner.nextLine();
    }

    public static int readInt(String message){
        return Integer.parseInt(readString(message));
    }
}
