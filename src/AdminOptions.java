public enum AdminOptions {
    SEEALLCUSTOMERS(1, "See all customers"),
    SEEALLACCOUNTS(2, "See all accounts"),
    DELETEANACCOUNT(3, "Delete account"),
    UPDATECUSTOMER(4, "Update Customer"),
    DELETECUSTOMER(5, "Delete customer"),
    EXIT(6, "Exit");


    int count;
    String optionName;

    public int getCount() {
        return count;
    }

    public String getOptionName() {
        return optionName;
    }

    AdminOptions(int count, String optionName){
        this.count = count;
        this.optionName = optionName;
    }


}
