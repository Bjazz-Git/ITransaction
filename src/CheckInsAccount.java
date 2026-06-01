public class CheckInsAccount extends Account{
    String accountType = "CheckingsAccount";

    @Override
    double addInterest() {
        return 0;
    }
}
